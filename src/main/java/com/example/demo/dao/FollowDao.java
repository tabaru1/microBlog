package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.entity.Follow;
import com.example.demo.entity.User;
import com.example.demo.repository.FollowRepository;

@Repository
public class FollowDao implements BaseDao<Follow> {
	@Autowired
	FollowRepository repository;

	@Override
	public List<Follow> findAll() {
		return repository.findAll();
	}

	@Override
	public Follow findById(Integer id) throws DataNotFoundException {
		return repository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	@Override
	public void save(Follow follow) {
		this.repository.save(follow);
	}

	@Override
	public void deleteById(Integer id) {
		try {
			Follow follow = this.findById(id);
			this.repository.deleteById(follow.getId());
		} catch (DataNotFoundException e) {
			System.out.println("no data");
		}
	}

	public Follow findByFollowingAndFollowed(User following, User followed) throws DataNotFoundException{
		return repository.findByFollowingAndFollowed(following, followed);
	}


	public List<Follow> findByUser(User user) throws DataNotFoundException {
		List<Follow> follows = repository.findByFollowed(user);
		if (follows == null) {
			throw new DataNotFoundException();
		}
		return follows;
	}

	public List<Integer> findByFollowingIdAndFollowedId(Integer userId, Integer followingUserId) {
		return repository.findByFollowingIdAndFollowedId(userId, followingUserId);
	}

	public List<Follow> findByFollowing(User following) {
		return repository.findByFollowing(following);
	}

	
//	public List<Follow> findByUserId(Integer follwingUserId) throws DataNotFoundException {
//		List<Follow> follows = this.repository.findByFollwingUserId(follwingUserId);
//		if (follows == null) {
//			throw new DataNotFoundException();
//		}
//		return follows;
//	}
//
//	public List<Follow> findByFollwingUserId(Integer follwingUserId) {
//		return repository.findByFollwingUserId(follwingUserId);
//	}


}
