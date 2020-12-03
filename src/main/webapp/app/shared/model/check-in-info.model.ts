import { ISegment } from 'app/shared/model/segment.model';
import { IBaggageData } from 'app/shared/model/baggage-data.model';

export interface ICheckInInfo {
  id?: number;
  memberName?: string;
  pnrNumber?: string;
  checkInStatus?: string;
  segments?: ISegment[];
  baggageData?: IBaggageData[];
}

export class CheckInInfo implements ICheckInInfo {
  constructor(
    public id?: number,
    public memberName?: string,
    public pnrNumber?: string,
    public checkInStatus?: string,
    public segments?: ISegment[],
    public baggageData?: IBaggageData[]
  ) {}
}
