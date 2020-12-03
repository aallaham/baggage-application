import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BaggageHandlerSharedModule } from 'app/shared/shared.module';
import { CheckInInfoComponent } from './check-in-info.component';
import { CheckInInfoDetailComponent } from './check-in-info-detail.component';
import { CheckInInfoUpdateComponent } from './check-in-info-update.component';
import { CheckInInfoDeleteDialogComponent } from './check-in-info-delete-dialog.component';
import { checkInInfoRoute } from './check-in-info.route';

@NgModule({
  imports: [BaggageHandlerSharedModule, RouterModule.forChild(checkInInfoRoute)],
  declarations: [CheckInInfoComponent, CheckInInfoDetailComponent, CheckInInfoUpdateComponent, CheckInInfoDeleteDialogComponent],
  entryComponents: [CheckInInfoDeleteDialogComponent],
})
export class BaggageHandlerCheckInInfoModule {}
