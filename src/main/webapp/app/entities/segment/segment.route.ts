import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISegment, Segment } from 'app/shared/model/segment.model';
import { SegmentService } from './segment.service';
import { SegmentComponent } from './segment.component';
import { SegmentDetailComponent } from './segment-detail.component';
import { SegmentUpdateComponent } from './segment-update.component';

@Injectable({ providedIn: 'root' })
export class SegmentResolve implements Resolve<ISegment> {
  constructor(private service: SegmentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISegment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((segment: HttpResponse<Segment>) => {
          if (segment.body) {
            return of(segment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Segment());
  }
}

export const segmentRoute: Routes = [
  {
    path: '',
    component: SegmentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baggageHandlerApp.segment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SegmentDetailComponent,
    resolve: {
      segment: SegmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baggageHandlerApp.segment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SegmentUpdateComponent,
    resolve: {
      segment: SegmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baggageHandlerApp.segment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SegmentUpdateComponent,
    resolve: {
      segment: SegmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baggageHandlerApp.segment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
