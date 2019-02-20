import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
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
    return this.http.post<any[]>(`${environment.serverUrl}/api/workers/all`, {});
  }

  deleteWorker(id: number) {
    return this.http.delete(`${environment.serverUrl}/api/workers/` + id);
  }

  update(worker: Worker) {
    return this.http.post(`${environment.serverUrl}/api/workers/updateworker`, worker);
  }
  
  headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8');


  setHttpHeader() {
    const headers = new HttpHeaders().set('Accept', 'application/json').set('Content-Type', 'application/json');
    let options = { headers: headers, responseType: 'text' as 'text'};
    return options;
  }

  add(worker: Worker) {
    return this.http.post<any>(`${environment.serverUrl}/api/workers/addworker`, worker);
  }
}
