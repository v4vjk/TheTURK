import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

export class Worker {
    id: string;
    workerName: string;
    description: string;
    createdAt: NgbDateStruct;
    updatedAt: NgbDateStruct;
}