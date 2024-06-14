package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Follow;
import com.example.demo.entity.User;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
	Follow findByFollowingAndFollowed(User following, User followed);

	public List<Integer> findByFollowingIdAndFollowedId(Integer userId, Integer followingUserId);

	//	 public int findByfollowingUserId(Integer following);
	public List<Follow> findByFollowed(User followed);

	public List<Follow> findByFollowing(User following);
	//	List<Follow> findByUserId(Integer userId);

}
