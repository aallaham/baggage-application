import { Moment } from 'moment';
import { ICheckInInfo } from 'app/shared/model/check-in-info.model';

export interface ISegment {
  id?: number;
  seatNumber?: string;
  departureDate?: Moment;
  checkInInfo?: ICheckInInfo;
}

export class Segment implements ISegment {
  constructor(public id?: number, public seatNumber?: string, public departureDate?: Moment, public checkInInfo?: ICheckInInfo) {}
}
