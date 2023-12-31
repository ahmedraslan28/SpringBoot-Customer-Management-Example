import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateCustomerFormComponent } from './update-customer-form.component';

describe('UpdateCustomerFormComponent', () => {
  let component: UpdateCustomerFormComponent;
  let fixture: ComponentFixture<UpdateCustomerFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateCustomerFormComponent]
    });
    fixture = TestBed.createComponent(UpdateCustomerFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
