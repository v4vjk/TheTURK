import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule, HttpEventType, HttpResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Job } from '../models/Job';
import { JobRequest } from '../models/JobRequest';

@Injectable({
  providedIn:  'root'
  })

export class JobsRequestService {

  constructor(
    private http: HttpClient
  ) { }

  getAll() {
    return this.http.post<JobRequest[]>(`${environment.serverUrl}/api/request/all`, {});
  }

}
