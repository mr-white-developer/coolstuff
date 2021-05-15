import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { WarehouseData, ViewResult, CompanyData } from '../model';
import { PageServiceService } from '../page-service.service';
declare var $: any;
@Component({
  selector: 'app-warehouse-setup',
  templateUrl: './warehouse-setup.component.html',
  styleUrls: ['./warehouse-setup.component.css']
})

export class WarehouseSetupComponent implements OnInit {
  edit: boolean = false;
  myFormGroup: FormGroup = new FormGroup({
    'wh-id': new FormControl('-1'),
    'wh-code': new FormControl('', [
      Validators.required,
    ]),
    'wh-name': new FormControl('',
      Validators.required
    ),
    'wh-status': new FormControl(true),
    'wh-cdate': new FormControl(this.pgService.getDateString(new Date())),
    'com-id': new FormControl('-1',Validators.minLength(19)),
    'com-name': new FormControl('')

  });
  roles = {
    button: {
      save: true,
      delete: true,
      inactive: true,
      edit: true
    }
  }
  _companyList:any = [];

  constructor(private activeRoute: ActivatedRoute,
    private http: HttpClient,
    private router: Router,
    private pgService: PageServiceService) {

  }
  ngAfterViewInit(): void {
    this.activeRoute.queryParams.subscribe(
      (p: Params) => {
        const id = p['id'];
        if (id == '-1' || id == undefined) {
          this.edit = true;
          $('#wh-cb-isedit').prop('disabled', true);
          this.myFormGroup.setValue(
            {
              'wh-id': '-1',
              'wh-code': '',
              'wh-name': '',
              'wh-status': true,
              'wh-cdate': this.pgService.getDateString(new Date()),
              'com-id': '-1',
              'com-name':''
            },
          );
        
          $('#btn-wh-save').show();
        }else {
          this.getById(id).then(
            (e: WarehouseData) => {
              this.myFormGroup.setValue(
                {
                  'wh-id': e.id,
                  'wh-code': e.code,
                  'wh-name': e.name,
                  'wh-status': e.status,
                  'wh-cdate': e.cdate,
                  'com-id': e.company.id,
                  'com-name': e.company.name
                },
              );
           
            }
          ).catch(errormes => {
            let i = confirm(errormes);
            this.router.navigate(['warehouse-list']);
          });
          this.editOn();
        }
      }
    );
  }

  ngOnInit(): void {

    this.pgService.getCompanyList({ id:'-1'}).then( (e:ViewResult<CompanyData>)=>{
      this._companyList = e.list;
    }).catch( e=>{
      alert(e);
      this._companyList = [];
    } );

  }
  goChild(myid:string){
   
    //this.router.navigate(['/company-setup'],{queryParams : {id:myid,auth: this.pgService.getRandomAuth()},})
    
   
  }
  editOn() {
    if (this.edit) {
      $('#btn-wh-save').show();
      $('#btn-wh-delete').show();
      $('#btn-wh-inactive').show();
    } else {
      $('#btn-wh-save').hide();
      $('#btn-wh-delete').hide();
      $('#btn-wh-inactive').hide();
    }
  }


  _getWareHouseModel(): WarehouseData {
    return {
      currentRow: -1,
      maxRowsPerPage: -1,
      rowNumber: 0,
      id: '',
      code: '',
      name: '',
      status: 1,
      cdate: '',
      company: {
        currentRow: -1,
        maxRowsPerPage: -1,
        rowNumber: 0,
        id: '-1',
        code: '',
        name: '',
        status: 1,
        cdate: '',
        mdate:'',
        ownerName: '',
        email: '',
        phone: '',
        warehouses:[]
      }
    }
  }
  getById(id: string) {
    return new Promise<WarehouseData>((pro, reject) => {
      const url = this.pgService.config.url + "warehouse-setup/getbyid/" + id;
      this.http.get<ViewResult<WarehouseData>>(url, this.pgService.getOptions()).subscribe(
        (resp: ViewResult<WarehouseData>) => {
          if (resp.status == 200) {
            pro(resp.data);
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
    let obj = this._getWareHouseModel();
    obj.id = this.myFormGroup.get('wh-id')?.value ;
    obj.code = this.myFormGroup.get('wh-code')?.value;
    obj.name = this.myFormGroup.get('wh-name')?.value;
    obj.status = this.myFormGroup.get('wh-status')?.value == true ? 1 : 2;
    obj.company.id = this.myFormGroup.get('com-id')?.value;
    if (obj.id === '-1') {
      this.save(obj);
    } else {
      this.update(obj);
    }

  }
  update(obj: any) {
    const url = this.pgService.config.url + 'warehouse-setup/update'
    this.http.post<ViewResult<WarehouseData>>(url, obj, this.pgService.getOptions()).subscribe(
      (resp: ViewResult<WarehouseData>) => {
        if(resp.status == 200){
        window.alert("Successfully updated");
        this.router.navigate(['/warehouse-list']);
      }else{
          alert("Client Error")
        }
      },
      (error) => {
        alert("Can't connect to server")
      }
    )
  }
  save(obj: WarehouseData) {
    const url = this.pgService.config.url + 'warehouse-setup/save'
    this.http.post<ViewResult<WarehouseData>>(url, obj, this.pgService.getOptions()).subscribe(
      (resp: ViewResult<WarehouseData>) => {
        if (resp.status == 200) {
          alert("Successfully saved");
          this.router.navigate(['/warehouse-list']);
        }
        else {
          window.alert(resp.message)
        }
      },
      (error) => {
        window.alert("Can't connect to server")
      }
    )
  }
  delete() {

    const url = this.pgService.config.url + 'warehouse-setup/delete'
    this.http.post(url, {
      'id': this.myFormGroup.get('wh-id')?.value
    }, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          window.alert("Successfully deleted");
          this.router.navigate(['/warehouse-list'])
        } else window.alert(resp.message)
      },
      (error) => {
        window.alert("Can't connect to server")
      }
    )
  }


}
