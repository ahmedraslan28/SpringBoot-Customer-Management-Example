import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { AccessGuardServiceService } from './access-guard-service.service';
export const accessDashboardGuard: CanActivateFn = (route, state) => {
  return inject(AccessGuardServiceService).canNavigateDashBoard(route, state);
};
