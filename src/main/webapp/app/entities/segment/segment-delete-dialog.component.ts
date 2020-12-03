import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISegment } from 'app/shared/model/segment.model';
import { SegmentService } from './segment.service';

@Component({
  templateUrl: './segment-delete-dialog.component.html',
})
export class SegmentDeleteDialogComponent {
  segment?: ISegment;

  constructor(protected segmentService: SegmentService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.segmentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('segmentListModification');
      this.activeModal.close();
    });
  }
}
