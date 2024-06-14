package com.example.demo.web.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.entity.Favorite;
import com.example.demo.entity.Follow;
import com.example.demo.entity.Tweet;
import com.example.demo.entity.User;
import com.example.demo.service.FavoriteService;
import com.example.demo.service.FollowService;
import com.example.demo.service.TweetService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/microBlog/admin/tweet")
public class TweetController {
	@Autowired
	TweetService tweetService;
	@Autowired
	UserService userService;
	@Autowired
	FavoriteService favoriteService;
	@Autowired
	FollowService followService;
	
	
	
	/*
	 * 一覧表示
	*/
	@GetMapping(value = "/mytalk/{userId}")
	public String list(@PathVariable Integer userId,Tweet tweet,Model model, @AuthenticationPrincipal UserDetails user,RedirectAttributes ra) {
		// 全件取得
		String username = user.getUsername();
		try {
			User userData = userService.findByEmail(username);
			List<Tweet> tweets = tweetService.findByUserId(userData.getId());
			model.addAttribute("tweets", tweets);
		} catch (DataNotFoundException e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/microBlog/admin";		
			}
		return "admin/tweet/mytalk";
	}
	

	/*
	 * タイムライン画面表示
	
	@GetMapping(value = "/talk")
	public String talk(Integer tweetId,Tweet tweet,@AuthenticationPrincipal UserDetails user, Model model) throws DataNotFoundException {
		String username = user.getUsername();
		User userData = userService.findByEmail(username);
		model.addAttribute("tweetId", userData.getId());
		
//		Favorite ftweet = favoriteService.findByTweetIdAndUserId(tweetId, userData.getId());
//		model.addAttribute("ftweet", ftweet);
		
		User userId = userService.findById(userData.getId());
		model.addAttribute("user", userId);
		List<Tweet> tweets = tweetService.findAll();//findByUserId(userData.getId())
		model.addAttribute("tweets", tweets);
		return "admin/tweet/talk";
	}
	
	/*
	 * タイムライン画面表示
     
	@GetMapping(value = "/talk")
	public String talk(Integer tweetId,Tweet tweet,@AuthenticationPrincipal Follow follow ,@AuthenticationPrincipal UserDetails user, Model model, RedirectAttributes ra){
		String username = user.getUsername();
		try {
			User userData = userService.findByEmail(username);
			User userId = userService.findById(userData.getId());
			model.addAttribute("user", userId);
			List<Favorite> favorites= favoriteService.findByUserId(userData.getId());
			model.addAttribute("favorites", favorites);
			
			List<Tweet> tweets = tweetService.findByUserId(userData.getId());
			model.addAttribute("tweets", tweets);
			
			
		} catch (DataNotFoundException e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/microBlog/admin/tweet/talk";
			}
		return "admin/tweet/talk";
	}
	 */
	
	/*
	 * タイムライン画面表示
	 */
	@GetMapping(value = "/talk")
	public String talk(Integer tweetId,Tweet tweet,@AuthenticationPrincipal UserDetails userDetails, Model model, RedirectAttributes ra){
		String username = userDetails.getUsername();
		try {
			User userData = userService.findByEmail(username);
			User user = userService.findById(userData.getId());
			model.addAttribute("user", user);
//			List<Favorite> favorites= favoriteService.findByUserId(userData.getId());
//			model.addAttribute("favorites", favorites);
			
			List<Follow> follows = followService.findByUser(userData);
		   
			List<Integer> userIds = new ArrayList<>();
			userIds.add(user.getId());
			for (Follow value : follows) {
				userIds.add(value.getFollowing().getId());	
			}
			
			List<Tweet> tweets = tweetService.findByUserIdIn(userIds); // ログインユーザのidが含まれていない
//			tweets.add(tweetService.findById(userData.getId()));	
			//Collections.reverse(tweets);//タイムラインで時系列、上に表示
			model.addAttribute("tweets", tweets);
			
			//Listにtweet_idを集める
			List<Favorite> favorites = favoriteService.findByUserId(userData.getId());
			List<Integer> tweetIds = new ArrayList<>();
			for (Favorite value : favorites) {
				tweetIds.add(value.getTweet().getId());
			}
			model.addAttribute("tweetIds", tweetIds);
			
		} catch (DataNotFoundException e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/microBlog/admin/tweet/talk";
			}
		return "admin/tweet/talk";
	}
	
