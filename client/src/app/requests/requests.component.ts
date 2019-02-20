import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';
import { NgbModule, NgbDate, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ApplicationConstants } from '../models/app-constants';
import { Worker } from '../models/Worker';
import swal from 'sweetalert2';
import { JobsRequestService } from '../services/jobsRequest-service';
import { JobRequest } from '../models/JobRequest';
@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.scss'],
  providers: [
    JobsRequestService,
    HttpClient
   ]
})
export class RequestsComponent implements OnInit {


 
  // It maintains list of JobRequest
  jobRequests: JobRequest[] = [];

  constructor(
    private http: HttpClient,
    private jobsRequestService: JobsRequestService,
    )
   {}

  ngOnInit() {
    this.refreshPage();
  }

  refreshPage() {
    this.jobsRequestService.getAll().subscribe(data => {
      console.log("got data" + JSON.stringify(data));
      this.jobRequests = data;
    }, error => {
      console.log("got error" + error);
    });
  }

}
