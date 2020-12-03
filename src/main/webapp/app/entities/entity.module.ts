import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'check-in-info',
        loadChildren: () => import('./check-in-info/check-in-info.module').then(m => m.BaggageHandlerCheckInInfoModule),
      },
      {
        path: 'segment',
        loadChildren: () => import('./segment/segment.module').then(m => m.BaggageHandlerSegmentModule),
      },
      {
        path: 'baggage-data',
        loadChildren: () => import('./baggage-data/baggage-data.module').then(m => m.BaggageHandlerBaggageDataModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class BaggageHandlerEntityModule {}
