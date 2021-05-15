import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { PackSizeData } from '../model';
import { PageServiceService } from '../page-service.service';
declare var $ : any;
@Component({
  selector: 'app-packsize-list',
  templateUrl: './packsize-list.component.html',
  styleUrls: ['./packsize-list.component.css']
})
export class PacksizeListComponent implements OnInit {
  pg_config = {
    id: "pc-b",
    itemsPerPage: this.pgService.pg_config.itemPerPage,
    currentPage: 1,
    totalItems: 0
  };
  _list: any = [];

  myFormGroup: FormGroup = new FormGroup({
    'packsize-id':new FormControl('-1'),
    'packsize-code': new FormControl(''),
    'packsize-name': new FormControl(''),
    'packsize-status': new FormControl(-1)

  });
  constructor(private pgService: PageServiceService,
    private http: HttpClient,
    private route: Router) { }

  ngAfterViewInit(): void {
    $('#myspinner').show();
    $('.table').hide();
    this.pgService.getPackSizeList(this.mapFormGroupToObject(0)).then((resp: any) => {
      this._list = resp.list;
      this.pg_config.totalItems = resp.totalItem;
      $('#myspinner').hide();
      $('.table').show();
    }).catch((e) => {
      alert(e);
      this._list = [];
      $('#myspinner').hide();
      $('.table').show();
    })
  }

  ngOnInit(): void {

  }
  showDetail(myid: string) {
    this.route.navigate(['/packsize-setup'], { queryParams: { id: myid } });
  }
  routeNew() {
    this.route.navigate(['/packsize-setup'], { queryParams: { id: '-1' } });
  }
  _pageChanged(e: any) {
    this.pg_config.currentPage = e;
    let currentIndex = (this.pg_config.currentPage - 1) * this.pg_config.itemsPerPage;

    this.pgService.getPackSizeList(this.mapFormGroupToObject(currentIndex)).then((d: any) => {
      this._list = d;
      this.pg_config.totalItems = d.totalItem;
    }).catch(e => {
      this._list = [];
    })
  }

  mapFormGroupToObject(index: any): PackSizeData{
    return {
      currentRow: index,
      maxRowsPerPage: this.pg_config.itemsPerPage,
      rowNumber: 0,
      id: '-1',
      code: this.myFormGroup.get('packsize-code')?.value,
      name: this.myFormGroup.get('packsize-name')?.value,
      status: parseInt(this.myFormGroup.get('packsize-status')?.value),
      cdate: ''
    }
  }

  search() {
    this.pgService.getPackSizeList(this.mapFormGroupToObject(0)).then((d: any) => {
      this._list = d.list;
      this.pg_config.totalItems = d.totalItem;
    }).catch(e => {
      this._list = [];
    })
  }

  searchUndo() {
    this.myFormGroup.setValue({
      'packsize-code': '',
      'packsize-name': '',
      'packsize-status': -1,
    })
  }


}
