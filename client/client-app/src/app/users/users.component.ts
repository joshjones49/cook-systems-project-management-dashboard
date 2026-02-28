import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { Company, CompanyService } from '../services/company.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-users',
  imports: [CommonModule, FormsModule],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit {
  currentCompany: Company | null = null;
  users: any;
  confirmPassword = '';

  isModalOpen = false;
  newUser = {
    profile: {
      firstName: '',
      lastName: '',
      email: '',
      phone: '',
    },
    credentials: {
      username: '',
      password: '',
    },
    admin: false
  };

  constructor(private userService: UserService, private companyService: CompanyService) { };

  ngOnInit(): void {
    this.companyService.currentCompany.subscribe(
      (company) => (this.currentCompany = company)
    );
    console.log(this.currentCompany);
    if (this.currentCompany) {
      this.userService.fetchUsers(this.currentCompany.id).subscribe({
        next: (users) => {
          this.users = users;
          console.log('Users loaded:', users);
        },
        error: (error) => {
          console.error('Error fetching users:', error);
        }
      });
    }
  }


  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
    this.resetForm();
  }

  resetForm() {
    this.newUser = {
      profile: {
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
      },
      credentials: {
        username: '',
        password: '',
      },
      admin: false
    };
  }

  createUser() {
    if (this.newUser.credentials.password !== this.confirmPassword) {
      console.error('Passwords do not match');
      return;
    }

    if (this.newUser && this.currentCompany) {
      this.userService.createUser(this.newUser, this.currentCompany.id).subscribe({
        next: (response) => {
          this.closeModal();
          this.refreshUsers();
        },
        error: (error) => {
          console.error('Error fetching users:', error);
        }
      });

    }
  }

  private refreshUsers() {
    if (this.currentCompany) {
      this.userService.fetchUsers(this.currentCompany.id).subscribe({
        next: (users) => {
          this.users = users;
        },
        error: (error) => {
          console.error('Error refreshing users:', error);
        }
      });
    }
  }
}


