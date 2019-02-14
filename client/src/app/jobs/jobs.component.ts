import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpEventType, HttpResponse } from '@angular/common/http';
import { first } from 'rxjs/operators';
import { ApplicationConstants } from '../models/app-constants';
import { JobsService } from '../services/jobs-service';
import { Job } from '../models/Job';
import swal from 'sweetalert2';

@Component({
  selector: 'app-jobs',
  templateUrl: './jobs.component.html',
  styleUrls: ['./jobs.component.scss'],
  providers: [
    JobsService,
    HttpClient
   ]
})
export class JobsComponent implements OnInit {

  percentDone: number;
  uploadSuccess: boolean;

  // It maintains list of jobs
  jobs: Job[] = [];
  // It maintains Job Model
  regModel: Job;
  // It maintains Job form display status. By default it will be false.
  showNew: Boolean = false;
  // It will be either 'Save' or 'Update' based on operation.
  submitType: string = 'Save';
  // It maintains table row index based on selection.
  selectedRow: number;
  // It maintains Array of countries.
  countries: string[] = ['US', 'UK', 'India', 'UAE'];
  constructor(
    private http: HttpClient,
    private jobService: JobsService,
    )
   {}

  ngOnInit() {
    this.refreshPage();
  }

  private refreshPage() {
    this.jobService.getAll().subscribe(data => {
      console.log("got data" + JSON.stringify(data));
      this.jobs = data;
    }, error => {
      console.log("got error" + error);
    });
  }

  // This method associate to New Button.
  onNew() {
    // Initiate new Job.
    this.regModel = new Job();
    // Change submitType to 'Save'.
    this.submitType = 'Save';
    // display Job entry section.
    this.showNew = true;
  }

  // This method associate to Save Button.
  onSave() {
    if (this.submitType === 'Save') {
      // Push Job model object into Job list.

      this.jobService.add(this.regModel)
      .pipe(first())
      .subscribe(
        data => {
          this.refreshPage();
          
          swal.fire({
            title: 'Added',
            text: 'Job ' + ApplicationConstants.ADDED_SUCCESSFULLY,
            type: 'success',
            showCancelButton: false,
            timer: 3000
          });

          return true;
        },
        error => {
        console.log('Failed to update job ' + error);
        swal.fire({
          title: 'Failed',
          text: 'Failed to add new Job!',
          type: 'error',
          showCancelButton: false,
        });

        return false;
      });
      

      
    } else {
      // Update the existing properties values based on model.
      this.jobs[this.selectedRow].jobName = this.regModel.jobName;
      this.jobs[this.selectedRow].className = this.regModel.className;
      this.jobs[this.selectedRow].jarName = this.regModel.jarName;
      this.jobs[this.selectedRow].jarPath = this.regModel.jarPath;
      this.jobs[this.selectedRow].description = this.regModel.description;
      this.jobs[this.selectedRow].createdAt = this.regModel.createdAt;
      this.jobs[this.selectedRow].updatedAt = this.regModel.updatedAt;

      this.jobService.update(this.regModel)
      .pipe(first())
      .subscribe(
        data => {
          console.log('Job ' + ApplicationConstants.UPDATED_SUCCESSFULLY);
          this.refreshPage();
          return true;
        },
        error => {
        console.log('Failed to update job ' + error);
        swal.fire({
          title: 'Failed',
          text: 'Failed to update Job!',
          type: 'error',
          showCancelButton: false,
        });
        return false;
      });
    }
    // Hide Job entry section.
    this.showNew = false;
  }

  // This method associate to Edit Button.
  onEdit(index: number) {
    // Assign selected table row index.
    this.selectedRow = index;
    // Initiate new Job.
    this.regModel = new Job();
    // Retrieve selected Job from list and assign to model.
    this.regModel = Object.assign({}, this.jobs[this.selectedRow]);
    // Change submitType to Update.
    this.submitType = 'Update';
    // Display Job entry section.
    this.showNew = true;
  }

  // This method associate to Delete Button.
  onDelete(index: number, id: number) {
    // Delete the corresponding Job entry from the list.
    this.jobs.splice(index, 1);

    this.jobService.deleteJob(id)
    .pipe(first())
    .subscribe(
      data => {
        swal.fire({
          title: 'Deleted!',
          text: 'Job ' + ApplicationConstants.DELETED_SUCCESSFULLY,
          type: 'success',
          showCancelButton: false,
          timer: 3000
        });

        return true;
      },
      error => {
      console.log('Failed to delete Job ' + error);

      swal.fire({
        title: 'Failed',
        text: 'Failed to delete Job!',
        type: 'error',
        showCancelButton: false,
      });

      return false;
    });
  }

  // This method associate toCancel Button.
  onCancel() {
    // Hide Job entry section.
    this.showNew = false;
  }

  upload(files: File[]){
    //pick from one of the 4 styles of file uploads below
    this.uploadAndProgress(files);
  }

    
  uploadAndProgress(files: File[]){
    console.log(files)
    var formData = new FormData();
    Array.from(files).forEach(f => formData.append('file',f))
    
    this.jobService.uploadFile(formData).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.percentDone = Math.round(100 * event.loaded / event.total);
      }
      else if (event instanceof HttpResponse) {
        this.uploadSuccess = true;
        this.regModel.jarName = files[0].name;
      }
    });

  }


}
