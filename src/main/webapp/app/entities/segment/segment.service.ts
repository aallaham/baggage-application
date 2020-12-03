import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISegment } from 'app/shared/model/segment.model';

type EntityResponseType = HttpResponse<ISegment>;
type EntityArrayResponseType = HttpResponse<ISegment[]>;

@Injectable({ providedIn: 'root' })
export class SegmentService {
  public resourceUrl = SERVER_API_URL + 'api/segments';

  constructor(protected http: HttpClient) {}

  create(segment: ISegment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(segment);
    return this.http
      .post<ISegment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(segment: ISegment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(segment);
    return this.http
      .put<ISegment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISegment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISegment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(segment: ISegment): ISegment {
    const copy: ISegment = Object.assign({}, segment, {
      departureDate: segment.departureDate && segment.departureDate.isValid() ? segment.departureDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.departureDate = res.body.departureDate ? moment(res.body.departureDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((segment: ISegment) => {
        segment.departureDate = segment.departureDate ? moment(segment.departureDate) : undefined;
      });
    }
    return res;
  }
}
