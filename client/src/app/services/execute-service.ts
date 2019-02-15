import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Worker } from '../models/Worker';
import { WorkerJob } from '../models/WorkerJob';

@Injectable({
  providedIn:  'root'
  })

export class ExecuteService {

  constructor(
    private http: HttpClient
  ) { }

  maxJobsPerWorker() {
    return this.http.get<string>(`${environment.serverUrl}/api/maxJobsPerWorker`,  {});
  }

  doExecute(workerJob: WorkerJob) {
    return this.http.post<any[]>(`${environment.serverUrl}/api/execution/doexecute`, workerJob);
  }

  deleteWorker(id: number) {
    return this.http.delete(`${environment.serverUrl}/api/workers/` + id);
  }



  add(worker: Worker) {
    return this.http.post(`${environment.serverUrl}/api/workers/addworker`, worker);
  }
}
