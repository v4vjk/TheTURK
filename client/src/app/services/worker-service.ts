import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Worker } from '../models/Worker';

@Injectable({
  providedIn:  'root'
  })

export class WorkerService {

  constructor(
    private http: HttpClient
  ) { }

  getAll() {
    return this.http.get<any[]>(`${environment.serverUrl}/api/workers/all`, {});
  }

  deleteWorker(id: number) {
    return this.http.delete(`${environment.serverUrl}/api/workers/` + id);
  }

  update(worker: Worker) {
    return this.http.post(`${environment.serverUrl}/api/workers/updateworker`, worker);
  }

  add(worker: Worker) {
    return this.http.post(`${environment.serverUrl}/api/workers/addworker`, worker);
  }
}
