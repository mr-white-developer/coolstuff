import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { CompanyData } from '../model';
import { PageServiceService } from '../page-service.service';
declare var $: any
@Component({
  selector: 'app-company-list',
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.css']
})
export class CompanyListComponent implements OnInit, AfterViewInit {


  pg_config = {
    id: "pc-b",
    itemsPerPage: this.pgService.pg_config.itemPerPage,
    currentPage: 1,
    totalItems: 0
  };
  _list: any = [];

  myFormGroup: FormGroup = new FormGroup({
    'company-id': new FormControl('-1'),
    'company-code': new FormControl(''),
    'company-name': new FormControl(''),
    'company-status': new FormControl(-1),
    'company-pname': new FormControl(''),
    'company-email': new FormControl(''),
    'company-phone': new FormControl('')

  });
  constructor(private pgService: PageServiceService,
    private http: HttpClient,
    private route: Router,
    private myApp: AppComponent) { }

  ngAfterViewInit(): void {
    $('#myspinner').show();
    $('.table').hide();
    this.pgService.getCompanyList(this.mapFormGroupToObject(0)).then((resp: any) => {
      this._list = resp.list;
      this.pg_config.totalItems = resp.totalItem;
      $('#myspinner').hide();
      $('.table').show();
    }).catch((e) => {
      alert(e)
      this._list = [];
      $('#myspinner').hide();
      $('.table').show();
    })
  }

  ngOnInit(): void {

  }
  showDetail(myid: string) {
    this.route.navigate(['/company-setup'], { queryParams: { id: myid } });
  }
  routeNew() {
    this.route.navigate(['/company-setup'], { queryParams: { id: '-1' } });
  }
  _pageChanged(e: any) {
    this.pg_config.currentPage = e;
    let currentIndex = (this.pg_config.currentPage - 1) * this.pg_config.itemsPerPage;

    this.pgService.getCompanyList(this.mapFormGroupToObject(currentIndex)).then((d: any) => {
      this._list = d;
      this.pg_config.totalItems = d.totalItem;
    }).catch(e => {
      this._list = [];
    })
  }
  mapFormGroupToObject(index: any): CompanyData {
    return {
      currentRow: index,
      maxRowsPerPage: this.pg_config.itemsPerPage,
      rowNumber: 0,
      id: this.myFormGroup.get('company-id')?.value,
      code: this.myFormGroup.get('company-code')?.value,
      name: this.myFormGroup.get('company-name')?.value,
      status: parseInt(this.myFormGroup.get('company-status')?.value),
      cdate: '',
      mdate: '',
      ownerName: this.myFormGroup.get('company-pname')?.value,
      email: this.myFormGroup.get('company-email')?.value,
      phone: this.myFormGroup.get('company-phone')?.value,
      warehouses:[]
    }
  }

  search() {
    $('#myspinner').show();
    $('.table').hide();
    this.pgService.getCompanyList(this.mapFormGroupToObject(0)).then((d: any) => {
      this._list = d.list;
      this.pg_config.totalItems = d.totalItem;
      $('#myspinner').hide();
      $('.table').show();
    }).catch(e => {
      this._list = [];
      $('#myspinner').hide();
      $('.table').show();
    })
  }
  searchUndo() {
    this.myFormGroup.setValue({
      'company-id': '-1',
      'company-code': '',
      'company-name': '',
      'company-status': -1,
      'company-pname': '',
      'company-email': '',
      'company-phone': ''
    })
  }

}
