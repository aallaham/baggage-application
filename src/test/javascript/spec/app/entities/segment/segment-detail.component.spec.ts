import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BaggageHandlerTestModule } from '../../../test.module';
import { SegmentDetailComponent } from 'app/entities/segment/segment-detail.component';
import { Segment } from 'app/shared/model/segment.model';

describe('Component Tests', () => {
  describe('Segment Management Detail Component', () => {
    let comp: SegmentDetailComponent;
    let fixture: ComponentFixture<SegmentDetailComponent>;
    const route = ({ data: of({ segment: new Segment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BaggageHandlerTestModule],
        declarations: [SegmentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SegmentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SegmentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load segment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.segment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
