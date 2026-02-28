import { Component, OnInit } from '@angular/core';
import { TeamsService } from '../services/teams.service';
import { CommonModule } from '@angular/common';
import { Team } from './teams.model';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { Company } from '../services/company.service';
import { User } from '../services/user.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-teams',
  imports: [CommonModule, FormsModule],
  templateUrl: './teams.component.html',
  styleUrl: './teams.component.css',
})
export class TeamsComponent implements OnInit {
  teams: Team[] = [];
  error: string | null = null;
  noTeams: boolean = false;

  currentUser: any;
  currentCompany: any;

  isModalOpen = false;
  availableUsers: any[] = [];
  selectedUsers: any[] = [];
  newTeam = {
    name: '',
    description: '',
    selectedUsers: [] as any[],
  };

  constructor(
    private teamsService: TeamsService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getUserFromLocalStorage();

    if (this.currentUser.admin) {
      this.getCompanyFromLocalStorage();
      if (this.currentCompany) {
        this.fetchCompanyTeams();
        this.fetchAvailableUsers();
      }
    } else {
      if (this.currentUser) {
        this.fetchUserTeams();
        this.fetchAvailableUsers();
      }
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
    this.newTeam = {
      name: '',
      description: '',
      selectedUsers: [] as any[],
    };
  }

  selectUser(user: any) {
    if (!this.selectedUsers.find((u) => u.id === user.id)) {
      this.selectedUsers.push(user);
      this.newTeam.selectedUsers = [...this.selectedUsers];
    }
  }

  removeSelectedUser(user: any) {
    this.selectedUsers = this.selectedUsers.filter((u) => u.id !== user.id);
    this.newTeam.selectedUsers = [...this.selectedUsers];
  }

  fetchAvailableUsers() {
    if (this.currentCompany) {
      this.userService.fetchUsers(this.currentCompany.id).subscribe({
        next: (users) => {
          this.availableUsers = users;
          console.log('available users' + this.availableUsers);
        },
        error: (error) => {
          console.error('Error fetching users:', error);
        },
      });
    }
  }

  createTeam() {
    console.log('create team mthod called', this.newTeam);
    if (this.newTeam.name && this.currentUser) {
      console.log(this.newTeam.selectedUsers);

      const formattedUsers = this.newTeam.selectedUsers.map((user) => ({
        id: user.id,
        credentials: {
          username: user?.username,
        },
        profile: {
          firstName: user?.profile?.firstName,
          lastName: user?.profile?.lastName,
          email: user?.profile?.email,
          phone: user?.profile?.phone,
        },
      }));

      const teamData = {
        name: this.newTeam.name,
        description: this.newTeam.description,
        users: formattedUsers,
      };

      this.teamsService.createTeam(teamData, this.currentCompany.id).subscribe({
        next: (response) => {
          this.closeModal();
          this.selectedUsers = [];
          if (response) {
            this.teams = [response, ...this.teams];
          }
        },
        error: (error) => {
          console.error('Error creating team:', error);
        },
      });
    }
  }

  getUserFromLocalStorage() {
    try {
      if (typeof window !== 'undefined' && window.localStorage) {
        console.log('localStorage is available');
        const user = localStorage.getItem('currentUser');
        this.currentUser = user ? JSON.parse(user) : null;
        const company = localStorage.getItem('currentCompany');
        this.currentCompany = company ? JSON.parse(company) : null;
      }
    } catch (error) {
      console.error('Error accessing localStorage:', error);
    }
  }

  getCompanyFromLocalStorage() {
    try {
      if (typeof window !== 'undefined' && window.localStorage) {
        console.log('localStorage is available');
        const company = localStorage.getItem('currentCompany');
        this.currentCompany = company ? JSON.parse(company) : null;
        console.log('Succesfully pulled company:', company);
      }
    } catch (error) {
      console.error('Error accessing currentCompany from localStorage:', error);
    }
  }

  fetchUserTeams(): void {
    this.teamsService.fetchUserTeams(this.currentUser.id).subscribe({
      next: (teams) => {
        this.teams = teams;
        this.error = null;
      },
      error: (err) => {
        this.error = 'Failed to load teams';
        this.teams = [];
        console.error(err);
      },
    });
  }

  fetchCompanyTeams(): void {
    this.teamsService.fetchCompanyTeams(this.currentCompany.id).subscribe({
      next: (teams) => {
        this.teams = teams;
        this.noTeams = this.teams.length === 0 ? true : false;
        this.error = null;
      },
      error: (err) => {
        this.error = 'Failed to load teams';
        this.teams = [];
        console.error(err);
      },
    });
  }

  goToProjects(teamId: number, teamName: string) {
    localStorage.setItem('teamName', JSON.stringify(teamName));
    this.router.navigate(['projects', teamId]);
  }
}
