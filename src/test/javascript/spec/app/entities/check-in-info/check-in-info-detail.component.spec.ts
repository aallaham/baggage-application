import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BaggageHandlerTestModule } from '../../../test.module';
import { CheckInInfoDetailComponent } from 'app/entities/check-in-info/check-in-info-detail.component';
import { CheckInInfo } from 'app/shared/model/check-in-info.model';

describe('Component Tests', () => {
  describe('CheckInInfo Management Detail Component', () => {
    let comp: CheckInInfoDetailComponent;
    let fixture: ComponentFixture<CheckInInfoDetailComponent>;
    const route = ({ data: of({ checkInInfo: new CheckInInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BaggageHandlerTestModule],
        declarations: [CheckInInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CheckInInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckInInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load checkInInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.checkInInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
