import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICheckInInfo } from 'app/shared/model/check-in-info.model';
import { CheckInInfoService } from './check-in-info.service';

@Component({
  templateUrl: './check-in-info-delete-dialog.component.html',
})
export class CheckInInfoDeleteDialogComponent {
  checkInInfo?: ICheckInInfo;

  constructor(
    protected checkInInfoService: CheckInInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.checkInInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('checkInInfoListModification');
      this.activeModal.close();
    });
  }
}
