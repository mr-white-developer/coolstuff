import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component,  OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PageServiceService } from './page-service.service';

declare var $: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterViewInit {

  title = 'jupitor-os';

  category_list = [
    {
      title: 'Fabrication Services rtrtertertert', code: '', id: 'd1', child: [
        { title: 'Consumer Electronics gdfgdsfgdsfg', id: 'd1-c1' },
        { title: 'Apparel', id: 'd1-c2' },
        { title: 'Sports & Entertainment', id: 'd1-c3' },

      ]
    },
    { title: 'Consumer Electronics', code: '', id: 'd2', child: [{ title: 'child 2', id: '' }] }


  ]
  selected_category = this.category_list[0];
  _offcanvas = {
    _menus: this.pgService.admin_menus
  }

  constructor(
    private route: Router,
    private http: HttpClient,
    private pgService: PageServiceService,
  ) {
    console.log("main constructor")
    //this.readConfig();
  }
  ngAfterViewInit(): void {
    //this.pgService.alertHtmlElement = document.getElementById('#alert-box') as HTMLElement;
  }

  async ngOnInit() {
    await this.readConfig();
  }
  /*read config*/
  readConfig() {
    return new Promise<void>((promise, reject) => {
      this.http.get('assets/config/config.json').subscribe(
        (data: any) => {
          this.pgService.config.url = data.url;
          this.pgService.config.name = data.name;
          promise();
        }, (error) => {
          reject();
        }
      );
    })
  }
  goRoute(link: string) {
    this.route.navigate([link]);

  }
  showChild(category: any) {
    this.selected_category = category;
  }
  hide() {
    $('#alert-box').removeClass('show')
  }
  show() {
    $('#alert-box').addClass('show')
  }

}













