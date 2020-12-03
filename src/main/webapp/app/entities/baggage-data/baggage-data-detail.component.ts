import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBaggageData } from 'app/shared/model/baggage-data.model';

@Component({
  selector: 'jhi-baggage-data-detail',
  templateUrl: './baggage-data-detail.component.html',
})
export class BaggageDataDetailComponent implements OnInit {
  baggageData: IBaggageData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ baggageData }) => (this.baggageData = baggageData));
  }

  previousState(): void {
    window.history.back();
  }
}
