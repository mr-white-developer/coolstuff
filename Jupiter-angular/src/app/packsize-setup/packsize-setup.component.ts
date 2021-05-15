import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { PackSizeData } from '../model';
import { PageServiceService } from '../page-service.service';
declare var $:any;
@Component({
  selector: 'app-packsize-setup',
  templateUrl: './packsize-setup.component.html',
  styleUrls: ['./packsize-setup.component.css']
})
export class PacksizeSetupComponent implements OnInit ,AfterViewInit{

  edit: boolean = false;
  myForm: FormGroup = new FormGroup({
    'packsize-id': new FormControl('-1'),
    'packsize-name': new FormControl('',
      Validators.required
    ),
    'packsize-code': new FormControl('', [
      Validators.required
    ]),
    'packsize-status': new FormControl(true),
    'packsize-cdate': new FormControl(''),
    
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
          $('#packsize-cb-isedit').prop('disabled', true);
          this.myForm.setValue(
            {
              'packsize-id': '-1',
              'packsize-name': '',
              'packsize-code': '',
              'packsize-status': true,
              'packsize-cdate': this.pgService.getDateString(new Date()),
            },
          );
          $('#btn-packsize-save').show();
        } else {
          this.getPackSizeById(id).then(
            (e: any) => {
              this.myForm.setValue(
                {
                  'packsize-id': e.id,
                  'packsize-name': e.name,
                  'packsize-code': e.code,
                  'packsize-cdate': e.cdate,
                  'packsize-status': e.status == 1 ? true : false
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
      $('#btn-packsize-save').show();
      $('#btn-packsize-delete').show();
      $('#btn-packsize-inactive').show();
    }else{
      $('#btn-packsize-save').hide();
      $('#btn-packsize-delete').hide();
      $('#btn-packsize-inactive').hide();
    }
  }
  

  _getPackSizeModel():PackSizeData {
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
  getPackSizeById(id: string) {
    return new Promise((pro, reject) => {
      const url = this.pgService.config.url + "packsize-setup/getbyid/" + id;
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
    let obj = this._getPackSizeModel();
    obj.id = this.myForm.get('packsize-id')?.value;
    obj.code = this.myForm.get('packsize-code')?.value;
    obj.name = this.myForm.get('packsize-name')?.value;
    obj.status = this.myForm.get('packsize-status')?.value == true ? 1 : 2;

    if (obj.id === '-1') {
      this.save(obj);
    } else {
      this.update(obj);
    }

  }
  update(obj: any) {
    const url = this.pgService.config.url + 'packsize-setup/update'
    this.http.post(url, obj, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        window.alert("Successfully updated");
        this.router.navigate(['/packsize-list']);
      },
      (error) => {
        alert("Server don't response. Try again!")
      }
    )
  }
  save(obj: any) {
    const url = this.pgService.config.url + 'packsize-setup/save'
    this.http.post(url, obj, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          window.alert("Successfully saved");
          this.router.navigate(['/packsize-list']);
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
    const url = this.pgService.config.url + 'packsize-setup/delete'
    this.http.post(url, {
      'id': this.myForm.get('packsize-id')?.value
    }, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          window.alert("Successfully deleted");
          this.router.navigate(['/packsize-list'])
        } else window.alert(resp.message)
      },
      (error) => {
        window.alert("Server Error")
      }
    )
  }

}
