import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';

export interface Project {
  id?: number;
  name: string;
  description: string;
  active: boolean;
}
@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  projectsSource = new BehaviorSubject<Project[] | null>(null);
  projects = this.projectsSource.asObservable();

  constructor(private http: HttpClient) {}

  fetchTeamProjects(teamId: number) {
    return this.http.get<Project[]>(`http://localhost:8080/projects/${teamId}`);
  }

  createProject(teamId: number, newProject: Project) {
    return this.http.post<Project>(
      `http://localhost:8080/projects/${teamId}`,
      newProject
    );
  }

  updateProject(projectId: number, newProject: Project) {
    return this.http.patch<Project>(
      `http://localhost:8080/projects/${projectId}`,
      newProject
    );
  }
}
