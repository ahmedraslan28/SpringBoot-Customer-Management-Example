import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateCustomerFormComponent } from './create-customer-form.component';

describe('CreateCustomerFormComponent', () => {
  let component: CreateCustomerFormComponent;
  let fixture: ComponentFixture<CreateCustomerFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateCustomerFormComponent]
    });
    fixture = TestBed.createComponent(CreateCustomerFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
