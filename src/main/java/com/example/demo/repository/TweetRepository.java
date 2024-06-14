package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Integer>{
	public List<Tweet> findByUserIdOrderByCreatedAtDesc(int follwingUserId);
	public List<Tweet> findByUserIdInOrderByCreatedAtDesc(List<Integer> userIds);
}
