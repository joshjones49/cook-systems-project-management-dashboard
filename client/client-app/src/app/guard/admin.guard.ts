import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';


export const adminGuard: CanActivateFn = (route, state) => {
  const router = inject(Router)
  try {
    if (typeof window !== 'undefined' && window.localStorage) {
      const currentUser = localStorage.getItem('currentUser');
      let user = currentUser ? JSON.parse(currentUser) : null;

      if (user && user.admin) {
        return true;
      }
    }
    router.navigate(['announcements'])
    return false
  } catch (error) {
    console.error('Error accessing localStorage:', error);
    return false;
  }
};
