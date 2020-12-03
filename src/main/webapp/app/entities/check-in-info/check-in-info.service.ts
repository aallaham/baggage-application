import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICheckInInfo } from 'app/shared/model/check-in-info.model';

type EntityResponseType = HttpResponse<ICheckInInfo>;
type EntityArrayResponseType = HttpResponse<ICheckInInfo[]>;

@Injectable({ providedIn: 'root' })
export class CheckInInfoService {
  public resourceUrl = SERVER_API_URL + 'api/check-in-infos';

  constructor(protected http: HttpClient) {}

  create(checkInInfo: ICheckInInfo): Observable<EntityResponseType> {
    return this.http.post<ICheckInInfo>(this.resourceUrl, checkInInfo, { observe: 'response' });
  }

  update(checkInInfo: ICheckInInfo): Observable<EntityResponseType> {
    return this.http.put<ICheckInInfo>(this.resourceUrl, checkInInfo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICheckInInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICheckInInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
