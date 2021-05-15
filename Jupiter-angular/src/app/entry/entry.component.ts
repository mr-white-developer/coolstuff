import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PageServiceService } from '../page-service.service';

@Component({
  selector: 'app-entry',
  templateUrl: './entry.component.html',
  styleUrls: ['./entry.component.css']
})
export class EntryComponent implements OnInit, AfterViewInit {
  myForm: FormGroup = new FormGroup({
    
    'brand-owner': new FormControl('', Validators.required),
   
    'brandowner-list': new FormControl([]),
   

  });
  constructor(private pgService: PageServiceService) { }
  ngAfterViewInit(): void {
    this.pgService.getBrandOwners({ id: '-1' }).then(
      (r: any) => {
        this.myForm.get('brandowner-list')?.setValue(
          r.list
        );
      }
    )

  }

  ngOnInit(): void {
  }
  public mapStock = function (option: any, value: any): boolean {
    return option.id === value.id;
  }
  click() {
    this.myForm.get('brand-owner')?.setValue(
      {
       
        "id": "2104280924072000004",
     
        "name": "Apple Inc",
       
    }
    )
  }
}
