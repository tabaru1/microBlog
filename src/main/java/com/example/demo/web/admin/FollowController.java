package com.example.demo.web.admin;

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
import com.example.demo.entity.Follow;
import com.example.demo.entity.User;
import com.example.demo.service.FollowService;
import com.example.demo.service.TweetService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/microBlog/admin/follow")
public class FollowController {
	@Autowired
	TweetService tweetService;
	@Autowired
	UserService userService;
	@Autowired
	FollowService followService;
	

	/*
	 * フォローする
	 */
	@GetMapping(value = "/create/{id}")
	public String follow(@PathVariable Integer id,@Valid Follow follow,BindingResult result, Model model, @AuthenticationPrincipal UserDetails user,RedirectAttributes ra, Integer userId)  {
		FlashData flash;
		try {
			String username = user.getUsername();
			User userData = userService.findByEmail(username);
//			tweet.setUser(userService.findById(userData.getId()));
//			Follow followingUser = follow.getId();
			Integer followingUser = userData.getId();
			Integer followedUser = tweetService.findById(id).getUser().getId();
//			follow.setUser(followService.findById(followingUser.getUserId()));
//			follow.setFollowing(tweetService.findById(followedUser.getId()));	
//			List<User> follows = userService.findByFollwingUserId(userData.getId());
			if (followingUser != null && followedUser != null && !followingUser.equals(followedUser)) {
            //フォローする
			  followService.save(follow);
			  return "redirect:/microBlog/admin/user/list";
       } else {
           return "フォローを失敗しました。";
       }
//			else {
//				return "redirect:/microBlog/admin/user/list";
//			}
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/microBlog/admin/user/list";
	}

}