import { Component, OnInit } from '@angular/core';
import { NgbModule, NgbDate, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { WorkerService } from '../services';

class Registration {
  constructor(
    public name: string = '',
    public description: string = '',
    public createdAt: NgbDateStruct = null,
    public updatedAt: NgbDateStruct = null,
  ) {}
}

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

 
  // It maintains list of Registrations
  registrations: Registration[] = [];
  // It maintains registration Model
  regModel: Registration;
  // It maintains registration form display status. By default it will be false.
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
  ) {
    // Add default registration data.
    this.registrations.push(new Registration('Johan', 'Peter', {year: 1980, month: 5, day: 12}, {year: 1980, month: 5, day: 12}));
    this.registrations.push(new Registration('Mohamed', 'Tariq', {year: 1975, month: 12, day: 3}, {year: 1975, month: 12, day: 3}));
    this.registrations.push(new Registration('Nirmal', 'Kumar', {year: 1970, month: 7, day: 25}, {year: 1970, month: 7, day: 25}));
    
  }

  ngOnInit() {
    this.workerService.getAll().subscribe(
      data => {
         console.log("got data" + JSON.stringify(data));
     },
      error => {
         console.log("got error" + error);
     });
  }

  // This method associate to New Button.
  onNew() {
    // Initiate new registration.
    this.regModel = new Registration();
    // Change submitType to 'Save'.
    this.submitType = 'Save';
    // display registration entry section.
    this.showNew = true;
  }

  // This method associate to Save Button.
  onSave() {
    if (this.submitType === 'Save') {
      // Push registration model object into registration list.
      this.registrations.push(this.regModel);
    } else {
      // Update the existing properties values based on model.
      this.registrations[this.selectedRow].name = this.regModel.name;
      this.registrations[this.selectedRow].description = this.regModel.description;
      this.registrations[this.selectedRow].createdAt = this.regModel.createdAt;
      this.registrations[this.selectedRow].updatedAt = this.regModel.updatedAt;
    }
    // Hide registration entry section.
    this.showNew = false;
  }

  // This method associate to Edit Button.
  onEdit(index: number) {
    // Assign selected table row index.
    this.selectedRow = index;
    // Initiate new registration.
    this.regModel = new Registration();
    // Retrieve selected registration from list and assign to model.
    this.regModel = Object.assign({}, this.registrations[this.selectedRow]);
    // Change submitType to Update.
    this.submitType = 'Update';
    // Display registration entry section.
    this.showNew = true;
  }

  // This method associate to Delete Button.
  onDelete(index: number) {
    // Delete the corresponding registration entry from the list.
    this.registrations.splice(index, 1);
  }

  deleteUser(id: String) {
    // this.userService.deleteUser(id)
    // .pipe(first())
    // .subscribe(
    //   data => {
    //     this.notificationService.showNotification('top', 'right', 'success', 'User ' + UserConstants.DELETED_SUCCESSFULLY);
    //     return true;
    //   },
    //   error => {
    //   console.log(error);
    //     this.notificationService.showNotification('top', 'right', 'danger', error);
    //     return false;
    // })
    // ;
  }

  // This method associate toCancel Button.
  onCancel() {
    // Hide registration entry section.
    this.showNew = false;
  }


}
