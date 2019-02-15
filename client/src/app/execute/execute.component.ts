import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';
import { NgbModule, NgbDate, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { WorkerService, ExecuteService, JobsService } from '../services';
import { ApplicationConstants } from '../models/app-constants';
import { Worker } from '../models/Worker';
import swal from 'sweetalert2';
import { Job } from '../models/Job';
import { WorkerJob } from '../models/WorkerJob';
@Component({
  selector: 'app-execute',
  templateUrl: './execute.component.html',
  styleUrls: ['./execute.component.scss']
})
export class ExecuteComponent implements OnInit {

 
  // It maintains list of Workers
  workers: Worker[] = [];

  // It maintains list of jobs
  jobs: Job[] = [];

  // It maintains worker Model
  selectedWorkerJob = new WorkerJob();
  // It maintains worker form display status. By default it will be false.

  // It maintains table row index based on selection.
  selectedRow: number;
  MAXJOBSPERWORKER: number;
  // It maintains Array of countries.
  countries: string[] = ['US', 'UK', 'India', 'UAE'];
  constructor(
    private http: HttpClient,
    private workerService: WorkerService,
    private jobService: JobsService,
    private executeService: ExecuteService,
    )
   {}

  ngOnInit() {

    this.executeService.maxJobsPerWorker().subscribe(data => {
      console.log("got MAXJOBSPERWORKER " + JSON.stringify(data));
      this.MAXJOBSPERWORKER = Number(data) ;
    }, error => {
      console.log("got error" + error);
    });

    this.refreshPage();
  }

  private refreshPage() {
    this.workerService.getAll().subscribe(data => {
      console.log("got data" + JSON.stringify(data));
      this.workers = data;
    }, error => {
      console.log("got error" + error);
    });

    this.jobService.getAll().subscribe(data => {
      console.log("got data" + JSON.stringify(data));
      this.jobs = data;
    }, error => {
      console.log("got error" + error);
    });
  }

  // This method associate to Save Button.
  onExecute(workerId: string, jobId: string) {

    this.selectedWorkerJob.workerId = workerId;
    this.selectedWorkerJob.jobId = jobId;

      this.executeService.doExecute(this.selectedWorkerJob)
      .pipe(first())
      .subscribe(
        data => {
          console.log('Worker ' + ApplicationConstants.UPDATED_SUCCESSFULLY);
          // this.refreshPage();
          return true;
        },
        error => {
        console.log('Failed to update Worker ' + error);
        swal.fire({
          title: 'Failed',
          text: 'Failed to update Worker!',
          type: 'error',
          showCancelButton: false,
        });
        return false;
      });
  }

}