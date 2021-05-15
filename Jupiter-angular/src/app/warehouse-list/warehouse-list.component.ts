import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { WarehouseData } from '../model';
import { PageServiceService } from '../page-service.service';
declare var $: any;
@Component({
  selector: 'app-warehouse-list',
  templateUrl: './warehouse-list.component.html',
  styleUrls: ['./warehouse-list.component.css']
})
export class WarehouseListComponent implements OnInit, AfterViewInit {

  pg_config = {
    id: "pc-b",
    itemsPerPage: this.pgService.pg_config.itemPerPage,
    currentPage: 1,
    totalItems: 0
  };
  _list: any = [];

  myFormGroup: FormGroup = new FormGroup({
    'wh-id':new FormControl('-1'),
    'wh-code': new FormControl(''),
    'wh-name': new FormControl(''),
    'wh-status': new FormControl(-1),
    'cm-id': new FormControl('-1'),
    'cm-name':new FormControl('')

  });
  constructor(private pgService: PageServiceService,
    private http: HttpClient,
    private route: Router) { }

  ngAfterViewInit(): void {
    $('#myspinner').show();
    $('.table').hide();
    this.pgService.getWarehouseList(this.mapFormGroupToObject(0)).then((resp: any) => {
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
    this.route.navigate(['/warehouse-setup'], { queryParams: { id: myid } });
  }
  routeNew() {
    this.route.navigate(['/warehouse-setup'], { queryParams: { id: '-1' } });
  }
  _pageChanged(e: any) {
    this.pg_config.currentPage = e;
    let currentIndex = (this.pg_config.currentPage - 1) * this.pg_config.itemsPerPage;

    this.pgService.getWarehouseList(this.mapFormGroupToObject(currentIndex)).then((d: any) => {
      this._list = d;
      this.pg_config.totalItems = d.totalItem;
    }).catch(e => {
      this._list = [];
    })
  }
  mapFormGroupToObject(index: any): WarehouseData {
    return {
      currentRow: index,
      maxRowsPerPage: this.pg_config.itemsPerPage,
      rowNumber: 0,
      id: '-1',
      code: this.myFormGroup.get('wh-code')?.value,
      name: this.myFormGroup.get('wh-name')?.value,
      status: parseInt(this.myFormGroup.get('wh-status')?.value),
      cdate: '',
      company: {
        currentRow: -1,
        maxRowsPerPage: -1,
        rowNumber: 0,
        id: this.myFormGroup.get('cm-id')?.value,
        code: this.myFormGroup.get('cm-name')?.value,
        name: '',
        status: -1,
        cdate: '',
        mdate:'',
        ownerName: '',
        email: '',
        phone: '',
        warehouses:[]
      }
    }
  }

  search() {
    this.pgService.getWarehouseList(this.mapFormGroupToObject(0)).then((d: any) => {
      this._list = d.list;
      this.pg_config.totalItems = d.totalItem;
    }).catch(e => {
      this._list = [];
    })
  }
  searchUndo() {
    this.myFormGroup.setValue({
      'wh-code': '',
      'wh-name': '',
      'wh-status': -1,
      'cm-id': '',
      'cm-name':'',
    })
  }

}
