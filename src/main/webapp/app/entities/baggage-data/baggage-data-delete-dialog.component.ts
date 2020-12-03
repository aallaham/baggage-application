import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBaggageData } from 'app/shared/model/baggage-data.model';
import { BaggageDataService } from './baggage-data.service';

@Component({
  templateUrl: './baggage-data-delete-dialog.component.html',
})
export class BaggageDataDeleteDialogComponent {
  baggageData?: IBaggageData;

  constructor(
    protected baggageDataService: BaggageDataService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.baggageDataService.delete(id).subscribe(() => {
      this.eventManager.broadcast('baggageDataListModification');
      this.activeModal.close();
    });
  }
}
