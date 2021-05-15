import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { PackTypeData } from '../model';
import { PageServiceService } from '../page-service.service';
declare var $:any;
@Component({
  selector: 'app-packtype-setup',
  templateUrl: './packtype-setup.component.html',
  styleUrls: ['./packtype-setup.component.css']
})
export class PacktypeSetupComponent implements OnInit,AfterViewInit {

  edit: boolean = false;
  myForm: FormGroup = new FormGroup({
    'packtype-id': new FormControl('-1'),
    'packtype-name': new FormControl('',
      Validators.required
    ),
    'packtype-code': new FormControl(null, [
      Validators.required
    ]),
    'packtype-status': new FormControl(true),
    'packtype-cdate': new FormControl(''),
    
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
          $('#packtype-cb-isedit').prop('disabled', true);
          this.myForm.setValue(
            {
              'packtype-id': '-1',
              'packtype-name': '',
              'packtype-code': '',
              'packtype-status': true,
              'packtype-cdate': this.pgService.getDateString(new Date()),
            },
          );
          $('#btn-packtype-save').show();
        } else {
          this.getPackTypeById(id).then(
            (e: any) => {
              this.myForm.setValue(
                {
                  'packtype-id': e.id,
                  'packtype-name': e.name,
                  'packtype-code': e.code,
                  'packtype-cdate': e.cdate,
                  'packtype-status': e.status == 1 ? true : false
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
      $('#btn-packtype-save').show();
      $('#btn-packtype-delete').show();
      $('#btn-packtype-inactive').show();
    }else{
      $('#btn-packtype-save').hide();
      $('#btn-packtype-delete').hide();
      $('#btn-packtype-inactive').hide();
    }
  }
  

  _getPackTypeModel():PackTypeData {
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
  getPackTypeById(id: string) {
    return new Promise((pro, reject) => {
      const url = this.pgService.config.url + "packtype-setup/getbyid/" + id;
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
    let obj = this._getPackTypeModel();
    obj.id = this.myForm.get('packtype-id')?.value;
    obj.code = this.myForm.get('packtype-code')?.value;
    obj.name = this.myForm.get('packtype-name')?.value;
    obj.status = this.myForm.get('packtype-status')?.value == true ? 1 : 2;

    if (obj.id === '-1') {
      this.save(obj);
    } else {
      this.update(obj);
    }

  }
  update(obj: any) {
    const url = this.pgService.config.url + 'packtype-setup/update'
    this.http.post(url, obj, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        window.alert("Successfully updated");
        this.router.navigate(['/packtype-list']);
      },
      (error) => {
        alert("Server don't response. Try again!")
      }
    )
  }
  save(obj: any) {
    const url = this.pgService.config.url + 'packtype-setup/save'
    this.http.post(url, obj, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          window.alert("Successfully saved");
          this.router.navigate(['/packtype-list']);
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
    const url = this.pgService.config.url + 'packtype-setup/delete'
    this.http.post(url, {
      'id': this.myForm.get('packtype-id')?.value
    }, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          window.alert("Successfully deleted");
          this.router.navigate(['/packtype-list'])
        } else window.alert(resp.message)
      },
      (error) => {
        window.alert("Server Error")
      }
    )
  }

}
