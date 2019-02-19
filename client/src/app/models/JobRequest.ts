import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { Params } from './params';

export class JobRequest {
    id: string;
    workerId: string;
    jobId: string;
    status: string;
    input: Params;
    output: Params;
    startTime: NgbDateStruct;
    endTime: NgbDateStruct;
}