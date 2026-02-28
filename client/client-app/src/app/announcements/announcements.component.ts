import { Component } from '@angular/core';
import {
  Announcement,
  AnnouncementsService,
} from '../services/announcements.service';
import { Company, CompanyService } from '../services/company.service';
import { CommonModule } from '@angular/common';
import { User, UserService } from '../services/user.service';
import { FormsModule } from '@angular/forms'; // Add FormsModule

@Component({
  selector: 'app-announcements',
  imports: [CommonModule, FormsModule],
  templateUrl: './announcements.component.html',
  styleUrl: './announcements.component.css',
})
export class AnnouncementsComponent {
  announcements: Announcement[] = [];
  currentCompany: Company | null = null;
  errorMessage: String | null = null;
  isAdmin: boolean | null = null;
  showModal: boolean = false;
  currentUser: User | null = null;
  newAnnouncement: { title: string; message: string } = {
    title: '',
    message: '',
  };

  constructor(
    private companyService: CompanyService,
    private announcementsService: AnnouncementsService,
    private userService: UserService
  ) {}

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

  ngOnInit() {
    this.getCompanyFromLocalStorage();
    this.getUserFromLocalStorage();
    this.announcementsService
      .getAnnouncements(this.currentCompany!.id)
      .subscribe({
        next: (announcements) => {
          this.announcements = announcements;
          this.errorMessage = null;
          console.log(announcements);
        },
        error: (error) => {
          this.errorMessage = `Failed to load announcements for ${this.currentCompany?.name}.`;
          console.error('Error:', error);
        },
      });
  }

  openModal() {
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  submitAnnouncement() {
    if (this.isAdmin && this.currentUser) {
      const announcement: Announcement = {
        title: this.newAnnouncement.title,
        message: this.newAnnouncement.message,
        company: this.currentCompany!,
        author: this.currentUser,
      };

      this.announcementsService
        .createAnnouncement(this.currentCompany!.id, announcement)
        .subscribe({
          next: (returnedAnnouncement) => {
            this.announcements = [returnedAnnouncement, ...this.announcements];
            this.closeModal();
          },
          error: (error) => {
            this.errorMessage = 'Failed to create announcement.';
            console.error('Error:', error);
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
        if (this.currentUser) {
          this.isAdmin = this.currentUser?.admin;
        }
      }
    } catch (error) {
      console.error('Error accessing localStorage:', error);
    }
  }
}
