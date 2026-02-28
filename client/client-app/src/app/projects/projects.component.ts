import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Project, ProjectService } from '../services/project.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User, UserService } from '../services/user.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-projects',
  imports: [CommonModule, FormsModule],
  templateUrl: './projects.component.html',
  styleUrl: './projects.component.css',
})
export class ProjectsComponent implements OnInit {
  projects: Project[] = [];
  errorMessage: string | null = null;
  currentTeamId: number | null = null;
  isAdmin: boolean | null = null;
  showModal: boolean = false;
  newProject: Project = {
    name: '',
    description: '',
    active: false,
  };
  isEditMode = false;
  editingProject: Project | null = null;
  currentUser: any;
  noProjects: boolean = false;
  teamName: any;

  constructor(
    private projectService: ProjectService,
    private route: ActivatedRoute,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.handleLocalStorage();
    const teamIdParam = this.route.snapshot.paramMap.get('teamId');

    if (teamIdParam) {
      const teamId: number = parseInt(teamIdParam, 10);
      this.currentTeamId = teamId;
      console.log('Initial route parameter "teamId":', teamId);

      this.projectService.fetchTeamProjects(teamId).subscribe({
        next: (projects) => {
          this.projects = projects;
          this.noProjects = this.projects.length === 0 ? true : false;
          console.log('Projects:', projects);
          this.errorMessage = null;
        },
        error: (error) => {
          this.errorMessage = 'Error fetching projects';
          console.log('Error fetching projects', error);
        },
      });
    } else {
      this.errorMessage = 'Team ID is missing from the route parameters';
      console.log('Error: Team ID is missing');
    }
  }
  handleNavBack() {
    this.router.navigate(['/teams']);
  }

  openModal(project?: Project) {
    if (project) {
      this.isEditMode = true;
      this.editingProject = project;
      this.newProject = { ...project };
    } else {
      this.isEditMode = false;
      this.editingProject = null;
      this.newProject = { name: '', description: '', active: false };
    }
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
    this.isEditMode = false;
    this.editingProject = null;
    this.newProject = { name: '', description: '', active: false };
  }

  submitProject() {
    if (this.isEditMode && this.editingProject) {
      this.projectService.updateProject(this.editingProject.id!, this.newProject).subscribe({
        next: (response) => {
          const index = this.projects.findIndex(p => p.id === this.editingProject!.id);
          if (index !== -1) {
            this.projects[index] = response;
          }
          this.closeModal();
        },
        error: (error) => {
          console.error('Error updating project:', error);
        }
      });
    } else {

      this.projectService
        .createProject(this.currentTeamId!, this.newProject)
        .subscribe({
          next: (response) => {
            console.log('Project created successfully:', response);
            this.projects.push(response);
            this.noProjects = this.projects.length > 0 ? false : true;
            this.closeModal();
            this.newProject = { name: '', description: '', active: false };
          },
          error: (error) => {
            console.error('Error creating project:', error);
          },
        });
    }
  }

  handleLocalStorage() {
    try {
      if (typeof window !== 'undefined' && window.localStorage) {
        console.log('localStorage is available');
        const user = localStorage.getItem('currentUser');
        this.currentUser = user ? JSON.parse(user) : null;
        const nameOfTeam = localStorage.getItem('teamName')
        this.teamName = nameOfTeam ? JSON.parse(nameOfTeam) : null;
        if (this.currentUser) {
          this.isAdmin = this.currentUser.admin;
        }
      }
    } catch (error) {
      console.error('Error accessing localStorage:', error);
    }
  }

}
