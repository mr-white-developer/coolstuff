import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { CategoryData, SubCategoryData, ViewResult } from '../model';
import { PageServiceService } from '../page-service.service';
import Alert from 'bootstrap/js/dist/alert';

declare var $: any;
@Component({
  selector: 'app-category-setup',
  templateUrl: './category-setup.component.html',
  styleUrls: ['./category-setup.component.css']
})

export class CategorySetupComponent implements OnInit, AfterViewInit {
  @ViewChild('temp') subTemplate: any;

  edit: boolean = false;
  myCategoryFormGroup: FormGroup = new FormGroup({
    'cat-id': new FormControl('-1'),
    'cat-code': new FormControl(''),
    'cat-name': new FormControl(''),
    'cat-status': new FormControl(true),
    'cat-cdate': new FormControl(this.pgService.getDateString(new Date())),
    'cat-sublist': new FormControl([]),
  });
  mySubCategoryFormGroup: FormGroup = new FormGroup({
    'sub-id': new FormControl('-1'),
    'sub-code': new FormControl('', Validators.required),
    'sub-name': new FormControl('', Validators.required),
    'sub-status': new FormControl(true),
    'sub-cdate': new FormControl(this.pgService.getDateString(new Date())),
    'cat-id': new FormControl('-1'),

  });
  subCategoryList: any = [];

  roles = {
    button: {
      save: true,
      delete: true,
      inactive: true,
      edit: true
    }
  }
  _companyList: any = [];

  // myCategory = this._getCategoryData();
  // mySubCategory = this._getSubCategoryData();

  constructor(private activeRoute: ActivatedRoute,
    private http: HttpClient,
    private router: Router,
    private pgService: PageServiceService,
   ) {

  }
  ngAfterViewInit(): void {
    this.activeRoute.queryParams.subscribe(
      async (p: Params) => {
        const id = p['id'];
        if (id == '-1' || id == undefined) {
          this.edit = true;
          $('#wh-cb-isedit').prop('disabled', true);
          this.myCategoryFormGroup.setValue(
            {
              'cat-id': '-1',
              'cat-code': '',
              'cat-name': '',
              'cat-status': -1,
              'cat-cdate': this.pgService.getDateString(new Date()),
              'cat-sublist': [],
            },
          );
          this.subCategoryList = [];
          $('#btn-category-save').show();
        } else {
          console.log('before getid')
          await this.getById(id).then(
            (e: CategoryData) => {
              console.log('in getid')
              this.myCategoryFormGroup.setValue(
                {
                  'cat-id': e.id,
                  'cat-code': e.code,
                  'cat-name': e.name,
                  'cat-status': e.status,
                  'cat-cdate': e.cdate,
                  'cat-sublist': e.subCategories,
                },
              );

            }
          ).catch(errormes => {
            let i = confirm(errormes);
            this.router.navigate(['category']);
          });
          this.getSubCategoiesByCategoryId(this.myCategoryFormGroup.get('cat-id')?.value).then(
            (list: []) => {
              // this.myCategoryFormGroup.setValue({
              //   'cat-sublist': list
              // })
            }
          ).catch(
            e => {
              alert(e)
            }
          )
          this.editOn();
          console.log('after getid')
        }
      }
    );

  }

  ngOnInit(): void {



  }
  goChild(myid: string) {
    //this.router.navigate(['/company-setup'],{queryParams : {id:myid,auth: this.pgService.getRandomAuth()},})
  }
  editOn() {
    if (this.edit) {
      $('#btn-category-save').show();
      $('#btn-category-delete').show();
      $('#btn-category-inactive').show();
    } else {
      $('#btn-category-save').hide();
      $('#btn-category-delete').hide();
      $('#btn-category-inactive').hide();
    }
  }


