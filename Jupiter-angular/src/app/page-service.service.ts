import { DatePipe } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { promise } from 'selenium-webdriver';
import { AppComponent } from './app.component';
import { BrandData, CategoryData, CompanyData, CurrencyData, PackSizeData, PackTypeData, PriceTypeData, StockData, sysconfig, UomData, ViewResult, WarehouseData } from './model';

declare var $: any;
@Injectable({
  providedIn: 'root'
})
export class PageServiceService {
  alertHtmlElement: any;

  config: sysconfig = {
    "url": "http://localhost:8087/os/",
    "img_url" : "http://localhost:8087/jpimageloader/",
    "name": "",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJDT09MU1RVRkZfU09GVCIsImlkMSI6IiIsImlkMiI6IiIsImlkMyI6dHJ1ZSwiaWQ0IjoiMjAyMS0wNS0zMSAyMjo1NjoyNyIsImlhdCI6MTYyMjQ3ODM4NywiZXhwIjoxNjIyNDc5Mjg3LCJqdGkiOiJkYWY5ZmU4YS0yNjY5LTQyMjUtODVmYy1hZTAzNTgyMTFjZTAifQ.iNI3lbZFpHUMbO18DbPIOAACc9gBo7A4TnXAHcfB820"
  }
  loginUser = {
    'company_id': ''
  }
  pg_config = {
    itemPerPage: 10
  }
  DATE_FORMAT = "yyyy-MM-dd";
  admin_menus = [
    { title: 'Brand', route: '/brand-list', shown: true },
    { title: 'Brand Owner', route: '/brand-owner-list', shown: true },
    { title: 'WareHouse', route: '/warehouse-list', shown: true },
    { title: 'Company', route: '/company-list', shown: true },
    { title: 'Category', route: '/category', shown: true },
    { title: 'Pack Types', route: '/packtype-list', shown: true },
    { title: 'Pack Size', route: '/packsize-list', shown: true },
    { title: 'Stock Units', route: '/uom-list', shown: true },
    { title: 'Price Type', route: '/price-type-list', shown: true },
    { title: 'Stock', route: '/stock-list', shown: true }
  ].sort((a, b) => (a.title > b.title) ? 1 : -1);
  authen: number = 0;
  ENUM = {
    STATUS :  {
      Default: { code: 0, desc: "Default" },
      True: { code: 1, desc: "True" },
      False: { code: 2, desc: "False" }
    },
    PRICE_TYPE:{
      ByRatio : {code:1,desc:"By Ratio"},
      BySpecific : {code:2,desc:"By Specific"}
    }
  }

