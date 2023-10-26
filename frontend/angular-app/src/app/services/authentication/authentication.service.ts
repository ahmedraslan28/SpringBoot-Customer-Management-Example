import { AuthenticationResponse } from './../../models/authentication-response';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthenticationRequest } from 'src/app/models/authentication-request';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private httpClient: HttpClient) {}

  private readonly authUrl = `${environment.api.baseUrl}/${environment.api.authUrl}`;

  login(authRequest : AuthenticationRequest) : Observable<AuthenticationResponse>{
    return this.httpClient.post<AuthenticationResponse>(this.authUrl, authRequest) ;
  }
}
