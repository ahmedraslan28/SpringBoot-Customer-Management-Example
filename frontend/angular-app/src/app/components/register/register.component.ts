import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CustomerRegistrationRequest } from '../../models/customer-registration-request';
import { CustomerService } from '../../services/customer/customer.service';
import { AuthenticationService } from '../../services/authentication/authentication.service';
import { AuthenticationRequest } from '../../models/authentication-request';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  errorMsg = '';
  customer: CustomerRegistrationRequest = {};
  constructor(
    private router: Router,
    private customerService: CustomerService,
    private authenticationService: AuthenticationService
  ) {}

  login() {
    this.router.navigate(['login']);
  }

  createAccount() {
    this.customerService.createCustomer(this.customer).subscribe({
      next: (res) => {
        localStorage.setItem('token', res.token);
        this.router.navigate(['dashboard']);
        // const authReq: AuthenticationRequest = {
        //   username: this.customer.email,
        //   password: this.customer.password,
        // };
        // this.authenticationService.login(authReq).subscribe({
        //   next: (authenticationResponse) => {
        //     localStorage.setItem('token', authenticationResponse.token);
        //     this.router.navigate(['dashboard']);
        //   },
      },
      error: (err) => {
        this.errorMsg = err.error.message;
      },
    });
  }
}
