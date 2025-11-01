package com.cooksys.server.entities;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Announcement {

  @Id
  @GeneratedValue
  private Long id;

  @CreationTimestamp
  private Timestamp date;

  private String title;

  private String message;

  @ManyToOne
  @JoinColumn(name = "company_announcements")
  private Company company;

  @ManyToOne
  @JoinColumn(name = "user_announcements")
  private User author;


  
}
