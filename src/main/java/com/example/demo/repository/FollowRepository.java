package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Follow;

public interface FollowRepository extends JpaRepository<Follow, Integer>{

//	List<Follow> findByFollwingUserId(Integer follwingUserId);
}
