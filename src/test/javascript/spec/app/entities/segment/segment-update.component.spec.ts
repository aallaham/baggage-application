import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BaggageHandlerTestModule } from '../../../test.module';
import { SegmentUpdateComponent } from 'app/entities/segment/segment-update.component';
import { SegmentService } from 'app/entities/segment/segment.service';
import { Segment } from 'app/shared/model/segment.model';

describe('Component Tests', () => {
  describe('Segment Management Update Component', () => {
    let comp: SegmentUpdateComponent;
    let fixture: ComponentFixture<SegmentUpdateComponent>;
    let service: SegmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BaggageHandlerTestModule],
        declarations: [SegmentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SegmentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SegmentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SegmentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Segment(123);
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
        const entity = new Segment();
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
