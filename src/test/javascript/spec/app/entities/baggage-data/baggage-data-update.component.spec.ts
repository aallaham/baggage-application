import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BaggageHandlerTestModule } from '../../../test.module';
import { BaggageDataUpdateComponent } from 'app/entities/baggage-data/baggage-data-update.component';
import { BaggageDataService } from 'app/entities/baggage-data/baggage-data.service';
import { BaggageData } from 'app/shared/model/baggage-data.model';

describe('Component Tests', () => {
  describe('BaggageData Management Update Component', () => {
    let comp: BaggageDataUpdateComponent;
    let fixture: ComponentFixture<BaggageDataUpdateComponent>;
    let service: BaggageDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BaggageHandlerTestModule],
        declarations: [BaggageDataUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BaggageDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BaggageDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BaggageDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BaggageData(123);
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
        const entity = new BaggageData();
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
