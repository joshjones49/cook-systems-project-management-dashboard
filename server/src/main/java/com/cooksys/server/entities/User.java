package com.cooksys.server.entities;

import java.util.ArrayList;
import java.util.List;

import com.cooksys.server.embeddables.Credentials;
import com.cooksys.server.embeddables.Profile;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="User_Table")
public class User {

  @Id
  @GeneratedValue
  private Long id;

  @Embedded
  private Credentials credentials;

  @Embedded
  private Profile profile;

  private boolean active;
  private boolean admin;

  private String status = "PENDING";

  @OneToMany(mappedBy = "author")
  private List<Announcement> announcements;

  @ManyToMany()
  @JoinTable(
      name = "user_team",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "team_id")
  )
  private List<Team> teams = new ArrayList<>();

  @ManyToMany()
  @JoinTable(
      name = "user_company",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "company_id")
  )
  private List<Company> companies;
  
}
