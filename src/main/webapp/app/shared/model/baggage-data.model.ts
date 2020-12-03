import { ICheckInInfo } from 'app/shared/model/check-in-info.model';

export interface IBaggageData {
  id?: number;
  baggageId?: string;
  weight?: number;
  status?: string;
  qrCode?: string;
  checkInInfo?: ICheckInInfo;
}

export class BaggageData implements IBaggageData {
  constructor(
    public id?: number,
    public baggageId?: string,
    public weight?: number,
    public status?: string,
    public qrCode?: string,
    public checkInInfo?: ICheckInInfo
  ) {}
}
