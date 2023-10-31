import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CustomerDTO } from 'src/app/models/customer-dto';
import {
  ConfirmationService,
  MessageService,
  ConfirmEventType,
} from 'primeng/api';
import { CustomerService } from 'src/app/services/customer/customer.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-customer-card',
  templateUrl: './customer-card.component.html',
  styleUrls: ['./customer-card.component.scss'],
  providers: [ConfirmationService, MessageService],
})
export class CustomerCardComponent {
  constructor(
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private customerService: CustomerService,
    private router : Router,
  ) {}

  @Input()
  customer: CustomerDTO = {};

  @Input()
  customerIndex: number = 0;

  @Output()
  updateEventChange = new EventEmitter<boolean>();

  @Input()
  operation: 'CREATE' | 'UPDATE' = 'UPDATE';

  @Output()
  operationChange = new EventEmitter<'CREATE' | 'UPDATE'>();

  @Input()
  loggedInUser: CustomerDTO = {};

  @Output()
  updatePage = new EventEmitter<void>();

  @Output()
  customerToUpdate = new EventEmitter<CustomerDTO>();

  onUpdateClick() {
    this.updateEventChange.emit(true);
    this.operationChange.emit('UPDATE');
    this.customerToUpdate.emit(this.customer) ;
  }

  get customerImage(): string {
    const gender = this.customer.gender === 'MALE' ? 'men' : 'women';
    return `https://randomuser.me/api/portraits/${gender}/${this.customerIndex}.jpg`;
  }

  confirmDelete() {
    this.confirmationService.confirm({
      message: `Are you sure you want to delete ${this.customer.name} ?
      you can't undo this action afterwards.`,
      header: 'Delete Customer',
      icon: 'pi pi-info-circle',
      accept: () => {
        this.customerService.deleteCustomer(this.customer.id).subscribe({
          next: (res) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Confirmed',
              detail: `${this.customer.name} Successfully deleted`,
            });
            setTimeout(() => {
              this.updatePage.emit();
            }, 3000) ;
            if(this.customer.id === this.loggedInUser.id){
              localStorage.removeItem('token') ;
              this.router.navigate(['login'])
            }
          },
          error: (err) => {
            this.messageService.add({
              severity: 'error',
              summary: 'error',
              detail: err.error.message,
            });
          },
        });
      },
      reject: (type: ConfirmEventType) => {
        switch (type) {
          case ConfirmEventType.REJECT:
            this.messageService.add({
              severity: 'error',
              summary: 'Rejected',
              detail: 'You have rejected',
            });
            break;
          case ConfirmEventType.CANCEL:
            this.messageService.add({
              severity: 'warn',
              summary: 'Cancelled',
              detail: 'You have cancelled',
            });
            break;
        }
      },
    });
  }
}
