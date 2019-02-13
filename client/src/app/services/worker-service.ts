import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { environment } from 'src/environments/environment';

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
}
