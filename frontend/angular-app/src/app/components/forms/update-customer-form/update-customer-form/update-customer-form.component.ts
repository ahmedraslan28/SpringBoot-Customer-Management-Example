import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { CustomerDTO } from 'src/app/models/customer-dto';
import { CustomerUpdateRequest } from 'src/app/models/customer-update-request';

@Component({
  selector: 'app-update-customer-form',
  templateUrl: './update-customer-form.component.html',
  styleUrls: ['./update-customer-form.component.scss'],
})
export class UpdateCustomerFormComponent{
  @Output()
  onCancel = new EventEmitter<void>();

  @Input()
  customerUpdateRequest: CustomerUpdateRequest = {};



  @Output()
  submit: EventEmitter<CustomerUpdateRequest> =
    new EventEmitter<CustomerUpdateRequest>();

  onSubmit() {
    this.submit.emit(this.customerUpdateRequest);
  }

  cancel() {
    this.onCancel.emit();
  }

  get isCustomerValid(): boolean {
    return (
      this.hasLength(this.customerUpdateRequest.name) &&
      this.hasLength(this.customerUpdateRequest.email) &&
      this.customerUpdateRequest.age !== undefined &&
      this.customerUpdateRequest.age > 16 &&
      this.customerUpdateRequest.age < 100
    );
  }

  private hasLength(input: string | undefined): boolean {
    return input !== null && input !== undefined && input.length > 0;
  }

}
