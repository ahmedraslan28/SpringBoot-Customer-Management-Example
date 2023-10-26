import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { accessLoginGuardGuard } from './services/guards/access-login-guard.guard';
import { accessDashboardGuard } from './services/guards/access-dashboard.guard';
import { RegisterComponent } from './components/register/register.component';

const routes: Routes = [
  {
    path: 'dashboard',
    component: HomeComponent,
    canActivate: [accessDashboardGuard],
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [accessLoginGuardGuard],
  },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [accessLoginGuardGuard],
  },
  {
    path : '' ,
    redirectTo : 'login',
    pathMatch : 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
