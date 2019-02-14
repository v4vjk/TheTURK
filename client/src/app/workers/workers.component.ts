import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';
import { NgbModule, NgbDate, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { WorkerService } from '../services';
import { ApplicationConstants } from '../models/app-constants';
import { Worker } from '../models/Worker';
import swal from 'sweetalert2';

@Component({
  selector: 'app-workers',
  templateUrl: './workers.component.html',
  styleUrls: ['./workers.component.scss'],
  providers: [
    WorkerService,
    HttpClient
   ]
})
export class WorkersComponent implements OnInit {

 
  // It maintains list of Workers
  workers: Worker[] = [];
  // It maintains worker Model
  regModel: Worker;
  // It maintains worker form display status. By default it will be false.
  showNew: Boolean = false;
  // It will be either 'Save' or 'Update' based on operation.
  submitType: string = 'Save';
  // It maintains table row index based on selection.
  selectedRow: number;
  // It maintains Array of countries.
  countries: string[] = ['US', 'UK', 'India', 'UAE'];
  constructor(
    private http: HttpClient,
    private workerService: WorkerService,
    )
   {}

  ngOnInit() {
    this.refreshPage();
  }

  private refreshPage() {
    this.workerService.getAll().subscribe(data => {
      console.log("got data" + JSON.stringify(data));
      this.workers = data;
    }, error => {
      console.log("got error" + error);
    });
  }

  // This method associate to New Button.
  onNew() {
    // Initiate new worker.
    this.regModel = new Worker();
    // Change submitType to 'Save'.
    this.submitType = 'Save';
    // display worker entry section.
    this.showNew = true;
  }

  // This method associate to Save Button.
  onSave() {
    if (this.submitType === 'Save') {
      // Push worker model object into worker list.
      // this.workers.push(this.regModel);

      this.workerService.add(this.regModel)
      .pipe(first())
      .subscribe(
        data => {
          swal.fire({
            title: 'Added!',
            text: 'Worker ' + ApplicationConstants.ADDED_SUCCESSFULLY,
            type: 'success',
            showCancelButton: false,
            timer: 3000
          });

          this.refreshPage();
          return true;
        },
        error => {
        swal.fire({
          title: 'Failed',
          text: 'Failed to add new worker!',
          type: 'error',
          showCancelButton: false,
        });
        return false;
      });
    } else {
      // Update the existing properties values based on model.
      this.workers[this.selectedRow].workerName = this.regModel.workerName;
      this.workers[this.selectedRow].description = this.regModel.description;
      this.workers[this.selectedRow].createdAt = this.regModel.createdAt;
      this.workers[this.selectedRow].updatedAt = this.regModel.updatedAt;

      this.workerService.update(this.regModel)
      .pipe(first())
      .subscribe(
        data => {
          console.log('Worker ' + ApplicationConstants.UPDATED_SUCCESSFULLY);
          this.refreshPage();
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
    // Hide worker entry section.
    this.showNew = false;
  }

  // This method associate to Edit Button.
  onEdit(index: number) {
    // Assign selected table row index.
    this.selectedRow = index;
    // Initiate new worker.
    this.regModel = new Worker();
    // Retrieve selected worker from list and assign to model.
    this.regModel = Object.assign({}, this.workers[this.selectedRow]);
    // Change submitType to Update.
    this.submitType = 'Update';
    // Display worker entry section.
    this.showNew = true;
  }

  // This method associate to Delete Button.
  onDelete(index: number, id: number) {
    // Delete the corresponding worker entry from the list.
    this.workers.splice(index, 1);

    this.workerService.deleteWorker(id)
    .pipe(first())
    .subscribe(
      data => {
        swal.fire({
          title: 'Deleted!',
          text: 'Worker ' + ApplicationConstants.DELETED_SUCCESSFULLY,
          type: 'success',
          showCancelButton: false,
          timer: 3000
        });

        return true;
      },
      error => {
      console.log('Failed to delete Worker ' + error);
      swal.fire({
        title: 'Failed',
        text: 'Failed to delete Worker!',
        type: 'error',
        showCancelButton: false,
      });

      return false;
    });
  }

  // This method associate toCancel Button.
  onCancel() {
    // Hide worker entry section.
    this.showNew = false;
  }


}