	/*
	 * タイムライン画面表示
	@GetMapping(value = "/talk")
	public String talk(Integer tweetId,Tweet tweet,@AuthenticationPrincipal UserDetails userDetails, Model model, RedirectAttributes ra){
		String username = userDetails.getUsername();
		try {
			User userData = userService.findByEmail(username);
			User userId = userService.findById(userData.getId());
			model.addAttribute("user", userId);
//			List<Favorite> favorites= favoriteService.findByUserId(userData.getId());
//			model.addAttribute("favorites", favorites);
			
			List<Follow> follows = followService.findByUser(userData);
			if(follows.isEmpty()) {
				List<Tweet> tweets = tweetService.findByUserId(userData.getId());
				model.addAttribute("tweets", tweets);	
			}
			else {
				List<Integer> userIds = new ArrayList<>();
				for (Follow value : follows) {
					userIds.add(value.getFollowed().getId());
					userIds.add(value.getFollowing().getId());	
				}
				List<Tweet> tweets = tweetService.findByUserIdIn(userIds); // ログインユーザのidが含まれていない
//				tweets.add(tweetService.findById(userData.getId()));	
				Collections.reverse(tweets);//タイムラインで時系列、上に表示
				model.addAttribute("tweets", tweets);
			}
			//Listにtweet_idを集める
			List<Favorite> favorites = favoriteService.findByUserId(userData.getId());
			List<Integer> tweetIds = new ArrayList<>();
			for (Favorite value : favorites) {
				tweetIds.add(value.getTweet().getId());
			}
			model.addAttribute("tweetIds", tweetIds);
		} catch (DataNotFoundException e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/microBlog/admin/tweet/talk";
			}
		return "admin/tweet/talk";
	}
	 */
	
	/*
	 * つぶやき
	*/
	@PostMapping(value = "/create")
	public String register(@Valid Tweet tweet, BindingResult result,Model model, @AuthenticationPrincipal UserDetails userDetails,RedirectAttributes ra) {
		FlashData flash;
		try {
			String username = userDetails.getUsername();
			User userData = userService.findByEmail(username);
			tweet.setUser(userService.findById(userData.getId()));
			if (result.hasErrors()) {
				String errorMessages = "";
				for (ObjectError error : result.getAllErrors()) {
					// ここでメッセージを取得する。
					errorMessages += error.getDefaultMessage();
				}
				flash = new FlashData().danger(errorMessages);
				ra.addFlashAttribute("flash", flash);
				return "redirect:/microBlog/admin/tweet/talk";
			}
			else {	
				tweetService.save(tweet);
			}                                                                                                                                                                                                                                                                                                                                                         
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/microBlog/admin/tweet/talk";		
		}
		return "redirect:/microBlog/admin/tweet/talk";
	}
	
	/*
	 * 個別ユーザつぶやき一覧表示
	*/
	@GetMapping(value = "/view/{userId}")
	public String view(@PathVariable Integer userId,Tweet tweet,Model model,@AuthenticationPrincipal UserDetails userDetails,RedirectAttributes ra) throws DataNotFoundException {
		String username = userDetails.getUsername();
		try {
			
			List<Tweet> tweets = tweetService.findByUserId(userId);
			//Collections.reverse(tweets);//タイムラインで時系列、上に表示
			model.addAttribute("tweets", tweets);
			
			User userData = userService.findByEmail(username);
			User uId = userService.findById(userData.getId());
			model.addAttribute("user", uId);
			//Listにtweet_idを集める
			List<Favorite> favorites = favoriteService.findByUserId(userData.getId());
			List<Integer> tweetIds = new ArrayList<>();
			for (Favorite value : favorites) {
				tweetIds.add(value.getTweet().getId());
			}
			model.addAttribute("tweetIds", tweetIds);
		} catch (DataNotFoundException e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/microBlog/admin/tweet/talk";		
			}
		return "admin/tweet/view";
	}
	
	
	/*
	 * 個別ユーザつぶやき一覧表示
	*/
	@GetMapping(value = "/view2/{id}")
	public String Individual(@PathVariable Integer id,Tweet tweet,Model model,@AuthenticationPrincipal UserDetails userDetails,RedirectAttributes ra) throws DataNotFoundException {
		String username = userDetails.getUsername();
		try {
			Tweet tweets = tweetService.findById(id);
			model.addAttribute("tweets", tweets);
			
			User userData = userService.findByEmail(username);
			User uId = userService.findById(userData.getId());
			model.addAttribute("user", uId);
			//Listにtweet_idを集める
			List<Favorite> favorites = favoriteService.findByUserId(userData.getId());
			List<Integer> tweetIds = new ArrayList<>();
			for (Favorite value : favorites) {
				tweetIds.add(value.getTweet().getId());
			}
			model.addAttribute("tweetIds", tweetIds);
		} catch (DataNotFoundException e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/microBlog/admin/tweet/talk";		
			}
		return "admin/tweet/view2";
	}

	
}
