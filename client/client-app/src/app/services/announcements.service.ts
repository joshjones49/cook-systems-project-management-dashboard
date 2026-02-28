import { Injectable } from '@angular/core';
import { Company } from './company.service';
import { User } from './user.service';
import { HttpClient } from '@angular/common/http';

export interface Announcement {
  title: string;
  message: string;
  company: Company;
  author: User;
  date?: Date;
}

@Injectable({
  providedIn: 'root',
})
export class AnnouncementsService {
  constructor(private http: HttpClient) {}

  getAnnouncements(companyId: number) {
    return this.http.get<Announcement[]>(
      `http://localhost:8080/announcements/${companyId}`
    );
  }
  createAnnouncement(companyId: number, announcement: Announcement) {
    return this.http.post<Announcement>(
      `http://localhost:8080/announcements/${announcement.author.username}/${companyId}`,
      announcement
    );
  }
}
