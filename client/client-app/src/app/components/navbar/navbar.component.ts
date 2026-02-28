import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterLink, RouterLinkActive } from '@angular/router';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink, RouterLinkActive, CommonModule],
  providers: [UserService],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent implements OnInit {
  displayName: string = '';
  isAdmin: boolean = false;
  showNavbar = true;

  constructor(private userService: UserService, private router: Router) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: any) => {
      this.showNavbar = !(event.url === '/' || event.url === '/login');
    });
  }

  ngOnInit(): void {
    this.userService.currentUser.subscribe((user) => {
      if (user) {
        console.log('Current user:', user);
        if (user.admin) {
          this.isAdmin = true;
          this.displayName = 'ACTING AS ADMIN';
        } else {
          this.displayName =
            user.profile.firstName + ' ' + user.profile.lastName[0] + '.';
        }
      }
    });
  }

  handleLogout() {
    this.userService.logout();
    this.router.navigate(['login']);
  }
}
