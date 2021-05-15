import { animate, state, style, transition, trigger } from '@angular/animations';
import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { PageServiceService } from '../page-service.service';
declare var $ : any;
@Component({
  selector: 'app-brand-list',
  templateUrl: './brand-list.component.html',
  styleUrls: ['./brand-list.component.css'],
  animations: [
  trigger(
    'inOutAnimation',
    [
      transition(
        ':enter',
        [
          style({ opacity: 0 }),
          animate('0.5s ease-out',
            style({ opacity: 1 }))
        ]
      ),
      transition(
        ':leave',
        [
          style({ height: 300, opacity: 1 }),
          animate('0.2s ease-in',
            style({ height: 0, opacity: 0 }))
        ]
      )
    ],

  ),
  trigger('openClose', [
    // ...
    state('open', style({
      height: '200px',
      opacity: 1,

    })),
    state('closed', style({
      height: '100px',
      opacity: 0.5,

    })),
    transition('open => closed', [
      animate('1s')
    ]),
    transition('closed => open', [
      animate('0.5s')
    ]),
  ]),
]
},

)
export class BrandListComponent implements OnInit, AfterViewInit {
  pg_config = {
    id: "pc-b",
    itemsPerPage: this.pgService.pg_config.itemPerPage,
    currentPage: 1,
    totalItems: 0
  };
  _brandList: any = [];

  brandForm: FormGroup = new FormGroup({
    "b-name": new FormControl(''),
    "b-code": new FormControl(''),
    "b-status": new FormControl(-1),
    "bo-name": new FormControl(''),
    "bo-code": new FormControl(''),
    "bo-status": new FormControl(-1)
  })
  constructor(private pgService: PageServiceService,
    private http: HttpClient,
    private route: Router) { }

  ngAfterViewInit(): void {
    $('#myspinner').show();
    $('.table').hide();
    this.pgService.getBrandList(this.mapFormGroupToObject(0)).then((resp: any) => {
      this._brandList = resp.list;
      this.pg_config.totalItems = resp.totalItem;
      $('#myspinner').hide();
      $('.table').show();
    }).catch((e) => {
      alert(e)
      this._brandList = [];
      $('#myspinner').hide();
      $('.table').show();
    })
  }

  ngOnInit(): void {

  }
  showDetail(myid: string) {
    this.route.navigate(['/brand'], { queryParams: { id: myid } });
  }
  routeNew() {
    this.route.navigate(['/brand'], { queryParams: { id: '-1' } });
  }
  _pageChanged(e: any) {
    this.pg_config.currentPage = e;
    let currentIndex = (this.pg_config.currentPage - 1) * this.pg_config.itemsPerPage;

    this.pgService.getBrandList(this.mapFormGroupToObject(currentIndex)).then((d: any) => {
      this._brandList = d;
      this.pg_config.totalItems = d.totalItem;
    }).catch(e => {
      this._brandList = [];
    })
  }
  mapFormGroupToObject(index: any) {
    return {
      "code": this.brandForm.get('b-code')?.value,
      "name": this.brandForm.get("b-name")?.value,
      "brandOwner" :{
        "code":this.brandForm.get('bo-code')?.value,
        "name":this.brandForm.get("bo-name")?.value,
        "status":parseInt(this.brandForm.get("bo-status")?.value)
      },
      "status": parseInt(this.brandForm.get("b-status")?.value),
      "currentRow": index,
      "maxRowsPerPage": this.pg_config.itemsPerPage
    }
  }

  search() {
    this.pgService.getBrandList(this.mapFormGroupToObject(0)).then((d: any) => {
      this._brandList = d.list;
      this.pg_config.totalItems = d.totalItem;
    }).catch(e => {
      this._brandList = [];
    })
  }
  searchUndo() {
    this.brandForm.setValue({
      "b-name": '',
      "b-code": '',
      "b-status": -1,
      "bo-name": '',
      "bo-code": '',
      "bo-status": -1
    })
  }



}
