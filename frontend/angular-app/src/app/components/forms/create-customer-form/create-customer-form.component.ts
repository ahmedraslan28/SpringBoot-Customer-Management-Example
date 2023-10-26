import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CustomerRegistrationRequest } from 'src/app/models/customer-registration-request';

@Component({
  selector: 'app-create-customer-form',
  templateUrl: './create-customer-form.component.html',
  styleUrls: ['./create-customer-form.component.scss'],
})
export class CreateCustomerFormComponent {
  @Output()
  onCancel = new EventEmitter<void>();

  @Output()
  submit: EventEmitter<CustomerRegistrationRequest> =
    new EventEmitter<CustomerRegistrationRequest>();

  customerRegistrationRequest: CustomerRegistrationRequest = {};

  cancel() {
    this.onCancel.emit();
  }

  onSubmit() {
    this.submit.emit(this.customerRegistrationRequest);
  }

  get isCustomerValid(): boolean {
    return (
      this.hasLength(this.customerRegistrationRequest.name) &&
      this.hasLength(this.customerRegistrationRequest.email) &&
      this.hasLength(this.customerRegistrationRequest.password) &&
      this.hasLength(this.customerRegistrationRequest.gender) &&
      this.customerRegistrationRequest.age !== undefined &&
      this.customerRegistrationRequest.age > 16 &&
      this.customerRegistrationRequest.age < 100
    );
  }

  private hasLength(input: string | undefined): boolean {
    return input !== null && input !== undefined && input.length > 0;
  }
}
