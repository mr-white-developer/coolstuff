import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { CompanyData, ViewResult } from '../model';
import { PageServiceService } from '../page-service.service';
declare var $: any
@Component({
  selector: 'app-company-setup',
  templateUrl: './company-setup.component.html',
  styleUrls: ['./company-setup.component.css']
})
export class CompanySetupComponent implements OnInit, AfterViewInit {

  edit: boolean = false;
  myFormGroup: FormGroup = new FormGroup({
    'company-id': new FormControl('-1'),
    'company-code': new FormControl('', Validators.required),
    'company-name': new FormControl('', Validators.required),
    'company-status': new FormControl(true),
    'company-cdate': new FormControl(this.pgService.getDateString(new Date())),
    'company-pname': new FormControl(''),
    'company-email': new FormControl(''),
    'company-phone': new FormControl('', Validators.required)
  });
  roles = {
    button: {
      save: true,
      delete: true,
      inactive: true,
      edit: true
    }
  }
  constructor(private activeRoute: ActivatedRoute,
    private http: HttpClient,
    private router: Router,
    private pgService: PageServiceService) {

  }
  ngAfterViewInit(): void {
    this.activeRoute.queryParams.subscribe(
      (p: Params) => {
        const id:string = p['id'];
        const authen:number = p['auth'];
        // if(this.pgService.authen == authen ) {
        //   this.roles.button.edit = true;
        // }else {
        //   this.roles.button.edit = false;
        // }
        if (id == '-1') {
          this.edit = true;
          $('#company-cb-isedit').prop('disabled', true);
          this.myFormGroup.setValue(
            {
              'company-id': '-1',
              'company-code': '',
              'company-name': '',
              'company-status': true,
              'company-cdate': this.pgService.getDateString(new Date()),
              'company-pname': '',
              'company-email': '',
              'company-phone': ''
            },
          );
          $('#btn-company-save').show();
         
        } else {
          this.getCompanyById(id).then(
            (e: CompanyData) => {
              this.myFormGroup.setValue(
                {
                  'company-id': e.id,
                  'company-code': e.code,
                  'company-name': e.name,
                  'company-status': e.status == 1 ? true: false,
                  'company-cdate': e.cdate,
                  'company-pname': e.ownerName,
                  'company-email': e.email,
                  'company-phone': e.phone
                },
              );
            }
          ).catch(e => {
             alert(e);
              this.router.navigate(['company-list']);
          });
          
          this.editOn();
        }
      }
    );


  }

  ngOnInit(): void {
    

  }
  editOn() {
    if (this.edit) {
      $('#btn-company-save').show();
      $('#btn-company-delete').show();
      $('#btn-company-inactive').show();
    } else {
      $('#btn-company-save').hide();
      $('#btn-company-delete').hide();
      $('#btn-company-inactive').hide();
    }
  }


  _getCompanyModel() : CompanyData{
    return {
      currentRow: -1,
      maxRowsPerPage:-1,
      rowNumber:0,
      id:'-1',
      code:'',
      name:'',
      status: -1,
      cdate:'',
      mdate:'',
      ownerName:'',
      email:'',
      phone:'',
      warehouses:[]
    }
  }
  getCompanyById(id: string) {
    return new Promise<CompanyData>((pro, reject) => {
      const url = this.pgService.config.url + "company-setup/getbyid/" + id;
      this.http.get<ViewResult<CompanyData>>(url, this.pgService.getOptions()).subscribe(
        (resp: ViewResult<CompanyData>) => {
          if (resp.status == 200) {
            pro(resp.data)
          } else {
            reject("No record!");
          }
        },
        error => {
          reject("Server error");
        }
      )
    })
  }
  submit() {
    if (!this.myFormGroup.valid) {
      alert("Fill all fields")
      return;
    }
    let obj = this._getCompanyModel();
    obj.id = this.myFormGroup.get('company-id')?.value;
    obj.code = this.myFormGroup.get('company-code')?.value;
    obj.name= this.myFormGroup.get('company-name')?.value;
    obj.status= this.myFormGroup.get('company-status')?.value == true ? 1:2;
    obj.cdate= '';
    obj.mdate= '';
    obj.ownerName= this.myFormGroup.get('company-pname')?.value;
    obj.email= this.myFormGroup.get('company-email')?.value;
    obj.phone= this.myFormGroup.get('company-phone')?.value;
   
    if (obj.id === '-1') {
      this.save(obj);
    } else {
      this.update(obj);
    }

  }
  update(obj: any) {
    const url = this.pgService.config.url + 'company-setup/update'
    this.http.post(url, obj, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        window.alert("Successfully updated")
      },
      (error) => {

      }
    )
  }
  save(obj: any) {
    const url = this.pgService.config.url + 'company-setup/save'
    this.http.post<ViewResult<CompanyData>>(url, obj, this.pgService.getOptions()).subscribe(
      (resp: ViewResult<CompanyData>) => {
        if (resp.status == 200) {
          alert("Successfully saved");
          this.router.navigate(['/company-list']);
        }
        else {
          alert(resp.message)
        }
      },
      (error) => {
        alert("Server Error")
      }
    )
  }
  delete() {

    const url = this.pgService.config.url + 'company-setup/delete'
    this.http.post(url, {
      'id': this.myFormGroup.get('company-id')?.value
    }, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          window.alert("Successfully deleted");
          this.router.navigate(['/company-list'])
        } else window.alert(resp.message)
      },
      (error) => {
        window.alert("Server Error")
      }
    )
  }

}
