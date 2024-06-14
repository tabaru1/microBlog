package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.dao.BaseDao;
import com.example.demo.dao.FollowDao;
import com.example.demo.entity.Follow;
import com.example.demo.entity.User;

@Service
public class FollowService implements BaseService<Follow> {
	@Autowired
	private BaseDao<Follow> dao;
	@Autowired
	private FollowDao followdao;

	@Override
	public List<Follow> findAll() {
		return dao.findAll();
	}

	@Override
	public Follow findById(Integer id) throws DataNotFoundException {
		return dao.findById(id);
	}

	@Override
	public void save(Follow follow) {
		dao.save(follow);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}
	
	public Follow findByFollowingAndFollowed(User following, User followed)  {
		try {
			return followdao.findByFollowingAndFollowed(following, followed);
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Follow> findByUser(User user) throws DataNotFoundException {
		return followdao.findByUser(user);
	}

	public List<Integer> findByFollowingIdAndFollowedId(Integer userId, Integer followingUserId) {
		return followdao.findByFollowingIdAndFollowedId(userId, followingUserId);

	}

	public List<Follow> findByFollowing(User following) {
		return followdao.findByFollowing(following);
	}
	

}
