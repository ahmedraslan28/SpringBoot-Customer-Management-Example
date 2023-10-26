import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { CustomerDTO } from 'src/app/models/customer-dto';
import { CustomerService } from 'src/app/services/customer/customer.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  customer: CustomerDTO = {};

  sideBarVisible: boolean = false;

  customers: CustomerDTO[] = [];

  operation: 'CREATE' | 'UPDATE' = 'CREATE';

  constructor(private customerService: CustomerService) {}
  ngOnInit(): void {
    this.findAllCustomers();
  }

  private findAllCustomers() {
    this.customerService.getCustomers().subscribe({
      next: (res) => {
        this.customers = res;
      },
    });
  }

  reloadCustomers() {
    this.findAllCustomers();
  }

  updateCustomer(customerDTO: CustomerDTO) {
    this.customer = customerDTO;
  }

  setOperation(val: 'UPDATE' | 'CREATE') {
    this.operation = val;
  }

  setSideBar(val: boolean) {
    console.log(val);
    this.sideBarVisible = val;
  }

  onClick() {
    this.sideBarVisible = true;
    this.operation = 'CREATE';
    this.customer = {} ;
  }
}
