package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.entity.Tweet;
import com.example.demo.repository.TweetRepository;

@Repository
public class TweetDao implements BaseDao<Tweet> {
	@Autowired
	TweetRepository repository;

	@Override
	public List<Tweet> findAll() {
		return repository.findAll();
	}

	@Override
	public Tweet findById(Integer id) throws DataNotFoundException {
		return repository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	@Override
	public void save(Tweet tweet) {
		this.repository.save(tweet);
	}

	@Override
	public void deleteById(Integer id) {
		try {
			Tweet tweet = this.findById(id);
			this.repository.deleteById(tweet.getId());
		} catch (DataNotFoundException e) {
			System.out.println("no data");
		}
	}
	
	public List<Tweet> findByUserId(Integer userId) throws DataNotFoundException {
		List<Tweet> tweets = this.repository.findByUserId(userId);
		if (tweets == null) {
			throw new DataNotFoundException();
		}
		return tweets;
	}
	


}
