package com.example.demo.web.admin;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.entity.Favorite;
import com.example.demo.entity.User;
import com.example.demo.service.FavoriteService;
import com.example.demo.service.TweetService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/microBlog/admin/favorite")
public class FavoriteController {
	@Autowired
	FavoriteService favoriteService;
	@Autowired
	UserService userService;
	@Autowired
	TweetService tweetService;
	
	
	/*
	 * 一覧表示
	
	@GetMapping(value = "/mylist")
	public String list(Favorite favorite,Model model, @AuthenticationPrincipal UserDetails userDetails,RedirectAttributes ra, Integer tweetId) {
		// 全件取得	
		String username = userDetails.getUsername();
		try {
			User userData = userService.findByEmail(username);
			User userId = userService.findById(userData.getId());
			model.addAttribute("user", userId);
			
			
			List<Favorite> favorites= favoriteService.findByUserId(userData.getId());
			//Collections.reverse(favorites);//タイムラインで時系列、上に表示
			model.addAttribute("favorites", favorites);
//			List<Favorite> fav = new ArrayList<>();
//			for (Favorite value : favorites) {
//				fav.add(value);
//			}
			model.addAttribute("favorites", favorites);
			Favorite ftweet = favoriteService.findByTweetIdAndUserId(tweetId,  userData.getId());
			model.addAttribute("ftweet", ftweet);
		} catch (DataNotFoundException e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/microBlog/admin/favorite/mylist";		
			}
		return "admin/favorite/mylist";
	}
	
	/*
	 * 一覧表示
	*/
	@GetMapping(value = "/mylist")
	public String list(Favorite favorite,Model model, @AuthenticationPrincipal UserDetails userDetails,RedirectAttributes ra, Integer tweetId) {
		// 全件取得	
		String username = userDetails.getUsername();
		try {
			User userData = userService.findByEmail(username);
			User user = userService.findById(userData.getId());
			model.addAttribute("user", user);
			
			
			List<Favorite> favorites= favoriteService.findByUserId(userData.getId());
		    Collections.sort(favorites, (f1, f2) -> f2.getTweet().getCreatedAt().compareTo(f1.getTweet().getCreatedAt()));
			/*favorites = favorites.stream()
					.sorted((f1, f2) -> f2.getTweet().getCreatedAt().compareTo(f1.getTweet().getCreatedAt()))
					.collect(Collectors.toList());*/
			/*List<Integer> tweetIds = new ArrayList<>();
			tweetIds.add(user.getId());
			for (Favorite value : favorites) {
				tweetIds.add(value.getTweet().getId());	
			}
			List<Tweet> tweets = tweetService.findByUserIdIn(tweetIds); 
			
			List<Favorite> fav = new ArrayList<>();
			fav.add(((Favorite) tweets).getTweet());*/
			

			model.addAttribute("favorites", favorites);
			
			//Collections.reverse(favorites);//タイムラインで時系列、上に表示
//			model.addAttribute("favorites", favorites);
//			List<Favorite> fav = new ArrayList<>();
//			for (Favorite value : favorites) {
//				fav.add(value);
//			}
//		    model.addAttribute("favorites", favorites);
			Favorite ftweet = favoriteService.findByTweetIdAndUserId(tweetId,  userData.getId());
			model.addAttribute("ftweet", ftweet);
		} catch (DataNotFoundException e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/microBlog/admin/favorite/mylist";		
			}
		return "admin/favorite/mylist";
	}
	
	/*
	 * お気に入りに追加
	 */
	@GetMapping(value = "/create/{tweetId}")
	public String resister(@PathVariable Integer tweetId,@Valid Favorite favorite,BindingResult result, Model model, @AuthenticationPrincipal UserDetails userDetails,RedirectAttributes ra) {
		FlashData flash;
		try {
			String username = userDetails.getUsername();
			User userData = userService.findByEmail(username);
			
			favorite.setUser(userService.findById(userData.getId()));
			favorite.setTweet(tweetService.findById(tweetId));	
			Favorite ftweet = favoriteService.findByTweetIdAndUserId(tweetId,  userData.getId());
			if(ftweet != null ) {
				return "redirect:/microBlog/admin/tweet/talk";
			}
			else {
				favoriteService.save(favorite);
			    flash = new FlashData().success("お気に入りに追加しました");
			}
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/microBlog/admin/tweet/talk";		
			}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/microBlog/admin/favorite/mylist";
	}
	
	@GetMapping(value = "/delete/{tweetId}")
	public String delete(@PathVariable Integer tweetId, Favorite favorite, Model model,
			@AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttr) {
		try {
			String username = userDetails.getUsername();
			User userData = userService.findByEmail(username);
			Favorite fu = favoriteService.findByTweetIdAndUserId(tweetId, userData.getId());
			favoriteService.deleteById(fu.getId());
			FlashData flash = new FlashData().success("お気に入り解除しました");
			redirectAttr.addFlashAttribute("flash", flash);
		} catch (Exception e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			redirectAttr.addFlashAttribute("flash", flash);
		}
		return "redirect:/microBlog/admin/favorite/mylist";		
	}
	
	

		


	
}
