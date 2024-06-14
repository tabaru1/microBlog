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
	@GetMapping(value = "/create/{userId}")
	public String follow(@PathVariable Integer userId,Follow follow, BindingResult result, Model model, @AuthenticationPrincipal UserDetails user,RedirectAttributes ra)  {
		FlashData flash;
		try {
			String username = user.getUsername();
			User userData = userService.findByEmail(username);
			User followed = userService.findById(userData.getId());
			User following = userService.findById(userId);
			
			
			Follow follower = followService.findByFollowingAndFollowed(following,  followed);
			if(follower != null ) {
				return "redirect:/microBlog/admin/user";
			}
			else {
				follow = new Follow();
	            follow.setFollowing(following);
	            follow.setFollowed(followed);
	            followService.save(follow);
	    		flash = new FlashData().success("フォローが登録されました");
			}
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/microBlog/admin/user";
			}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/microBlog/admin/user";
	}
	
	
}