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
import { Params } from '../models/params';

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

  // It max list of WorkerJob
  workerJobs1: WorkerJob[] = [];

  // It maintains worker Model
  selectedWorkerJob = new WorkerJob();
  params = new Params();

  // It maintains table row index based on selection.
  selectedRow: number;
  MAXJOBSPERWORKER: number;
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

      for (let i = 0; i < this.MAXJOBSPERWORKER; i++) {
        var workerJob = new WorkerJob();
  
        this.workerJobs1.push(workerJob);
      }

    }, error => {
      console.log("got error MAXJOBSPERWORKER " + error);
    });

    this.refreshPage();
}

  private refreshPage() {
    this.workerService.getAll().subscribe(data => {
      console.log("got data" + JSON.stringify(data));
      this.workers = data;
      this.workers.forEach(worker => {
        worker.workerJobs = this.workerJobs1;
    });
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

  // This method associate to execute.
  onExecute(workerId: string, jobId: string, param1: string, param2: string) {
    
    //check if job is selected
    if(jobId == "Select Job"){
      swal.fire({
        title: 'Job',
        text: 'Please select Job!',
        type: 'warning',
        showCancelButton: false,
      });
      return;
    }
    this.selectedWorkerJob.workerId = workerId;
    this.selectedWorkerJob.jobId = jobId;
    this.params.FIRST_NAME = param1;
    this.params.LAST_NAME = param2;
    this.selectedWorkerJob.params = this.params;

    console.log(JSON.stringify(this.selectedWorkerJob));

      this.executeService.doExecute(this.selectedWorkerJob)
      .pipe(first())
      .subscribe(
        data => {
          console.log('Job executed successfully');
          return true;
        },
        error => {
        console.log('Failed to execute job ' + error);
        // swal.fire({
        //   title: 'Failed',
        //   text: 'Failed  to execute job!',
        //   type: 'error',
        //   showCancelButton: false,
        // });
        return false;
      });
  }

}