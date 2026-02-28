import { Routes } from '@angular/router';
import { CompanySelectComponent } from './company-select/company-select.component';
import { LoginComponent } from './login/login.component';
import { TeamsComponent } from './teams/teams.component';
import { AnnouncementsComponent } from './announcements/announcements.component';
import { authGuard } from './guard/auth.guard';
import { UsersComponent } from './users/users.component';
import { ProjectsComponent } from './projects/projects.component';
import { adminGuard } from './guard/admin.guard';
import { loginGuard } from './auth/login.guard';

export const routes: Routes = [
  {
    path: 'company-select',
    component: CompanySelectComponent,
    /*canActivate: [adminGuard]*/
  },
  { path: 'login', component: LoginComponent, /*canActivate: [loginGuard]*/ },
  { path: 'teams', component: TeamsComponent, canActivate: [authGuard] },
  {
    path: 'announcements',
    component: AnnouncementsComponent,
    /*canActivate: [authGuard],*/
  },
  { path: 'users', component: UsersComponent, /*canActivate: [adminGuard]*/ },
  { path: 'projects/:teamId', component: ProjectsComponent, /*canActivate: [authGuard]*/ },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];