  _getCategoryData(): CategoryData {
    return {
      currentRow: -1,
      maxRowsPerPage: -1,
      rowNumber: -1,
      id: '-189909',
      code: '',
      name: '',
      status: -1,
      cdate: '',
      subCategories: []
    }
  }
  _getSubCategoryData(): SubCategoryData {
    return {
      currentRow: -1,
      maxRowsPerPage: -1,
      rowNumber: -1,
      id: '-1',
      code: '',
      name: '',
      status: -1,
      cdate: '',
      category: this._getCategoryData()
    }
  }
  getById(id: string) {
    return new Promise<CategoryData>((pro, reject) => {
      const url = this.pgService.config.url + "category-setup/getbyid/" + id;
      this.http.get<ViewResult<CategoryData>>(url, this.pgService.getOptions()).subscribe(
        (resp: ViewResult<CategoryData>) => {
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

    if (!this.myCategoryFormGroup.valid) {
      alert("Fill all fields")
      return;
    }
    let obj = this._getCategoryData();
    obj.id = this.myCategoryFormGroup.get('cat-id')?.value;
    obj.code = this.myCategoryFormGroup.get('cat-code')?.value;
    obj.name = this.myCategoryFormGroup.get('cat-name')?.value;
    obj.status = this.myCategoryFormGroup.get('cat-status')?.value ? 1 : 2;

    if (obj.id === '-1') {
      this.saveCategory(obj);
    } else {
      this.updateCategory(obj);
    }

  }
  updateCategory(obj: any) {
    // const url = this.pgService.config.url + 'warehouse-setup/update'
    // this.http.post<ViewResult<CategoryData>>(url, obj, this.pgService.getOptions()).subscribe(
    //   (resp: ViewResult<CategoryData>) => {
    //     if (resp.status == 200) {
    //       window.alert("Successfully updated");
    //       this.router.navigate(['/warehouse-list']);
    //     } else {
    //       alert("Client Error")
    //     }
    //   },
    //   (error) => {
    //     alert("Can't connect to server")
    //   }
    // )
  }

  saveCategory(obj: CategoryData) {

    const url = this.pgService.config.url + 'category-setup/save'
    this.http.post<ViewResult<CategoryData>>(url, obj, this.pgService.getOptions()).subscribe(
      (resp: ViewResult<CategoryData>) => {
        if (resp.status == 200) {
          alert("Successfully saved");
          // this.router.navigate(['/cate-list']);
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
  deleteCategory() {
    const url = this.pgService.config.url + 'category-setup/delete'
    this.http.post(url, {
      'id': this.myCategoryFormGroup.get('cat-id')?.value
    }, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          window.alert("Successfully deleted");
          this.router.navigate(['/category'])
        } else window.alert(resp.message)
      },
      (error) => {
        window.alert("Can't connect to server")
      }
    )
  }

  submitSubCategory() {
    if (this.mySubCategoryFormGroup.valid) {
      $('#sub-btn-save').prop('disabled', true);
      $('#sub-btn-modal-close').prop('disabled', true);
      let obj = this._getSubCategoryData();
      obj.id = this.mySubCategoryFormGroup.get('sub-id')?.value;
      obj.code = this.mySubCategoryFormGroup.get('sub-code')?.value;
      obj.name = this.mySubCategoryFormGroup.get('sub-name')?.value;
      obj.status = this.mySubCategoryFormGroup.get('sub-status')?.value ? 1 : 2;
      obj.category.id = this.mySubCategoryFormGroup.get('cat-id')?.value;
      if (obj.id == '-1') {
        this.saveSubCategory(obj).then(() => {      
          $('#sub-btn-save').prop('disabled', false);
          $('#sub-btn-modal-close').prop('disabled', false);
          $('#modal-subcategory-setup').appendTo("body").modal('hide');
    
          alert("Successfully saved");
        }).catch((e) => {
          $('#sub-btn-save').prop('disabled', false);
          $('#sub-btn-modal-close').prop('disabled', false);
          $('#modal-subcategory-setup').appendTo("body").modal('hide');
          alert(e)
        })
      } else {
        this.updateSubCategory(obj).then(() => {
          $('#sub-btn-save').prop('disabled', false);
          $('#sub-btn-modal-close').prop('disabled', false);
          $('#modal-subcategory-setup').appendTo("body").modal('hide');
          alert("Successfully saved");
        }).catch((e) => {
          $('#sub-btn-save').prop('disabled', false);
          $('#sub-btn-modal-close').prop('disabled', false);
          $('#modal-subcategory-setup').appendTo("body").modal('hide');
          alert(e)
        })
      }
    }


  }
  saveSubCategory(obj: SubCategoryData) {
    return new Promise<void>((resolve, reject) => {
      const url = this.pgService.config.url + 'subcategory-setup/save'
      this.http.post<ViewResult<CategoryData>>(url, obj, this.pgService.getOptions()).subscribe(
        (resp: ViewResult<CategoryData>) => {
          if (resp.status == 200) {

            let subList = this.myCategoryFormGroup.get('cat-sublist')?.value;
            subList.push(resp.data);

            resolve();
          }
          else {
            reject(resp.message);
          }
        },
        (error) => {
          reject("Can't connect to server")
        }
      )
    });
  }
  updateSubCategory(obj: SubCategoryData) {
    return new Promise<void>((resolve, reject) => {
      const url = this.pgService.config.url + 'subcategory-setup/update'
      this.http.post<ViewResult<SubCategoryData>>(url, obj, this.pgService.getOptions()).subscribe(
        (resp: ViewResult<SubCategoryData>) => {
          if (resp.status == 200) {
            let subList = this.myCategoryFormGroup.get('cat-sublist')?.value;
            for (let i = 0; i < subList.length; i++) {
              if (subList[i].id == obj.id) {
                subList[i] = resp.data;
                break;
              }
            }
            resolve()

          } else {
            reject("Client Error");
          }
        },
        (error) => {
          reject("Can't connect to server");
        }
      )
    })

  }
  deleteSubCategory() {

    // const url = this.pgService.config.url + 'warehouse-setup/delete'
    // this.http.post(url, {
    //   'id': this.myFormGroup.get('wh-id')?.value
    // }, this.pgService.getOptions()).subscribe(
    //   (resp: any) => {
    //     if (resp.status == 200) {
    //       window.alert("Successfully deleted");
    //       this.router.navigate(['/warehouse-list'])
    //     } else window.alert(resp.message)
    //   },
    //   (error) => {
    //     window.alert("Can't connect to server")
    //   }
    // )
  }
  addNewSubCategory() {
    this.mySubCategoryFormGroup = new FormGroup({
      'sub-id': new FormControl('-1'),
      'sub-code': new FormControl('', Validators.required),
      'sub-name': new FormControl('', Validators.required),
      'sub-status': new FormControl(true),
      'sub-cdate': new FormControl(this.pgService.getDateString(new Date())),
      'cat-id': new FormControl(this.myCategoryFormGroup.get('cat-id')?.value),
    });
    $('#modal-subcategory-setup').appendTo("body").modal('show');
  }
  editSubCategory(e: SubCategoryData) {
    this.mySubCategoryFormGroup = new FormGroup({
      'sub-id': new FormControl(e.id),
      'sub-code': new FormControl(e.code, Validators.required),
      'sub-name': new FormControl(e.name, Validators.required),
      'sub-status': new FormControl(e.status),
      'sub-cdate': new FormControl(e.cdate),
      'cat-id': new FormControl(e.category.id),
    });
    $('#modal-subcategory-setup').appendTo("body").modal('show');
  }

  getSubCategoiesByCategoryId(catid: string) {
    return new Promise<[]>((resolve, reject) => {
      const url = this.pgService.config.url + 'subcategory-setup/getall';
      let cri = this._getSubCategoryData();
      cri.category.id = catid;
      this.http.post<ViewResult<SubCategoryData>>(url, cri, this.pgService.getOptions()).subscribe(
        (d: ViewResult<SubCategoryData>) => {
          if (d.status == 200) {
            return resolve(d.list);
          } else {
            reject(d.message);
          }
        },
        error => {
          reject("Can't connect to server");
        }
      )
    })
  }


}
