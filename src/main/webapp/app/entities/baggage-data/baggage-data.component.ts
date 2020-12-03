import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBaggageData } from 'app/shared/model/baggage-data.model';
import { BaggageDataService } from './baggage-data.service';
import { BaggageDataDeleteDialogComponent } from './baggage-data-delete-dialog.component';

@Component({
  selector: 'jhi-baggage-data',
  templateUrl: './baggage-data.component.html',
})
export class BaggageDataComponent implements OnInit, OnDestroy {
  baggageData?: IBaggageData[];
  eventSubscriber?: Subscription;

  constructor(
    protected baggageDataService: BaggageDataService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.baggageDataService.query().subscribe((res: HttpResponse<IBaggageData[]>) => (this.baggageData = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBaggageData();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBaggageData): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBaggageData(): void {
    this.eventSubscriber = this.eventManager.subscribe('baggageDataListModification', () => this.loadAll());
  }

  delete(baggageData: IBaggageData): void {
    const modalRef = this.modalService.open(BaggageDataDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.baggageData = baggageData;
  }
}
