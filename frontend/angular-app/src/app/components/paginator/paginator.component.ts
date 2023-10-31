import { Component, EventEmitter, Output, Input } from '@angular/core';
import { CustomerService } from 'src/app/services/customer/customer.service';

export interface PageEvent {
  first: number;
  rows: number;
  page: number;
  pageCount: number;
}

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.scss'],
})
export class PaginatorComponent {
  first: number = 0;

  rows: number = 10;

  @Input()
  totalRecords = 0 ;


  constructor(private customerService : CustomerService){}


  @Output()
  pageChange = new EventEmitter<PageEvent>() ;


  onPageChange(event: any) {
    this.first = event.first;
    this.rows = event.rows;
    this.pageChange.emit(event) ;
    console.log(event)

  }
}
