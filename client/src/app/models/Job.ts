import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

export class Job {
    id: string;
    jobName: string;
    className: string;
    jarName: string;
    jarPath: string;
    description: string;
    createdAt: NgbDateStruct;
    updatedAt: NgbDateStruct;
}