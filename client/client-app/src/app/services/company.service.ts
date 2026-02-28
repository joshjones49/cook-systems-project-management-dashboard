import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

export interface Company {
  id: number;
  name: string;
  description: string;
}

@Injectable({
  providedIn: 'root',
})
export class CompanyService {
  currentCompanySource = new BehaviorSubject<Company | null>(null);
  currentCompany = this.currentCompanySource.asObservable();

  constructor(private http: HttpClient) {
    this.loadCurrentCompany();
  }

  private loadCurrentCompany() {
    try {
      const company = localStorage.getItem('currentCompany');
      this.currentCompanySource.next(company ? JSON.parse(company) : null);
    } catch (e) {
      console.error('Failed to parse currentCompany from localStorage', e);
      this.currentCompanySource.next(null);
    }
  }

  getAllCompanies(): Observable<Company[]> {
    return this.http.get<Company[]>('http://localhost:8080/company');
  }

  setCurrentCompany(company: Company) {
    this.currentCompanySource.next(company);
    localStorage.setItem('currentCompany', JSON.stringify(company));
  }
}
