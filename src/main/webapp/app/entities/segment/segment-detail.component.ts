import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISegment } from 'app/shared/model/segment.model';

@Component({
  selector: 'jhi-segment-detail',
  templateUrl: './segment-detail.component.html',
})
export class SegmentDetailComponent implements OnInit {
  segment: ISegment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ segment }) => (this.segment = segment));
  }

  previousState(): void {
    window.history.back();
  }
}
