import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthenticationRequest } from 'src/app/models/authentication-request';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [MessageService],
})
export class LoginComponent {
  authenticationRequest: AuthenticationRequest = {};

  constructor(
    private authenticationService: AuthenticationService,
    private messageService: MessageService,
    private router: Router
  ) {
  }

  login() {
    this.authenticationService.login(this.authenticationRequest).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        this.router.navigate(['dashboard']);
      },
      error: (err) => {
        this.messageService.add({
          key: 'bc',
          severity: 'error',
          summary: 'error',
          detail: err.error.message,
        });
      },
    });
  }

  register() {
    this.router.navigate(['register']);
  }
}
