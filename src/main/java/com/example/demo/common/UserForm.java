package com.example.demo.common;

import com.example.demo.common.ValidationGroups.Create;
import com.example.demo.common.ValidationGroups.Update;

import jakarta.persistence.Column;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class UserForm {

	@Column(length = 255, nullable = false, unique = true)
	@NotEmpty(groups = { Create.class, Update.class }, message = "メールアドレスは必須項目です")
	@Email(groups = { Create.class, Update.class }, message = "メールアドレスの形式が不正です")
	private String email;

	@Column(length = 255, nullable = false)
	@NotEmpty(groups = { Create.class }, message = "パスワードは必須項目です")
	private String password;
	
	@Column(length = 255, nullable = false)
	@NotEmpty(groups = { Create.class }, message = "パスワード(確認用)は必須項目です")
	private String passwordConfirm;
	
	@Column(length = 255, nullable = false)
	@NotEmpty(groups = { Create.class }, message = "ニックネームは必須項目です")
	private String nickname;

	@AssertTrue(message = "パスワードとパスワード(再認証)は同一にしてください。")
    public boolean isPasswordValid() {
        return password.equals(passwordConfirm);
    }
}
