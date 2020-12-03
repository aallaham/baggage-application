import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckInInfo } from 'app/shared/model/check-in-info.model';

@Component({
  selector: 'jhi-check-in-info-detail',
  templateUrl: './check-in-info-detail.component.html',
})
export class CheckInInfoDetailComponent implements OnInit {
  checkInInfo: ICheckInInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkInInfo }) => (this.checkInInfo = checkInInfo));
  }

  previousState(): void {
    window.history.back();
  }
}
