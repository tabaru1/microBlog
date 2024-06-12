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
public class Follow  {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

//	@Column(name = "userId", nullable = false)
//	private String followed;
//	
//	@Column(name = "followingUserId", nullable = false)
//    private String following;
	
	
	@ManyToOne
	@JoinColumn(name = "followingUserId")
	private Follow follow;
	
	@ManyToOne
	@JoinColumn(name = "tweetId")
	private Tweet tweet;
	
//	@ManyToOne
//	@JoinColumn(name = "followingUserId")
//	private User follows;

	
}
