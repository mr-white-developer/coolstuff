import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { UomData } from '../model';
import { PageServiceService } from '../page-service.service';
declare var $ : any;
@Component({
  selector: 'app-stock-unit-list',
  templateUrl: './stock-unit-list.component.html',
  styleUrls: ['./stock-unit-list.component.css']
})
export class StockUnitListComponent implements OnInit,AfterViewInit {

  
  pg_config = {
    id: "pc-b",
    itemsPerPage: this.pgService.pg_config.itemPerPage,
    currentPage: 1,
    totalItems: 0
  };
  _list: any = [];

  myFormGroup: FormGroup = new FormGroup({
    'uom-id':new FormControl('-1'),
    'uom-code': new FormControl(''),
    'uom-name': new FormControl(''),
    'uom-status': new FormControl(-1)

  });
  constructor(private pgService: PageServiceService,
    private http: HttpClient,
    private route: Router) { }

  ngAfterViewInit(): void {
    $('#myspinner').show();
    $('.table').hide();
    this.pgService.getUomList(this.mapFormGroupToObject(0)).then((resp: any) => {
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
    this.route.navigate(['/uom-setup'], { queryParams: { id: myid } });
  }
  routeNew() {
    this.route.navigate(['/uom-setup'], { queryParams: { id: '-1' } });
  }
  _pageChanged(e: any) {
    this.pg_config.currentPage = e;
    let currentIndex = (this.pg_config.currentPage - 1) * this.pg_config.itemsPerPage;

    this.pgService.getUomList(this.mapFormGroupToObject(currentIndex)).then((d: any) => {
      this._list = d;
      this.pg_config.totalItems = d.totalItem;
    }).catch(e => {
      this._list = [];
    })
  }
  mapFormGroupToObject(index: any): UomData {
    return {
      currentRow: index,
      maxRowsPerPage: this.pg_config.itemsPerPage,
      rowNumber: 0,
      id: '-1',
      code: this.myFormGroup.get('uom-code')?.value,
      name: this.myFormGroup.get('uom-name')?.value,
      status: parseInt(this.myFormGroup.get('uom-status')?.value),
      cdate: ''
    }
  }

  search() {
    this.pgService.getUomList(this.mapFormGroupToObject(0)).then((d: any) => {
      this._list = d.list;
      this.pg_config.totalItems = d.totalItem;
    }).catch(e => {
      this._list = [];
    })
  }
  searchUndo() {
    this.myFormGroup.setValue({
      'uom-code': '',
      'uom-name': '',
      'uom-status': -1,
    })
  }

}
