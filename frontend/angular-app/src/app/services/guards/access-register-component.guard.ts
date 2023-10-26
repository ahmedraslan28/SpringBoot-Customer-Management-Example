import { CanActivateFn } from '@angular/router';

export const accessRegisterComponentGuard: CanActivateFn = (route, state) => {
  return true;
};
