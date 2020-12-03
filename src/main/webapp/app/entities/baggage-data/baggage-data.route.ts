import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBaggageData, BaggageData } from 'app/shared/model/baggage-data.model';
import { BaggageDataService } from './baggage-data.service';
import { BaggageDataComponent } from './baggage-data.component';
import { BaggageDataDetailComponent } from './baggage-data-detail.component';
import { BaggageDataUpdateComponent } from './baggage-data-update.component';

@Injectable({ providedIn: 'root' })
export class BaggageDataResolve implements Resolve<IBaggageData> {
  constructor(private service: BaggageDataService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBaggageData> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((baggageData: HttpResponse<BaggageData>) => {
          if (baggageData.body) {
            return of(baggageData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BaggageData());
  }
}

export const baggageDataRoute: Routes = [
  {
    path: '',
    component: BaggageDataComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baggageHandlerApp.baggageData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BaggageDataDetailComponent,
    resolve: {
      baggageData: BaggageDataResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baggageHandlerApp.baggageData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BaggageDataUpdateComponent,
    resolve: {
      baggageData: BaggageDataResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baggageHandlerApp.baggageData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BaggageDataUpdateComponent,
    resolve: {
      baggageData: BaggageDataResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baggageHandlerApp.baggageData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
