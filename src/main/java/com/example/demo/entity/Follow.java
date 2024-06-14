package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "follow")
public class Follow {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
    private User followed;


	@ManyToOne
	@JoinColumn(name = "followingUserId", nullable = false)
    private User following;

//	@ManyToOne
//	@JoinColumn(name = "userId")
//	private User user;
//	
//	@ManyToOne
//	@JoinColumn(name = "userId")
//	private User user;
	
//	@ManyToOne
//	@JoinColumn(name = "tweetId")
//	private Tweet tweet;

//	@ManyToOne
//	@JoinColumn(name = "followingUserId")
//	private User followingUser;

	
}
