package com.cooksys.server.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Company {

  @Id
  @GeneratedValue
  private Long id;

  private String name;
  private String description;

  @OneToMany(mappedBy = "company")
  private List<Team> teams;

  @OneToMany(mappedBy = "company")
  private List<Announcement> announcements;

  @ManyToMany(mappedBy="companies")
	private List<User> users;

  @Override
  public String toString() {
    return "Company{id=" + id + ", name=" + name + ", description=" + description + "}";
  }
  
}
