import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../services/user.service';
import { CommonModule } from '@angular/common';
import { CompanyService } from '../services/company.service';

@Component({
  selector: 'app-login',
  imports: [RouterModule, FormsModule, CommonModule],
  providers: [UserService],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  credentials = {
    username: '',
    password: '',
  };
  errorMessage: string = '';

  constructor(
    private userService: UserService,
    private companyService: CompanyService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  clearError(): void {
    this.errorMessage = '';
  }

  handleLogin(): void {
    this.errorMessage = '';

    if (!this.credentials.username && !this.credentials.password) {
      this.errorMessage = 'Username and password are required';
      return;
    }
    if (!this.credentials.username) {
      this.errorMessage = 'Username is required';
      return;
    }
    if (!this.credentials.password) {
      this.errorMessage = 'Password is required';
      return;
    }

    this.userService.loginUser(this.credentials).subscribe({
      next: (user) => {
        this.userService.setCurrentUser(user);
        if (user.admin) {
          this.router.navigate(['/company-select']);
        } else {
          this.companyService.setCurrentCompany(user.companies[0]);
          this.router.navigate(['/announcements']);
        }
      },
      error: (error) => {
        console.error('Login failed:', error);
        this.errorMessage =
          'Login failed. Please check your credentials and try again.';
      },
    });
  }
}
