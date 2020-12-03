import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BaggageHandlerTestModule } from '../../../test.module';
import { BaggageDataDetailComponent } from 'app/entities/baggage-data/baggage-data-detail.component';
import { BaggageData } from 'app/shared/model/baggage-data.model';

describe('Component Tests', () => {
  describe('BaggageData Management Detail Component', () => {
    let comp: BaggageDataDetailComponent;
    let fixture: ComponentFixture<BaggageDataDetailComponent>;
    const route = ({ data: of({ baggageData: new BaggageData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BaggageHandlerTestModule],
        declarations: [BaggageDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BaggageDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BaggageDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load baggageData on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.baggageData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
