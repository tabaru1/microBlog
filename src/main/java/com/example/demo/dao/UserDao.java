package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.entity.Tweet;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@Repository
public class UserDao implements BaseDao<User> {
	@Autowired
	UserRepository repository;

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User findById(Integer id) throws DataNotFoundException {
		return this.repository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	@Override
	public void save(User user) {
		this.repository.save(user);
	}

	@Override
	public void deleteById(Integer id) {
		try {
			User user = this.findById(id);
			this.repository.deleteById(user.getId());
		} catch (DataNotFoundException e) {
			System.out.println("no data");
		}
	}

	public User findByEmail(String email) throws DataNotFoundException {
		User user = this.repository.findByEmail(email);
		if (user == null) {
			throw new DataNotFoundException();
		}
		return user;
	}
	
	public List<User> findByIdNot(Integer id) throws DataNotFoundException {
		List<User> users = this.repository.findByIdNot(id);
		if (users == null) {
			throw new DataNotFoundException();
		}
		return users;
	}
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	List<Tweet> tweets;

//	public List<User> findByFollwingUserId(Integer id) throws DataNotFoundException {
//		List<User> follows = this.repository.findByFollwingUserId(id);
//		if (follows == null) {
//			throw new DataNotFoundException();
//		}
//		return follows;
//	}
	
}
