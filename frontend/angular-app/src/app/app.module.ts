import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { MenuBarComponent } from './components/menu-bar/menu-bar.component';
import { MenuBarItemComponent } from './components/menu-bar-item/menu-bar-item.component';
import { HeaderBarComponent } from './components/header-bar/header-bar.component';
import { ButtonModule } from 'primeng/button';
import { BadgeModule } from 'primeng/badge';
import { AvatarModule } from 'primeng/avatar';
import { MenuModule } from 'primeng/menu';
import { SidebarModule } from 'primeng/sidebar';
import { SideBarComponent } from './components/side-bar/side-bar.component';
import { CreateCustomerFormComponent } from './components/forms/create-customer-form/create-customer-form.component';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { LoginComponent } from './components/login/login.component';
import {  HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { MessagesModule, } from 'primeng/messages';
import { ToastModule } from 'primeng/toast';
import { HttpInterceptorService } from './services/interceptor/http-interceptor.service';
import { CardModule } from 'primeng/card';
import { CustomerCardComponent } from './components/customer-card/customer-card.component';
import { UpdateCustomerFormComponent } from './components/forms/update-customer-form/update-customer-form/update-customer-form.component';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { RegisterComponent } from './components/register/register.component';
import { PaginatorComponent } from './components/paginator/paginator.component';
import { PaginatorModule } from 'primeng/paginator';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ProgressSpinnerComponent } from './components/progress-spinner/progress-spinner.component';
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MenuBarComponent,
    MenuBarItemComponent,
    HeaderBarComponent,
    SideBarComponent,
    CreateCustomerFormComponent,
    LoginComponent,
    CustomerCardComponent,
    UpdateCustomerFormComponent,
    RegisterComponent,
    PaginatorComponent,
    ProgressSpinnerComponent,

  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    MessagesModule,
    AppRoutingModule,
    ButtonModule,
    AvatarModule,
    MenuModule,
    SidebarModule,
    InputTextModule,
    DropdownModule,
    HttpClientModule,
    ToastModule,
    CardModule,
    BadgeModule,
    ConfirmDialogModule,
    PaginatorModule,
    ProgressSpinnerModule,
  ],
  providers: [
    {
      provide : HTTP_INTERCEPTORS,
      useClass : HttpInterceptorService,
      multi:true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
