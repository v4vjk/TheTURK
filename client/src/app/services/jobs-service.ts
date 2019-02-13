import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule, HttpEventType, HttpResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Job } from '../models/Job';

@Injectable({
  providedIn:  'root'
  })

export class JobsService {

  constructor(
    private http: HttpClient
  ) { }

  getAll() {
    return this.http.get<any[]>(`${environment.serverUrl}/api/jobs/all`, {});
  }

  deleteJob(id: number) {
    return this.http.delete(`${environment.serverUrl}/api/jobs/` + id);
  }

  update(job: Job) {
    return this.http.post(`${environment.serverUrl}/api/jobs/updatejob`, job);
  }

  add(job: Job) {
    return this.http.post(`${environment.serverUrl}/api/jobs/addjob`, job);
  }

  uploadFile(formData: FormData) {
    return this.http.post(`${environment.serverUrl}/api/jobs/upload`,
       formData, { reportProgress: true, observe: 'events' });
  }
}
