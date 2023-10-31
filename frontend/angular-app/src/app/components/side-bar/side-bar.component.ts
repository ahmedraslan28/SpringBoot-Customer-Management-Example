import { MessageService } from 'primeng/api';
import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  Output,
  SimpleChanges,
} from '@angular/core';
import { CustomerRegistrationRequest } from 'src/app/models/customer-registration-request';
import { CustomerUpdateRequest } from 'src/app/models/customer-update-request';
import { CustomerService } from 'src/app/services/customer/customer.service';
import { CustomerDTO } from 'src/app/models/customer-dto';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.scss'],
  providers: [MessageService],
})
export class SideBarComponent implements OnChanges {
  constructor(
    private customerService: CustomerService,
    private messageService: MessageService
  ) {}
  ngOnChanges(changes: SimpleChanges): void {
    this.customerToUpdateRequest = {
      name: this.customerToUpdate.name,
      email: this.customerToUpdate.email,
      age: this.customerToUpdate.age,
    };
  }
  @Input()
  customerToUpdate: CustomerDTO = {};

  @Input()
  customerToUpdateRequest: CustomerUpdateRequest = {};

  @Input()
  sideBarVisible: boolean = false;

  @Output()
  sideBarVisibleChange = new EventEmitter<boolean>();

  @Input()
  operation: 'CREATE' | 'UPDATE' = 'CREATE';

  @Output()
  updatePage = new EventEmitter<void>();

  onHide() {
    this.sideBarVisibleChange.emit(false);
  }

  save(customer: CustomerRegistrationRequest | CustomerUpdateRequest) {
    console.log('onSave', customer);
    if (this.customerToUpdate.id) {
      console.log('updated');
      this.customerService
        .updateCustomer(this.customerToUpdate.id, customer)
        .subscribe({
          next: (res) => {
            this.updatePage.emit();
            this.messageService.add({
              key: 'bc',
              severity: 'success',
              summary: 'Successfully Updated',
              detail: `customer updated successfully`,
            });
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
      this.onHide();
      return;
    }
    console.log('created');

    this.customerService.createCustomer(customer).subscribe({
      next: (res) => {
        this.sideBarVisible = false;
        this.updatePage.emit();
        this.messageService.add({
          key: 'bc',
          severity: 'success',
          summary: 'Customer Saved',
          detail: 'created successfully',
        });
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

    this.onHide();
  }
}
