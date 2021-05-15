import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { PriceTypeData } from '../model';
import { PageServiceService } from '../page-service.service';
declare var $:any;
@Component({
  selector: 'app-price-type-setup',
  templateUrl: './price-type-setup.component.html',
  styleUrls: ['./price-type-setup.component.css']
})
export class PriceTypeSetupComponent implements OnInit {
  edit: boolean = false;
  myForm: FormGroup = new FormGroup({
    'pricetype-id': new FormControl('-1'),
    'pricetype-name': new FormControl('',
      Validators.required
    ),
    'pricetype-code': new FormControl(null, [
      Validators.required
    ]),
    'pricetype-status': new FormControl(true),
    'pricetype-cdate': new FormControl(''),
    
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
          $('#pricetype-cb-isedit').prop('disabled', true);
          this.myForm.setValue(
            {
              'pricetype-id': '-1',
              'pricetype-name': '',
              'pricetype-code': '',
              'pricetype-status': true,
              'pricetype-cdate': this.pgService.getDateString(new Date()),
            },
          );
          $('#btn-pricetype-save').show();
        } else {
          this.getPriceTypeById(id).then(
            (e: any) => {
              this.myForm.setValue(
                {
                  'pricetype-id': e.id,
                  'pricetype-name': e.name,
                  'pricetype-code': e.code,
                  'pricetype-cdate': e.cdate,
                  'pricetype-status': e.status == 1 ? true : false
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
      $('#btn-pricetype-save').show();
      $('#btn-pricetype-delete').show();
      $('#btn-pricetype-inactive').show();
    }else{
      $('#btn-pricetype-save').hide();
      $('#btn-pricetype-delete').hide();
      $('#btn-pricetype-inactive').hide();
    }
  }
  

  _getPriceTypeModel():PriceTypeData {
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
  getPriceTypeById(id: string) {
    return new Promise((pro, reject) => {
      const url = this.pgService.config.url + "pricetype-setup/getbyid/" + id;
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
    let obj = this._getPriceTypeModel();
    obj.id = this.myForm.get('pricetype-id')?.value;
    obj.code = this.myForm.get('pricetype-code')?.value;
    obj.name = this.myForm.get('pricetype-name')?.value;
    obj.status = this.myForm.get('pricetype-status')?.value == true ? 1 : 2;

    if (obj.id === '-1') {
      this.save(obj);
    } else {
      this.update(obj);
    }

  }
  update(obj: any) {
    const url = this.pgService.config.url + 'pricetype-setup/update'
    this.http.post(url, obj, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        window.alert("Successfully updated");
        this.router.navigate(['/price-type-list']);
      },
      (error) => {
        alert("Server don't response. Try again!")
      }
    )
  }
  save(obj: any) {
    const url = this.pgService.config.url + 'pricetype-setup/save'
    this.http.post(url, obj, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          window.alert("Successfully saved");
          this.router.navigate(['/price-type-list']);
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
    const url = this.pgService.config.url + 'pricetype-setup/delete'
    this.http.post(url, {
      'id': this.myForm.get('pricetype-id')?.value
    }, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          window.alert("Successfully deleted");
          this.router.navigate(['/price-type-list'])
        } else window.alert(resp.message)
      },
      (error) => {
        window.alert("Server Error")
      }
    )
  }
}
