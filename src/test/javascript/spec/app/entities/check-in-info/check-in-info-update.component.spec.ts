import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BaggageHandlerTestModule } from '../../../test.module';
import { CheckInInfoUpdateComponent } from 'app/entities/check-in-info/check-in-info-update.component';
import { CheckInInfoService } from 'app/entities/check-in-info/check-in-info.service';
import { CheckInInfo } from 'app/shared/model/check-in-info.model';

describe('Component Tests', () => {
  describe('CheckInInfo Management Update Component', () => {
    let comp: CheckInInfoUpdateComponent;
    let fixture: ComponentFixture<CheckInInfoUpdateComponent>;
    let service: CheckInInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BaggageHandlerTestModule],
        declarations: [CheckInInfoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CheckInInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CheckInInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CheckInInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CheckInInfo(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CheckInInfo();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
