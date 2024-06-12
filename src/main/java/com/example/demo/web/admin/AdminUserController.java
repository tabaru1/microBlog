package com.example.demo.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.common.ValidationGroups.Update;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/microBlog/admin/user")
public class AdminUserController {
	@Autowired
	UserService userService;

	/*
	 * ユーザ一覧表示
	
	@GetMapping(path = {"", "/"})
	public String list(@AuthenticationPrincipal UserDetails user,Model model) throws DataNotFoundException {
		String username = user.getUsername();
		User userData = userService.findByEmail(username);
		User userId = userService.findById(userData.getId());
		model.addAttribute("user", userId);
		// 全件取得
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		return "admin/user/list";
	}
	
	/*
	 * ユーザ一覧表示
	@GetMapping(path = {"", "/"})
	public String list(@AuthenticationPrincipal UserDetails userDetails,Model model, Integer id, RedirectAttributes ra) throws DataNotFoundException {
		String username = userDetails.getUsername();
		User userData = userService.findByEmail(username);
		User userId = userService.findById(userData.getId());
		model.addAttribute("user", userId);
		User uID = userService.findById(id);
		if(uID != userId) {
			model.addAttribute("user", uID);
		}
		else {
			return	"redirect:/microBlog/admin/user/list";
		}
	    return "admin/user/list";
	}
	
	/*
	 * ユーザ一覧表示
	 */
	@GetMapping(path = {"", "/"})
	public String list(@AuthenticationPrincipal UserDetails user,Model model, Integer id, RedirectAttributes ra) throws DataNotFoundException {
		String username = user.getUsername();
		User userData = userService.findByEmail(username);
		User userId = userService.findById(userData.getId());
		model.addAttribute("user", userId);
		List<User> users = userService.findByIdNot(userData.getId());
		model.addAttribute("users", users);
	    return "admin/user/list";
	}
	/*
	 * ログインユーザの編集画面表示
	 
	@GetMapping(value = "/edit")
	public String edit(@AuthenticationPrincipal UserDetails user,Model model, RedirectAttributes ra) {
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User editUser;
		try {
			editUser = userService.findByEmail(email);
			// パスワードは表示しないので、空にする
			editUser.setNickname("");
		} catch (DataNotFoundException e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin";
		}
		model.addAttribute(editUser);
		return "admin/user/edit";
	}

	/*
	 * ログインユーザの編集画面表示
	 */
	@GetMapping(value = "/edit")
	public String edit(@AuthenticationPrincipal UserDetails user,Model model, RedirectAttributes ra) {
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User editUser;
		try {
			editUser = userService.findByEmail(email);
		} catch (DataNotFoundException e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin";
		}
		model.addAttribute(editUser);
		return "admin/user/edit";
	}

	/*
	 * 更新
	 */
	@PostMapping(value = "/edit")
	public String update(@Validated(Update.class) User user, BindingResult result, Model model, RedirectAttributes ra) {
		// SpringSecurity側からログインユーザの情報を取得する
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		FlashData flash;
		try {
			User authUser = userService.findByEmail(email);
			if (result.hasErrors()) {
				model.addAttribute(user);
				return "admin/user/edit";
			}
			// リクエスト値とマージ
			authUser.setNickname(user.getNickname());
			userService.save(authUser);
			flash = new FlashData().success("更新しました");
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("該当データがありません!");
		} catch (Exception e) {
			flash = new FlashData().danger("エラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/microBlog/admin/user";
	}

}
