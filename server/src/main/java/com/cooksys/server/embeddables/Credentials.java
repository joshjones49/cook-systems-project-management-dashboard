package com.cooksys.server.embeddables;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;


@Embeddable
@Getter
@Setter
public class Credentials {

  @Column(nullable = false, unique = true)
  private String username;

  private String password;

}