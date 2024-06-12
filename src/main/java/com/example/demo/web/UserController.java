package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.FlashData;
import com.example.demo.common.UserForm;
import com.example.demo.common.ValidationGroups.Create;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/microBlog/user")
public class UserController {
	@Autowired
	UserService userService;

	/*
	 * 新規作成画面表示
	 */
	@GetMapping(value = "/create")
	public String form(UserForm userForm, Model model) {
		model.addAttribute("userForm", userForm);
		return "user/create";
	}

	/*
	 * 新規登録
	 */
	@PostMapping(value = "/create")
	public String register(@Validated(Create.class) UserForm userForm, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "user/create";
			}
			
			User user = new User();
			user.setNickname(userForm.getNickname());
			user.setEmail(userForm.getEmail());
			// 平文のパスワードを暗号文にする
			user.encodePassword(userForm.getPassword());
			// 新規登録
			userService.save(user);
			user.setAuth(true);
			flash = new FlashData().success("新規作成しました");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/microBlog/user/login";
	}
	/*
	 * ログイン画面表示
	 */
	@GetMapping(value = "/login")
	public String loginForm(User user, Model model) {
		return "user/login";
	}

}

