import { HttpClient } from '@angular/common/http';
import { AfterViewChecked, AfterViewInit, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Route, Router } from '@angular/router';
import { PageServiceService } from '../page-service.service';
declare var $: any;
@Component({
  selector: 'app-brand-owner-list',
  templateUrl: './brand-owner-list.component.html',
  styleUrls: ['./brand-owner-list.component.css']
})
export class BrandOwnerListComponent implements OnInit, AfterViewInit {
  pg_config = {
    id: "pc-bo",
    itemsPerPage: this.pgService.pg_config.itemPerPage,
    currentPage: 1,
    totalItems: 0
  };
  _bolist: any = [];
  boForm = new FormGroup({
    "bo-name": new FormControl(''),
    "bo-status": new FormControl(-1)
  })


  constructor(private pgService: PageServiceService,
    private route: Router,
    private http: HttpClient) {
  }



  ngOnInit(): void {

  }
  ngAfterViewInit(): void {
    this.pgService.getBrandOwners(this.mapFormGroupToObject(0)).then( (d:any) => {
      this._bolist = d.list;
      this.pg_config.totalItems = d.totalItem;
    }).catch(e => {
      alert("Can't connect to server!")
      this._bolist = [];
    });
    let collapse = document.getElementById("cri-box") as HTMLElement;
    collapse.addEventListener('hidden.bs.collapse', () => {

    })
  }
  ngAfterViewChecked(): void {

  }
  showDetail(boid: string) {
    this.route.navigate(['/brand-owner'], { queryParams: { id: boid } });
  }
  routeNew() {
    this.route.navigate(['/brand-owner'], { queryParams: { id: '-1' } });
  }
  _pageChanged(e: any) {
    this.pg_config.currentPage = e;
    let currentIndex = (this.pg_config.currentPage - 1) * this.pg_config.itemsPerPage;
    this.pgService.getBrandOwners(this.mapFormGroupToObject(currentIndex)).then((d:any) => {
      this._bolist = d.list;
      this.pg_config.totalItems = d.totalItem;
      console.log(this._bolist)
    }).catch(e => {
      this._bolist = [];
    })
  }
  mapFormGroupToObject(index:number){
    return {
      "name": this.boForm.get("bo-name")?.value,
      "status": parseInt(this.boForm.get("bo-status")?.value),
      "currentRow": index,
      "maxRowsPerPage": this.pg_config.itemsPerPage
    }
  }
 
  search() {
    let obj = {
      "t2": this.boForm.get("bo-name")?.value,
      "s1": parseInt(this.boForm.get("bo-status")?.value)
    }
    this.pgService.getBrandOwners(this.mapFormGroupToObject(0)).then((d:any) => {
      this._bolist = d.list
      this.pg_config.totalItems = d.totalItem;
        }).catch(e => {
      this._bolist = [];
    })
  }
  searchUndo() {
    this.boForm.setValue({
      "bo-name": "",
      "bo-status": -1
    })
  }

}
