import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BaggageHandlerTestModule } from '../../../test.module';
import { BaggageDataComponent } from 'app/entities/baggage-data/baggage-data.component';
import { BaggageDataService } from 'app/entities/baggage-data/baggage-data.service';
import { BaggageData } from 'app/shared/model/baggage-data.model';

describe('Component Tests', () => {
  describe('BaggageData Management Component', () => {
    let comp: BaggageDataComponent;
    let fixture: ComponentFixture<BaggageDataComponent>;
    let service: BaggageDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BaggageHandlerTestModule],
        declarations: [BaggageDataComponent],
      })
        .overrideTemplate(BaggageDataComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BaggageDataComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BaggageDataService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BaggageData(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.baggageData && comp.baggageData[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
