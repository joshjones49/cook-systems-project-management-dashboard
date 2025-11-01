package com.cooksys.server.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Team {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String description;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "company")
	private Company company;

	@OneToMany(mappedBy = "team")
	private List<Project> projects = new ArrayList<>();

	@ManyToMany(mappedBy = "teams")
	private List<User> users = new ArrayList<>();

	@Override
	public String toString() {
		return "Team{id=" + id + ", name=" + name + ", description=" + description + "}";
	}

}
