import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { PageServiceService } from '../page-service.service';
declare var $: any;
@Component({
  selector: 'app-brand-owner',
  templateUrl: './brand-owner.component.html',
  styleUrls: ['./brand-owner.component.css']
})
export class BrandOwnerComponent implements OnInit, AfterViewInit {
  edit: boolean = false;
  boForm: FormGroup = new FormGroup({
    'bo-name': new FormControl('',
      Validators.required
    ),
    'bo-id': new FormControl(null, [
      Validators.required,
      Validators.minLength(5),
      Validators.maxLength(5)
    ]),
    'bo-status': new FormControl(true),
    'bo-cdate': new FormControl(''),
    'bo-email': new FormControl(null),
    'bo-phone': new FormControl(null),
    'bo-address': new FormControl(null),
    'bo-uid': new FormControl(null)
  });
  roles = {
    button: {
      save: true,
      delete: false,
      inactive: true,
      edit: true
    }
  }
  model_brandowner: any = this._getBrandOwnerModel();

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
          $('#bo-cb-isedit').prop('disabled', true);
          this.boForm.setValue(
            {
              'bo-uid': '-1',
              'bo-name': '',
              'bo-id': '',
              'bo-status': true,
              'bo-cdate': this.pgService.getDateString(new Date()),
              'bo-email': '',
              'bo-phone': '',
              'bo-address': '',
            },
          );
          $('#btn-bo-save').show();
        } else {
          this.getBrandOwnerById(id).then(
            (e: any) => {
              this.boForm.setValue(
                {
                  'bo-uid': e.id,
                  'bo-name': e.name,
                  'bo-id': e.code,
                  'bo-cdate': e.cdate,
                  'bo-status': e.status == 1 ? true : false,
                  'bo-email': '',
                  'bo-phone': '',
                  'bo-address': '',
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
      $('#btn-bo-save').show();
      $('#btn-bo-delete').show();
      $('#btn-bo-inactive').show();
    }else{
      $('#btn-bo-save').hide();
      $('#btn-bo-delete').hide();
      $('#btn-bo-inactive').hide();
    }
  }
  

  _getBrandOwnerModel() {
    return {
      rowNumber: 0,
      id: '',
      code: '',
      name: '',
      status: 1,
      cdate: '',
      mdate: ''
    }
  }
  getBrandOwnerById(id: string) {
    return new Promise((pro, reject) => {
      const url = this.pgService.config.url + "brandowner-setup/getbrand-byid/" + id;
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
    //console.log($('#setup-bo-date').val());
    //return;
    if (!this.boForm.valid) {
      alert("Fill all fields")
      return;
    }
    let obj = this._getBrandOwnerModel();
    obj.id = this.boForm.get('bo-uid')?.value;
    obj.code = this.boForm.get('bo-id')?.value;
    obj.name = this.boForm.get('bo-name')?.value;
    obj.status = this.boForm.get('bo-status')?.value == true ? 1 : 2;

    if (obj.id === '-1') {
      this.save(obj);
    } else {
      this.update(obj);
    }

  }
  update(obj: any) {
    const url = this.pgService.config.url + 'brandowner-setup/update'
    this.http.post(url, obj, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        window.alert("Successfully updated")
      },
      (error) => {

      }
    )
  }
  save(obj: any) {

    const url = this.pgService.config.url + 'brandowner-setup/save-bo'
    this.http.post(url, obj, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          window.alert("Successfully saved");
          this.router.navigate(['/brand-owner-list']);
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

    const url = this.pgService.config.url + 'brandowner-setup/delete'
    this.http.post(url, {
      'id': this.boForm.get('bo-uid')?.value
    }, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          window.alert("Successfully deleted");
          this.router.navigate(['/brand-owner-list'])
        } else window.alert(resp.message)
      },
      (error) => {
        window.alert("Server Error")
      }
    )
  }

}
