import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { PageServiceService } from '../page-service.service';
declare var $: any;

@Component({
  selector: 'app-brand',
  templateUrl: './brand.component.html',
  styleUrls: ['./brand.component.css']
})
export class BrandComponent implements OnInit, AfterViewInit {
  edit: boolean = false;
  boForm: FormGroup = new FormGroup({
    'b-uid': new FormControl('',
      Validators.required
    ),
    'b-name': new FormControl('',
      Validators.required
    ),
    'b-code': new FormControl(null, [
      Validators.required
    ]),
    'b-status': new FormControl(true),
    'b-cdate': new FormControl(this.pgService.getDateString(new Date())),
    'bo-uid': new FormControl('-1'),
    'bo-name': new FormControl('')
  });
  roles = {
    button: {
      save: true,
      delete: true,
      inactive: true,
      edit: true
    }
  }
  _brandOwnerList: any = [];
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
          $('#b-cb-isedit').prop('disabled', true);
          this.boForm.setValue(
            {
              'b-uid': '-1',
              'b-name': '',
              'b-code': '',
              'b-status': true,
              'b-cdate': this.pgService.getDateString(new Date()),
              'bo-uid': '-1',
              'bo-name': ''
            },
          );
          $('#btn-b-save').show();
        } else {
          this.getBrandById(id).then(
            (e: any) => {
              this.boForm.setValue(
                {
                  'b-uid': e.id,
                  'b-name': e.name,
                  'b-code': e.code,
                  'b-cdate': e.cdate,
                  'b-status': e.status == 1 ? true : false,
                  'bo-uid': e.brandOwner.id,
                  'bo-name': e.brandOwner.name
                },
              );
            }
          ).catch(errormes => {
            let i = confirm(errormes);
            this.router.navigate(['brand-list']);
          });
          this.editOn();
        }
      }
    );


  }

  ngOnInit(): void {
    this.pgService.getBrandOwners({
      's1': 1
    }).then((resp: any) => {
      this._brandOwnerList = resp.list;
    })

  }
  editOn() {
    if (this.edit) {
      $('#btn-b-save').show();
      $('#btn-b-delete').show();
      $('#btn-b-inactive').show();
    } else {
      $('#btn-b-save').hide();
      $('#btn-b-delete').hide();
      $('#btn-b-inactive').hide();
    }
  }


  _getBrandModel() {
    return {
      rowNumber: 0,
      id: '',
      code: '',
      name: '',
      status: 1,
      cdate: '',
      mdate: '',
      brandOwner: {
        id: ''
      }
    }
  }
  getBrandById(id: string) {
    return new Promise((pro, reject) => {
      const url = this.pgService.config.url + "brand-setup/getbrand-byid/" + id;
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
    if (!this.boForm.valid) {
      alert("Fill all fields")
      return;
    }
    let obj = this._getBrandModel();
    obj.id = this.boForm.get('b-uid')?.value;
    obj.code = this.boForm.get('b-code')?.value;
    obj.name = this.boForm.get('b-name')?.value;
    obj.status = this.boForm.get('b-status')?.value == true ? 1 : 2;
    obj.brandOwner.id = this.boForm.get('bo-uid')?.value;
    if (obj.id === '-1') {
      this.save(obj);
    } else {
      this.update(obj);
    }

  }
  update(obj: any) {
    const url = this.pgService.config.url + 'brand-setup/update'
    this.http.post(url, obj, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        window.alert("Successfully updated")
      },
      (error) => {

      }
    )
  }
  save(obj: any) {

    const url = this.pgService.config.url + 'brand-setup/save-brands'
    this.http.post(url, obj, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          alert("Successfully saved");
          this.router.navigate(['/brand-list']);
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

    const url = this.pgService.config.url + 'brand-setup/delete'
    this.http.post(url, {
      'id': this.boForm.get('b-uid')?.value
    }, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          window.alert("Successfully deleted");
          this.router.navigate(['/brand-list'])
        } else window.alert(resp.message)
      },
      (error) => {
        window.alert("Server Error")
      }
    )
  }
}
