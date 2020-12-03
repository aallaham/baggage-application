import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISegment } from 'app/shared/model/segment.model';
import { SegmentService } from './segment.service';
import { SegmentDeleteDialogComponent } from './segment-delete-dialog.component';

@Component({
  selector: 'jhi-segment',
  templateUrl: './segment.component.html',
})
export class SegmentComponent implements OnInit, OnDestroy {
  segments?: ISegment[];
  eventSubscriber?: Subscription;

  constructor(protected segmentService: SegmentService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.segmentService.query().subscribe((res: HttpResponse<ISegment[]>) => (this.segments = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSegments();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISegment): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSegments(): void {
    this.eventSubscriber = this.eventManager.subscribe('segmentListModification', () => this.loadAll());
  }

  delete(segment: ISegment): void {
    const modalRef = this.modalService.open(SegmentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.segment = segment;
  }
}
