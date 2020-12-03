import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICheckInInfo } from 'app/shared/model/check-in-info.model';
import { CheckInInfoService } from './check-in-info.service';
import { CheckInInfoDeleteDialogComponent } from './check-in-info-delete-dialog.component';

@Component({
  selector: 'jhi-check-in-info',
  templateUrl: './check-in-info.component.html',
})
export class CheckInInfoComponent implements OnInit, OnDestroy {
  checkInInfos?: ICheckInInfo[];
  eventSubscriber?: Subscription;

  constructor(
    protected checkInInfoService: CheckInInfoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.checkInInfoService.query().subscribe((res: HttpResponse<ICheckInInfo[]>) => (this.checkInInfos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCheckInInfos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICheckInInfo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCheckInInfos(): void {
    this.eventSubscriber = this.eventManager.subscribe('checkInInfoListModification', () => this.loadAll());
  }

  delete(checkInInfo: ICheckInInfo): void {
    const modalRef = this.modalService.open(CheckInInfoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.checkInInfo = checkInInfo;
  }
}