  constructor(
    private datepie: DatePipe,
    private http: HttpClient,
  ) {
    console.log("service");

  }
  async getConfig(): Promise<sysconfig> {
    await new Promise<void>((promise, reject) => {
      if (this.config == undefined) {
        this.http.get<sysconfig>('assets/config/config.json').subscribe(
          (data: sysconfig) => {
            this.config = data;
            promise();
          }, (error) => {
            promise();
          }
        );
      }
    });
    return this.config;
  }
  getOptions() {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'api-token': '',
      }),
    };
    return options;
  }
  getOptionsMultiPart(){
    const options = {
      headers: new HttpHeaders({
        'api-token': '',
      }),
    };
    return options;
  }

  getProgressOptions() {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'api-token': '',
      }),
      withCredentials: true,
      observe: 'events',
      reportProgress: true,
      responseType: 'json'
    };
    return options;
  }

  getDateString(date: Date) {
    return this.datepie.transform(date, this.DATE_FORMAT);
  }
  dateFormatDbToUi(dbDate: string) {
    //return this.datepie.
  }
  async getBrandList(obj: any) {
    return new Promise<ViewResult<BrandData>>((resolve, reject) => {
      const url = this.config.url + 'brand-setup/get-brands'
      this.http.post<ViewResult<BrandData>>(url, obj, this.getOptions()).subscribe(
        (resp: any) => {
          if (resp.status == 200) {
            resolve(resp);
          } else {
            reject("Client Error")
          }
        },
        e => {
          reject("Can't connect to server")
        }
      )
    })
  }
  getBrandOwners(obj: any) {
    return new Promise((promise, reject) => {
      const url = this.config.url + "brandowner-setup/get-brandowners";
      this.http.post(url, obj
        , this.getOptions()).subscribe(
          (resp: any) => {
            if (resp.status == 200) {
              promise(resp);
            } else {
              reject("Server Error")
            }
          },
          error => {
            reject("Can't connect to server");
          }
        )
    })
  }
  getWarehouseList(obj: any) {
    return new Promise<ViewResult<WarehouseData>>((promise, reject) => {
      const url = this.config.url + "warehouse-setup/getall";
      this.http.post<ViewResult<WarehouseData>>(url, obj
        , this.getOptions()).subscribe(
          (resp: ViewResult<WarehouseData>) => {
            if (resp.status == 200) {
              promise(resp);
            } else {
              reject("Client Error")
            }
          },
          error => {
            reject("Can't connect to server");
          }
        )
    })
  }
  getCompanyList(obj: any) {
    return new Promise<ViewResult<CompanyData>>((promise, reject) => {
      const url = this.config.url + "company-setup/getall";
      this.http.post<ViewResult<CompanyData>>(url, obj
        , this.getOptions()).subscribe(
          (resp: ViewResult<CompanyData>) => {
            if (resp.status == 200) {
              promise(resp);
            } else {
              reject("Client Error")
            }
          },
          error => {
            reject("Can't connect to server");
          }
        )
    })
  }
  getCategoryList(obj: any) {
    return new Promise<ViewResult<CategoryData>>((promise, reject) => {
      const url = this.config.url + "category-setup/getall";
      this.http.post<ViewResult<CategoryData>>(url, obj
        , this.getOptions()).subscribe(
          (resp: ViewResult<CategoryData>) => {
            if (resp.status == 200) {
              promise(resp);
            } else {
              reject("Client Error")
            }
          },
          error => {
            reject("Can't connect to server");
          }
        )
    })
  }
  getPackTypeList(obj: any) {
    return new Promise<ViewResult<PackTypeData>>((promise, reject) => {
      const url = this.config.url + "packtype-setup/getall";
      this.http.post<ViewResult<PackTypeData>>(url, obj
        , this.getOptions()).subscribe(
          (resp: ViewResult<PackTypeData>) => {
            if (resp.status == 200) {
              promise(resp);
            } else {
              reject("Client Error")
            }
          },
          error => {
            reject("Can't connect to server");
          }
        )
    })
  }
  getPackSizeList(obj: any) {
    return new Promise<ViewResult<PackSizeData>>((promise, reject) => {
      const url = this.config.url + "packsize-setup/getall";
      this.http.post<ViewResult<PackSizeData>>(url, obj
        , this.getOptions()).subscribe(
          (resp: ViewResult<PackSizeData>) => {
            if (resp.status == 200) {
              promise(resp);
            } else {
              reject("Client Error");
            }
          },
          error => {
            reject("Can't connect to server");
          }
        )
    })
  }
  getUomList(obj: any) {
    return new Promise<ViewResult<UomData>>((promise, reject) => {
      const url = this.config.url + "uom-setup/getall";
      this.http.post<ViewResult<UomData>>(url, obj
        , this.getOptions()).subscribe(
          (resp: ViewResult<UomData>) => {
            if (resp.status == 200) {
              promise(resp);
            } else {
              reject("Client Error");
            }
          },
          error => {
            reject("Can't connect to server");
          }
        )
    })
  }
  getPriceTypeList(obj: any) {
    return new Promise<ViewResult<PriceTypeData>>((promise, reject) => {
      const url = this.config.url + "pricetype-setup/getall";
      this.http.post<ViewResult<PriceTypeData>>(url, obj
        , this.getOptions()).subscribe(
          (resp: ViewResult<PriceTypeData>) => {
            if (resp.status == 200) {
              promise(resp);
            } else {
              reject("Client Error");
            }
          },
          error => {
            reject("Can't connect to server");
          }
        )
    })
  }
  getCurrencyList(obj: any) {
    return new Promise<ViewResult<CurrencyData>>((promise, reject) => {
      const url = this.config.url + "currency-setup/getall";
      this.http.post<ViewResult<CurrencyData>>(url, obj
        , this.getOptions()).subscribe(
          (resp: ViewResult<CurrencyData>) => {
            if (resp.status == 200) {
              promise(resp);
            } else {
              reject("Client Error");
            }
          },
          error => {
            reject("Can't connect to server");
          }
        )
    })
  }
  getStockList(obj: any) {
    return new Promise<ViewResult<StockData>>((promise, reject) => {
      const url = this.config.url + "stock-setup/getall";
      this.http.post<ViewResult<StockData>>(url, obj
        , this.getOptions()).subscribe(
          (resp: ViewResult<StockData>) => {
            if (resp.status == 200) {
              promise(resp);
            } else {
              reject("Client Error");
            }
          },
          error => {
            reject("Can't connect to server");
          }
        )
    })
  }

  getRandomAuth() {
    let d = new Date();
    let n = d.getMilliseconds();
    this.authen = n;
    return n;
  }
  dataURLtoFile(dataurl:any, filename:any) {
 
    var arr = dataurl.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), 
        n = bstr.length, 
        u8arr = new Uint8Array(n);
        
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    
    return new File([u8arr], filename, {type:mime});
}

}
