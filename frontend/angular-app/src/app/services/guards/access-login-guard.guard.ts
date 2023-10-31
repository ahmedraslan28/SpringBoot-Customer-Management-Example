import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { AccessGuardServiceService } from './access-guard-service.service';

export const accessLoginGuardGuard: CanActivateFn = (route, state) => {
  return inject(AccessGuardServiceService).canNavigateLogin(route, state);
};
