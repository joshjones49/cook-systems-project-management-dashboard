import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const loginGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  
  try {
    if (typeof window !== 'undefined' && window.localStorage) {
      const currentUser = localStorage.getItem('currentUser');
      if (currentUser) {
        router.navigate(['announcements']);
        return false;
      }
    }
    return true;
  } catch (error) {
    console.error('Error checking localStorage:', error);
    return true;
  }
};
