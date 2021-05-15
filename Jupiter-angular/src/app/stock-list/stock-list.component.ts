import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { BrandData, BrandOwnerData, CategoryData, PackSizeData, PriceTypeData, StockData, SubCategoryData } from '../model';
import { PageServiceService } from '../page-service.service';
declare var $: any;
@Component({
  selector: 'app-stock-list',
  templateUrl: './stock-list.component.html',
  styleUrls: ['./stock-list.component.css']
})
export class StockListComponent implements OnInit, AfterViewInit {

  pg_config = {
    id: "pc-b",
    itemsPerPage: this.pgService.pg_config.itemPerPage,
    currentPage: 1,
    totalItems: 0
  };
  _list: any = [];

  myFormGroup: FormGroup = new FormGroup({
    'stock-id': new FormControl('-1'),
    'stock-code': new FormControl(''),
    'stock-name': new FormControl(''),
    'stock-status': new FormControl(-1)

  });
  constructor(private pgService: PageServiceService,
    private http: HttpClient,
    private route: Router) { }

  ngAfterViewInit(): void {
    $('#myspinner').show();
    $('.table').hide();
    this.pgService.getStockList(this.mapFormGroupToObject(0)).then((resp: any) => {
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
    this.route.navigate(['/stock-setup'], { queryParams: { id: myid } });
  }
  routeNew() {
    this.route.navigate(['/stock-setup'], { queryParams: { id: '-1' } });
  }
  _pageChanged(e: any) {
    this.pg_config.currentPage = e;
    let currentIndex = (this.pg_config.currentPage - 1) * this.pg_config.itemsPerPage;

    this.pgService.getStockList(this.mapFormGroupToObject(currentIndex)).then((d: any) => {
      this._list = d;
      this.pg_config.totalItems = d.totalItem;
    }).catch(e => {
      this._list = [];
    })
  }
  mapFormGroupToObject(index: any): StockData {
    return {
      currentRow: index,
      maxRowsPerPage: this.pg_config.itemsPerPage,
      rowNumber: 0,
      id: '-1',
      code: this.myFormGroup.get('stock-code')?.value,
      name: this.myFormGroup.get('stock-name')?.value,
      status: parseInt(this.myFormGroup.get('stock-status')?.value),
      cdate: '',
      category: this._getCategoryData(),
      subCategory: this._getSubCategoryData(),
      brand: this._getBrandModel(),
      packType: this._getPackTypeModel(),
      packSize: this._getPackSizeModel(),
      stockHoldings: [],
      uomStocks: []
    }
  }
  _getCategoryData(): CategoryData {
    return {
      currentRow: -1,
      maxRowsPerPage: -1,
      rowNumber: -1,
      id: '-1',
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
  _getBrandModel(): BrandData {
    return {
      currentRow: -1,
      maxRowsPerPage: -1,
      rowNumber: 0,
      id: '-1',
      code: '',
      name: '',
      status: -1,
      cdate: '',
      mdate: '',
      brandOwner: this._getBrandOwnerModel()
    }
  }
  _getBrandOwnerModel(): BrandOwnerData {
    return {
      currentRow: -1,
      maxRowsPerPage: -1,
      rowNumber: 0,
      id: '-1',
      code: '',
      name: '',
      status: -1,
      cdate: '',
      mdate: ''
    }
  }
  _getPackTypeModel(): PriceTypeData {
    return {
      currentRow: -1,
      maxRowsPerPage: -1,
      rowNumber: 0,
      id: '-1',
      code: '',
      name: '',
      status: -1,
      cdate: '',
    }
  }
  _getPackSizeModel(): PackSizeData {
    return {
      currentRow: -1,
      maxRowsPerPage: -1,
      rowNumber: 0,
      id: '-1',
      code: '',
      name: '',
      status: -1,
      cdate: '',
    }
  }
  search() {
    this.pgService.getStockList(this.mapFormGroupToObject(0)).then((d: any) => {
      this._list = d.list;
      this.pg_config.totalItems = d.totalItem;
    }).catch(e => {
      this._list = [];
    })
  }
  searchUndo() {
    this.myFormGroup.setValue({
      'stock-id': '-1',
    'stock-code':'',
    'stock-name': '',
    'stock-status':-1
    })
  }

}
