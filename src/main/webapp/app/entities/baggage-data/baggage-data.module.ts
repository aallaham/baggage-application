import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BaggageHandlerSharedModule } from 'app/shared/shared.module';
import { BaggageDataComponent } from './baggage-data.component';
import { BaggageDataDetailComponent } from './baggage-data-detail.component';
import { BaggageDataUpdateComponent } from './baggage-data-update.component';
import { BaggageDataDeleteDialogComponent } from './baggage-data-delete-dialog.component';
import { baggageDataRoute } from './baggage-data.route';

@NgModule({
  imports: [BaggageHandlerSharedModule, RouterModule.forChild(baggageDataRoute)],
  declarations: [BaggageDataComponent, BaggageDataDetailComponent, BaggageDataUpdateComponent, BaggageDataDeleteDialogComponent],
  entryComponents: [BaggageDataDeleteDialogComponent],
})
export class BaggageHandlerBaggageDataModule {}
