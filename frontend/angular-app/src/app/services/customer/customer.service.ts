import { CustomerRegistrationRequest } from './../../models/customer-registration-request';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from 'src/app/models/authentication-response';
import { CustomerDTO } from 'src/app/models/customer-dto';
import { CustomerUpdateRequest } from 'src/app/models/customer-update-request';
import { environment } from 'src/environments/environment';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  constructor(private httpClient: HttpClient) {}

  private readonly customersUrl = `${environment.api.baseUrl}/${environment.api.customersUrl}`;

  getCustomers(offset?: number, limit?: number): Observable<CustomerDTO[]> {
    if (!offset && !limit)
      return this.httpClient.get<CustomerDTO[]>(this.customersUrl);

    return this.httpClient.get<CustomerDTO[]>(
      `${this.customersUrl}?limit=${limit}&&offset=${offset}`
    );
  }

  getCustomerByEmail(email: string): Observable<CustomerDTO[]> {
    return this.httpClient.get<CustomerDTO[]>(
      `${this.customersUrl}?email=${email}`
    );
  }

  createCustomer(
    customerRegistrationRequest: CustomerRegistrationRequest
  ): Observable<AuthenticationResponse> {
    return this.httpClient.post<AuthenticationResponse>(
      this.customersUrl,
      customerRegistrationRequest
    );
  }

  deleteCustomer(id: number | undefined): Observable<void> {
    return this.httpClient.delete<void>(`${this.customersUrl}/${id}`);
  }

  updateCustomer(
    id: number | undefined,
    customer: CustomerUpdateRequest
  ): Observable<void> {
    return this.httpClient.put<void>(`${this.customersUrl}/${id}`, customer);
  }

  getCustomerFromToken(): Observable<CustomerDTO[]> {
    const token = localStorage.getItem('token');
    let email = '';
    if (token) {
      const jwtHelper = new JwtHelperService();
      if (!jwtHelper.isTokenExpired(token))
        email = jwtHelper.decodeToken(token).sub;
    }
    return this.getCustomerByEmail(email);
  }


  countCustomers(): Observable<number> {
    return this.httpClient.get<number>(`${this.customersUrl}/count`);
  }
}
