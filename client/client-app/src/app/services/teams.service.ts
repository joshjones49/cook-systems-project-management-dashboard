import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Team } from '../teams/teams.model';

@Injectable({
  providedIn: 'root'
})
export class TeamsService {

  private teamsURL: string = 'http://localhost:8080/teams'

  constructor(private http: HttpClient) {}

  fetchUserTeams(userId: number): Observable<Team[]> {
    return new Observable<Team[]>(observer => {
      this.http.get<any[]>(`${this.teamsURL}/user/${userId}`, {
        headers: { Accept: 'application/json' }
      }).subscribe({
        next: (rawTeams) => {
          const teams = rawTeams.map(team => new Team(
            team.id, 
            team.name, 
            team.description,
            team.company,
            team.users,
            team.projects
          ));
          observer.next(teams);
          observer.complete();
        },
        error: (err) => {
          console.error('Error fetching teams:', err);
          observer.error(new Error('Failed to fetch teams'));
        }
      });
    });
  }

  fetchCompanyTeams(companyId: number): Observable<Team[]> {
    return new Observable<Team[]>(observer => {
      this.http.get<any[]>(`${this.teamsURL}/company/${companyId}`, {
        headers: { Accept: 'application/json' }
      }).subscribe({
        next: (rawTeams) => {
          const teams = rawTeams.map(team => new Team(
            team.id,
            team.name,
            team.description,
            team.company,
            team.users,
            team.projects
          ));
          observer.next(teams);
          observer.complete();
        },
        error: (err) => {
          console.error('Error fetching teams:', err);
          observer.error(new Error('Failed to fetch teams'));
        }
      });
    });
  }


  createTeam(teamObj: any, companyId: any): Observable<any> {
    const url = `http://localhost:8080/teams/${companyId}`;
    return this.http.post(url, teamObj, {
      headers: { Accept: 'application/json' },
    });
  }
  
}
