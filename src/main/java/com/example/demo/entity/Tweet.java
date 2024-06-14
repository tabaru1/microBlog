package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "tweet")
public class Tweet  extends AbstractEntity{
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	
	@Column(length = 255, nullable = false)
	@NotEmpty(message = "本文は必須入力です")
	private String body;
	
//	@OneToMany(mappedBy = "tweet", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//	List<Follow> follows;

}
