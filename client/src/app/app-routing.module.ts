import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { WorkersComponent } from './workers/workers.component';
import { JobsComponent } from './jobs/jobs.component';
import { ExecuteComponent } from './execute/execute.component';
import { RequestsComponent } from './requests/requests.component';

// const routes: Routes = [];

const routes: Routes = [
  {
    path: 'workers',
    component: WorkersComponent,
    // outlet: 'popup'
  },
  {
    path: 'jobs',
    component: JobsComponent,
    // outlet: 'popup'
  },
  {
    path: 'execute',
    component: ExecuteComponent,
    // outlet: 'popup'
  },
  {
    path: 'requests',
    component: RequestsComponent,
    // outlet: 'popup'
  },
  // { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
