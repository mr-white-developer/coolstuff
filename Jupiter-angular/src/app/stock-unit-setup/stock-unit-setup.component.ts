import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { UomData } from '../model';
import { PageServiceService } from '../page-service.service';
declare var $ : any;
@Component({
  selector: 'app-stock-unit-setup',
  templateUrl: './stock-unit-setup.component.html',
  styleUrls: ['./stock-unit-setup.component.css']
})
export class StockUnitSetupComponent implements OnInit,AfterViewInit {

  
  edit: boolean = false;
  myForm: FormGroup = new FormGroup({
    'uom-id': new FormControl('-1'),
    'uom-name': new FormControl('',
      Validators.required
    ),
    'uom-code': new FormControl(null, [
      Validators.required
    ]),
    'uom-status': new FormControl(true),
    'uom-cdate': new FormControl(''),
    
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
        const id = p['id'];
        if (id == '-1') {
          this.edit = true;
          $('#uom-cb-isedit').prop('disabled', true);
          this.myForm.setValue(
            {
              'uom-id': '-1',
              'uom-name': '',
              'uom-code': '',
              'uom-status': true,
              'uom-cdate': this.pgService.getDateString(new Date()),
            },
          );
          $('#btn-uom-save').show();
        } else {
          this.getUomById(id).then(
            (e: any) => {
              this.myForm.setValue(
                {
                  'uom-id': e.id,
                  'uom-name': e.name,
                  'uom-code': e.code,
                  'uom-cdate': e.cdate,
                  'uom-status': e.status == 1 ? true : false
                },
              );
            }
          ).catch(errormes => {
            let i = confirm(errormes);
            this.router.navigate(['brand-owner-list']);
          });
          this.editOn();
        }
      }
    );
  }

  ngOnInit(): void {



  }
  editOn(){
    if(this.edit){
      $('#btn-uom-save').show();
      $('#btn-uom-delete').show();
      $('#btn-uom-inactive').show();
    }else{
      $('#btn-uom-save').hide();
      $('#btn-uom-delete').hide();
      $('#btn-uom-inactive').hide();
    }
  }
  

  _getUomModel():UomData {
    return {
      currentRow:-1,
      maxRowsPerPage:-1,
      rowNumber:0,
      id:'-1',
      code:'',
      name:'',
      status:-1,
      cdate:'',
    }
  }
  getUomById(id: string) {
    return new Promise((pro, reject) => {
      const url = this.pgService.config.url + "uom-setup/getbyid/" + id;
      this.http.get(url, this.pgService.getOptions()).subscribe(
        (resp: any) => {
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

    if (!this.myForm.valid) {
      alert("Fill all fields")
      return;
    }
    let obj = this._getUomModel();
    obj.id = this.myForm.get('uom-id')?.value;
    obj.code = this.myForm.get('uom-code')?.value;
    obj.name = this.myForm.get('uom-name')?.value;
    obj.status = this.myForm.get('uom-status')?.value == true ? 1 : 2;

    if (obj.id === '-1') {
      this.save(obj);
    } else {
      this.update(obj);
    }

  }
  update(obj: any) {
    const url = this.pgService.config.url + 'uom-setup/update'
    this.http.post(url, obj, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        window.alert("Successfully updated");
        this.router.navigate(['/uom-list']);
      },
      (error) => {
        alert("Server don't response. Try again!")
      }
    )
  }
  save(obj: any) {
    const url = this.pgService.config.url + 'uom-setup/save'
    this.http.post(url, obj, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          window.alert("Successfully saved");
          this.router.navigate(['/uom-list']);
        }
        else {
          window.alert(resp.message)
        }
      },
      (error) => {
        window.alert("Server Error")
      }
    )
  }
  delete() {
    const url = this.pgService.config.url + 'uom-setup/delete'
    this.http.post(url, {
      'id': this.myForm.get('uom-id')?.value
    }, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          window.alert("Successfully deleted");
          this.router.navigate(['/uom-list'])
        } else window.alert(resp.message)
      },
      (error) => {
        window.alert("Server Error")
      }
    )
  }

}
