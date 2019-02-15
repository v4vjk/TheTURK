import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';

export class ApplicationConstants {

    constructor(
        private http: HttpClient
      ) { }

    public static FILE_UPLOADED='File uploaded successfully';
    public static NO_ROW_SELECTED = 'No row selected!';
    public static ADDED_SUCCESSFULLY = 'added successfully.';
    public static DELETED_SUCCESSFULLY = 'deleted successfully.';
    public static CREATED_SUCCESSFULLY = 'created successfully.';
    public static UPDATED_SUCCESSFULLY = 'updated successfully.';

 }