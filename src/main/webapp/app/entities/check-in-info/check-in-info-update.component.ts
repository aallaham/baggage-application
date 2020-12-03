import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICheckInInfo, CheckInInfo } from 'app/shared/model/check-in-info.model';
import { CheckInInfoService } from './check-in-info.service';

@Component({
  selector: 'jhi-check-in-info-update',
  templateUrl: './check-in-info-update.component.html',
})
export class CheckInInfoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    memberName: [],
    pnrNumber: [],
    checkInStatus: [],
  });

  constructor(protected checkInInfoService: CheckInInfoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkInInfo }) => {
      this.updateForm(checkInInfo);
    });
  }

  updateForm(checkInInfo: ICheckInInfo): void {
    this.editForm.patchValue({
      id: checkInInfo.id,
      memberName: checkInInfo.memberName,
      pnrNumber: checkInInfo.pnrNumber,
      checkInStatus: checkInInfo.checkInStatus,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkInInfo = this.createFromForm();
    if (checkInInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.checkInInfoService.update(checkInInfo));
    } else {
      this.subscribeToSaveResponse(this.checkInInfoService.create(checkInInfo));
    }
  }

  private createFromForm(): ICheckInInfo {
    return {
      ...new CheckInInfo(),
      id: this.editForm.get(['id'])!.value,
      memberName: this.editForm.get(['memberName'])!.value,
      pnrNumber: this.editForm.get(['pnrNumber'])!.value,
      checkInStatus: this.editForm.get(['checkInStatus'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckInInfo>>): void {
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
}
