import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BaggageHandlerTestModule } from '../../../test.module';
import { SegmentComponent } from 'app/entities/segment/segment.component';
import { SegmentService } from 'app/entities/segment/segment.service';
import { Segment } from 'app/shared/model/segment.model';

describe('Component Tests', () => {
  describe('Segment Management Component', () => {
    let comp: SegmentComponent;
    let fixture: ComponentFixture<SegmentComponent>;
    let service: SegmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BaggageHandlerTestModule],
        declarations: [SegmentComponent],
      })
        .overrideTemplate(SegmentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SegmentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SegmentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Segment(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.segments && comp.segments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
