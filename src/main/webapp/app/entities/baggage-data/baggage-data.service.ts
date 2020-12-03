import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBaggageData } from 'app/shared/model/baggage-data.model';

type EntityResponseType = HttpResponse<IBaggageData>;
type EntityArrayResponseType = HttpResponse<IBaggageData[]>;

@Injectable({ providedIn: 'root' })
export class BaggageDataService {
  public resourceUrl = SERVER_API_URL + 'api/baggage-data';

  constructor(protected http: HttpClient) {}

  create(baggageData: IBaggageData): Observable<EntityResponseType> {
    return this.http.post<IBaggageData>(this.resourceUrl, baggageData, { observe: 'response' });
  }

  update(baggageData: IBaggageData): Observable<EntityResponseType> {
    return this.http.put<IBaggageData>(this.resourceUrl, baggageData, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBaggageData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBaggageData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
