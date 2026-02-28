import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Company } from './company.service';

export interface User {
  id: number;
  profile: any;
  username: string;
  active: boolean;
  admin: boolean;
  status: string;
  companies: Company[];
  teams: any[];
}

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private currentUserSource = new BehaviorSubject<User | null>(null);

  // Components can subscribe to this observable to ensure they have latest user
  // If page refreshes, constructor will set currentUser, and components will get that data
  // via their subscription
  currentUser: Observable<User | null> = this.currentUserSource.asObservable();

  constructor(private http: HttpClient) {
    this.loadCurrentUser();
  }

  private loadCurrentUser() {
    try {
      const user = localStorage.getItem('currentUser');
      this.currentUserSource.next(user ? JSON.parse(user) : null);
    } catch (e) {
      console.error('Failed to parse currentUser from localStorage', e);
      this.currentUserSource.next(null);
    }
  }

  loginUser(credentialsObj: any): Observable<any> {
    const url = 'http://localhost:8080/users/login';
    return this.http.post(url, credentialsObj, {
      headers: { Accept: 'application/json' },
    });
  }

  setCurrentUser(user: User) {
    localStorage.setItem('currentUser', JSON.stringify(user));
    this.currentUserSource.next(user);
  }

  logout() {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('currentCompany');
    this.currentUserSource.next(null);
  }

  fetchUsers(companyId: any): Observable<any> {
    const url = `http://localhost:8080/users/${companyId}`;
    return this.http.get(url, {
      headers: { Accept: 'application/json' },
    });
  }

  createUser(userObj: any, companyId: any): Observable<any> {
    const url = `http://localhost:8080/users/${companyId}`;
    return this.http.post(url, userObj, {
      headers: { Accept: 'application/json' },
    });
  }
}
