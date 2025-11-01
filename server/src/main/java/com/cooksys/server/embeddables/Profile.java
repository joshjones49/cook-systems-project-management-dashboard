package com.cooksys.server.embeddables;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;



@Embeddable
@Getter
@Setter
public class Profile {
  private String firstName;
  private String lastName;

  @Column(nullable = false)
  private String email;
  private String phone;
}
