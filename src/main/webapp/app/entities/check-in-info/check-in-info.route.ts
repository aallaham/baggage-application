import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICheckInInfo, CheckInInfo } from 'app/shared/model/check-in-info.model';
import { CheckInInfoService } from './check-in-info.service';
import { CheckInInfoComponent } from './check-in-info.component';
import { CheckInInfoDetailComponent } from './check-in-info-detail.component';
import { CheckInInfoUpdateComponent } from './check-in-info-update.component';

@Injectable({ providedIn: 'root' })
export class CheckInInfoResolve implements Resolve<ICheckInInfo> {
  constructor(private service: CheckInInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICheckInInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((checkInInfo: HttpResponse<CheckInInfo>) => {
          if (checkInInfo.body) {
            return of(checkInInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CheckInInfo());
  }
}

export const checkInInfoRoute: Routes = [
  {
    path: '',
    component: CheckInInfoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baggageHandlerApp.checkInInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CheckInInfoDetailComponent,
    resolve: {
      checkInInfo: CheckInInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baggageHandlerApp.checkInInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CheckInInfoUpdateComponent,
    resolve: {
      checkInInfo: CheckInInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baggageHandlerApp.checkInInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CheckInInfoUpdateComponent,
    resolve: {
      checkInInfo: CheckInInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baggageHandlerApp.checkInInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
