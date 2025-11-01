package com.cooksys.server.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Project {

  @Id
  @GeneratedValue
  private Long id;

  private String name;
  private String description;
  private boolean active;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "team")
  private Team team;

}
