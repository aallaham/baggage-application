import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISegment, Segment } from 'app/shared/model/segment.model';
import { SegmentService } from './segment.service';
import { ICheckInInfo } from 'app/shared/model/check-in-info.model';
import { CheckInInfoService } from 'app/entities/check-in-info/check-in-info.service';

@Component({
  selector: 'jhi-segment-update',
  templateUrl: './segment-update.component.html',
})
export class SegmentUpdateComponent implements OnInit {
  isSaving = false;
  checkininfos: ICheckInInfo[] = [];

  editForm = this.fb.group({
    id: [],
    seatNumber: [],
    departureDate: [],
    checkInInfo: [],
  });

  constructor(
    protected segmentService: SegmentService,
    protected checkInInfoService: CheckInInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ segment }) => {
      if (!segment.id) {
        const today = moment().startOf('day');
        segment.departureDate = today;
      }

      this.updateForm(segment);

      this.checkInInfoService.query().subscribe((res: HttpResponse<ICheckInInfo[]>) => (this.checkininfos = res.body || []));
    });
  }

  updateForm(segment: ISegment): void {
    this.editForm.patchValue({
      id: segment.id,
      seatNumber: segment.seatNumber,
      departureDate: segment.departureDate ? segment.departureDate.format(DATE_TIME_FORMAT) : null,
      checkInInfo: segment.checkInInfo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const segment = this.createFromForm();
    if (segment.id !== undefined) {
      this.subscribeToSaveResponse(this.segmentService.update(segment));
    } else {
      this.subscribeToSaveResponse(this.segmentService.create(segment));
    }
  }

  private createFromForm(): ISegment {
    return {
      ...new Segment(),
      id: this.editForm.get(['id'])!.value,
      seatNumber: this.editForm.get(['seatNumber'])!.value,
      departureDate: this.editForm.get(['departureDate'])!.value
        ? moment(this.editForm.get(['departureDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      checkInInfo: this.editForm.get(['checkInInfo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISegment>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICheckInInfo): any {
    return item.id;
  }
}
