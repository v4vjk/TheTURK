import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { Params } from './params';

export class WorkerJob {
    id: string;
    workerId: string;
    jobId: string;
    params: Params;
    createdAt: NgbDateStruct;
    updatedAt: NgbDateStruct;
}