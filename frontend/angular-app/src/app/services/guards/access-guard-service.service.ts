import { Injectable, inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class AccessGuardServiceService {
  constructor(private router: Router) {}

  canNavigateDashBoard(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const token = localStorage.getItem('token');
    if (token) {
      const jwtHelper = new JwtHelperService();
      if (!jwtHelper.isTokenExpired(token)) return true;
    }
    this.router.navigate(['login']);
    return false;
  }

  canNavigateLogin(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const token = localStorage.getItem('token');
    if (token) {
      const jwtHelper = new JwtHelperService();
      if (!jwtHelper.isTokenExpired(token)) {
        console.log("it's valid ")
        this.router.navigate(['dashboard']);
        return false ;
      }
    }
    return true;
  }

  canNavigateRegister(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const token = localStorage.getItem('token');
    if (token) {
      const jwtHelper = new JwtHelperService();
      if (!jwtHelper.isTokenExpired(token)) {
        this.router.navigate(['dashboard']);
        return false ;
      }
    }
    return true;
  }
}

