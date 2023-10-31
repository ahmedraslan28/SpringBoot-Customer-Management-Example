import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { CustomerDTO } from 'src/app/models/customer-dto';
import { CustomerService } from 'src/app/services/customer/customer.service';

@Component({
  selector: 'app-header-bar',
  templateUrl: './header-bar.component.html',
  styleUrls: ['./header-bar.component.scss'],
})
export class HeaderBarComponent {
  constructor(private router: Router) {}

  items: Array<MenuItem> = [
    { label: 'Profile', icon: 'pi pi-user' },
    { label: 'Settings', icon: 'pi pi-cog' },
    { separator: true },
    {
      label: 'Sign out',
      icon: 'pi pi-sign-out',
      command: () => this.logout(),
    },
  ];

  @Input()
  loggedInUser: CustomerDTO = {};

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['login']);
  }
}
