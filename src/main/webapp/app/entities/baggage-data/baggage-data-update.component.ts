import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBaggageData, BaggageData } from 'app/shared/model/baggage-data.model';
import { BaggageDataService } from './baggage-data.service';
import { ICheckInInfo } from 'app/shared/model/check-in-info.model';
import { CheckInInfoService } from 'app/entities/check-in-info/check-in-info.service';

@Component({
  selector: 'jhi-baggage-data-update',
  templateUrl: './baggage-data-update.component.html',
})
export class BaggageDataUpdateComponent implements OnInit {
  isSaving = false;
  checkininfos: ICheckInInfo[] = [];

  editForm = this.fb.group({
    id: [],
    baggageId: [],
    weight: [],
    status: [],
    qrCode: [],
    checkInInfo: [],
  });

  constructor(
    protected baggageDataService: BaggageDataService,
    protected checkInInfoService: CheckInInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ baggageData }) => {
      this.updateForm(baggageData);

      this.checkInInfoService.query().subscribe((res: HttpResponse<ICheckInInfo[]>) => (this.checkininfos = res.body || []));
    });
  }

  updateForm(baggageData: IBaggageData): void {
    this.editForm.patchValue({
      id: baggageData.id,
      baggageId: baggageData.baggageId,
      weight: baggageData.weight,
      status: baggageData.status,
      qrCode: baggageData.qrCode,
      checkInInfo: baggageData.checkInInfo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const baggageData = this.createFromForm();
    if (baggageData.id !== undefined) {
      this.subscribeToSaveResponse(this.baggageDataService.update(baggageData));
    } else {
      this.subscribeToSaveResponse(this.baggageDataService.create(baggageData));
    }
  }

  private createFromForm(): IBaggageData {
    return {
      ...new BaggageData(),
      id: this.editForm.get(['id'])!.value,
      baggageId: this.editForm.get(['baggageId'])!.value,
      weight: this.editForm.get(['weight'])!.value,
      status: this.editForm.get(['status'])!.value,
      qrCode: this.editForm.get(['qrCode'])!.value,
      checkInInfo: this.editForm.get(['checkInInfo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBaggageData>>): void {
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
