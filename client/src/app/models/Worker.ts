import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { WorkerJob } from './WorkerJob';

export class Worker {
    id: string;
    workerName: string;
    description: string;
    createdAt: NgbDateStruct;
    updatedAt: NgbDateStruct;
    workerJobs: WorkerJob[] = [];

}