import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AfterViewInit, Component, Injector, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NotifierComponent } from '../notifier/notifier.component';
import { PageServiceService } from '../page-service.service';
import { PopupService } from '../popup.service';
import { PopupComponent } from '../popup/popup.component';
declare var $: any;
@Component({
  selector: 'app-entry',
  templateUrl: './entry.component.html',
  styleUrls: ['./entry.component.css']
})
export class EntryComponent implements OnInit, AfterViewInit {
  myForm: FormGroup = new FormGroup({

    'brand-owner': new FormControl('', Validators.required),
    'brandowner-list': new FormControl([]),
  });

  event: any = null;
  croppedImage: any;
  files:any;
  constructor(private load: PopupService,
    injector: Injector,
    private http: HttpClient,
    private service: PageServiceService
  ) { }
  ngAfterViewInit(): void {
  }

  ngOnInit(): void {

  }
  popup() {
    this.load.showAlert("hi");
  }
  async loading() {
    await this.load.showLoading("hi").then((e: any) => {
      e.present();
      e.dismiss();
    });
    console.log("im")

  }
  fileChangeEvent(e: any) {
    const input = e.srcElement;
    this.files = input.files;
  }
  finishedCropper(e: any) {
    this.croppedImage = e;
    $('#myCrop').hide();
  }
  getimage() {
    const url = this.service.config.url + "upload/getimage"
    // var nuroImage = new Image;
    // var request = new XMLHttpRequest();
    // request.responseType = "blob";
    // request.onload = function () {
    //   nuroImage.src = URL.createObjectURL(this.response);
      
    // }
    // request.open("GET",url );
    // request.send();

    
    let caller = this.http.get(url, this.service.getOptions()).subscribe(
      (d: any) => {
        this.croppedImage = d;
      }
    )
  }
  postMultiPath(){
    let form = new FormData();
    for(let f of this.files){
      console.log(f)
      form.append('uploadedfile', f, f.type);
    }
    form.append('info', "hello");
    const url = this.service.config.url + "upload/fileupload";
    this.http.post(url,form,{
      headers: new HttpHeaders({
        'api-token': '',
      }),
    }).subscribe(
      (data:any)=>{
        alert(data)
      }
    )
  }
}
