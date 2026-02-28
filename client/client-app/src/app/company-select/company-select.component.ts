import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Company, CompanyService } from '../services/company.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-company-select',
  imports: [CommonModule, FormsModule],
  templateUrl: './company-select.component.html',
  styleUrl: './company-select.component.css',
})
export class CompanySelectComponent {
  companies: Company[] = [];
  selectedCompany: string = '';
  errorMessage: string | null = null;

  constructor(private companyService: CompanyService, private router: Router) {}

  ngOnInit() {
    this.companyService.getAllCompanies().subscribe({
      next: (companies) => {
        this.companies = companies;
        this.errorMessage = null;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load companies.';
        console.error('Error:', error);
      },
    });
  }

  selectCompany() {
    const selected = this.companies.find(
      (company) => company.name === this.selectedCompany
    );

    if (selected) {
      this.companyService.setCurrentCompany(selected);
      this.errorMessage = null;
      this.router.navigate(['/announcements']);
    } else {
      this.errorMessage = 'Company does not exist';
    }
  }
}
