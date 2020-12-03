import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BaggageHandlerTestModule } from '../../../test.module';
import { CheckInInfoComponent } from 'app/entities/check-in-info/check-in-info.component';
import { CheckInInfoService } from 'app/entities/check-in-info/check-in-info.service';
import { CheckInInfo } from 'app/shared/model/check-in-info.model';

describe('Component Tests', () => {
  describe('CheckInInfo Management Component', () => {
    let comp: CheckInInfoComponent;
    let fixture: ComponentFixture<CheckInInfoComponent>;
    let service: CheckInInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BaggageHandlerTestModule],
        declarations: [CheckInInfoComponent],
      })
        .overrideTemplate(CheckInInfoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CheckInInfoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CheckInInfoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CheckInInfo(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.checkInInfos && comp.checkInInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
