import { HttpClient } from '@angular/common/http';
import { syntaxError } from '@angular/compiler';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { BrandData, BrandOwnerData, CategoryData, CompanyData, CurrencyData, PackSizeData, PriceTypeData, PricingData, StockData, stockHoldingData, StockSetupData, StockUomData, SubCategoryData, UomData, ViewResult, WarehouseData } from '../model';
import { PageServiceService } from '../page-service.service';
import { PopupService } from '../popup.service';
declare var $: any;

@Component({
  selector: 'app-stock-setup',
  templateUrl: './stock-setup.component.html',
  styleUrls: ['./stock-setup.component.css']
})
export class StockSetupComponent implements OnInit, AfterViewInit {

  edit: boolean = false;

  roles = {
    button: {
      save: true,
      delete: true,
      inactive: true,
      edit: true
    }
  }
  imageChooserEvent:any = '';
  myForm: FormGroup = new FormGroup({
    'stock-id': new FormControl('-1'),
    'stock-name': new FormControl('',
      Validators.required
    ),
    'stock-code': new FormControl(null, [
      Validators.required
    ]),
    'stock-status': new FormControl(true),
    'stock-cdate': new FormControl(''),
    'brand-owner': new FormControl('', Validators.required),
    'brand': new FormControl('', Validators.required),
    'category': new FormControl('', Validators.required),
    'sub-category': new FormControl('', Validators.required),
    'packtype': new FormControl('', Validators.required),
    'packsize': new FormControl('', Validators.required),
    'brandowner-list': new FormControl([]),
    'category-list': new FormControl([]),
    'packtype-list': new FormControl([]),
    'packsize-list': new FormControl([])

  });
  myStockHoldingForm: FormGroup = new FormGroup({
    'cm': new FormControl(''),
    'cm-list': new FormControl([]),
    'stkhold-list': new FormControl([])
  })
  mySubStockHoldingForm: FormGroup = new FormGroup({
    'id': new FormControl('-1'),
    'qty': new FormControl(0, Validators.min(0)),
    'wh-name': new FormControl(''),
    'p-index': new FormControl(-1),

  })
  stockUnitForm: FormGroup = new FormGroup({
    'stockunit-list': new FormControl([]),
    'stockunit-filter-list': new FormControl([]),
    'pricetype-list': new FormControl([]),
    'currency-list': new FormControl([]),
    'stockunit-setup-list': new FormControl([]),
    'stockunit-idstring-list': new FormControl('')
  })
  setup_stockUnitForm: FormGroup = new FormGroup({
    'id': new FormControl('-1'),
    'uom': new FormControl('', Validators.required),
    'is-specific': new FormControl(false),
    'price': new FormControl(0.0),
    'ratio': new FormControl(0, [Validators.required, Validators.min(1)]),
  })
  base_stockUnitForm: FormGroup = new FormGroup({
    'id': new FormControl('-1'),
    'uom': new FormControl('', Validators.required),
    'price': new FormControl(0.0),
    'currency': new FormControl('')
  })
  constructor(private activeRoute: ActivatedRoute,
    private http: HttpClient,
    private router: Router,
    private pgService: PageServiceService,
    private popupService: PopupService) {

  }
  async ngAfterViewInit(): Promise<void> {
    await this.pgService.getUomList({ id: '-1' }).then(rs => {
      this.stockUnitForm.get('stockunit-list')?.setValue(
        rs.list
      );
    }).catch(
      e => {
        this.stockUnitForm.get('stockunit-list')?.setValue(
          []
        )
      }
    );
    await this.pgService.getPriceTypeList({ id: '-1' }).then((resp: any) => {
      this.stockUnitForm.get('pricetype-list')?.setValue(
        resp.list
      )


    }).catch((e) => {
      this.stockUnitForm.get('pricetype-list')?.setValue(
        []
      )

    })
    await this.pgService.getCurrencyList({ id: '-1' }).then((resp: any) => {
      this.stockUnitForm.get('currency-list')?.setValue(resp.list);
      // this.base_stockUnitForm.get('currency')?.setValue(resp.list.length == 0 ? '' : resp.list[0])
    })
    await this.pgService.getBrandOwners({ id: '-1', b1: true }).then((resp: any) => {
      this.myForm.get('brandowner-list')?.setValue(resp.list);
    })
    await this.pgService.getCategoryList({ id: '-1', b1: true }).then((resp: any) => {
      this.myForm.get('category-list')?.setValue(resp.list.sort((a: any, b: any) => (a.name > b.name) ? 1 : -1));
    })
    await this.pgService.getPackTypeList({ id: '-1' }).then((resp: any) => {
      this.myForm.get('packtype-list')?.setValue(resp.list.sort((a: any, b: any) => (a.name > b.name) ? 1 : -1));
    })
    await this.pgService.getPackSizeList({ id: '-1' }).then((resp: any) => {
      this.myForm.get('packsize-list')?.setValue(resp.list.sort((a: any, b: any) => (a.name > b.name) ? 1 : -1));
    });
    await this.pgService.getCompanyList({ id: '-1' }).then((resp: ViewResult<CompanyData>) => {
      this.myStockHoldingForm.get('cm-list')?.setValue(resp.list);
    });

    this.activeRoute.queryParams.subscribe(
      (p: Params) => {
        const id = p['id'];

        if (id == '-1' || id == undefined) {
          this.edit = true;
          $('#stock-cb-isedit').prop('disabled', true);
          this.myForm.get('stock-id')?.setValue('-1');
          this.myForm.get('stock-name')?.setValue('');
          this.myForm.get('stock-code')?.setValue('');
          this.myForm.get('stock-status')?.setValue(true);
          this.myForm.get('stock-cdate')?.setValue(this.pgService.getDateString(new Date()));


          $('#btn-stock-save').show();
        } else {
          this.popupService.showLoading("").then((e: any) => {
            e.present();
            this.getStockById(id).then(
              (resp: any) => {
                this.mapStock(resp);
                this.mapStockHolding(resp);
                this.mapStockUnit(resp);
                e.dismiss();
                this.edit = true;
              }
            ).catch(() => { e.dismiss() })
            this.editOn();
          })

        }
      }
    );
    console.log(this.myForm.get('brand-owner')?.value)

  }

  ngOnInit(): void {
    this.myForm.get('brand-owner')?.valueChanges.subscribe(
      changes => {
        this.myForm.get('brand')?.setValue('')
      }
    )
    this.myForm.get('category')?.valueChanges.subscribe(
      changes => {
        this.myForm.get('sub-category')?.setValue('')
      }
    )
    this.stockUnitForm.get('stockunit-setup-list')?.valueChanges.subscribe(
      changes => {
        if (changes.length > 0) this.base_stockUnitForm.get('uom')?.disable();
        else this.base_stockUnitForm.get('uom')?.enable();
      }
    )
  }
  editOn() {
    if (this.edit) {
      $('#btn-stock-save').show();
      $('#btn-stock-delete').show();
      $('#btn-stock-inactive').show();
    } else {
      $('#btn-stock-save').hide();
      $('#btn-stock-delete').hide();
      $('#btn-stock-inactive').hide();
    }
  }
  _getStockSetupModel(): StockSetupData {
    return {
      'stockHolding': [],
      'uomStocks': [],
      'stock': this._getStockModel(),
      'currency': this._getCurrencyModel()
    }
  }
  _getStockModel(): StockData {
    return {
      currentRow: -1,
      maxRowsPerPage: -1,
      rowNumber: 0,
      id: '-1',
      code: '',
      name: '',
      status: -1,
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
      id: '-189909',
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
  _getCurrencyModel(): CurrencyData {
    return {
      currentRow: -1,
      maxRowsPerPage: -1,
      rowNumber: 0,
      id: '-1',
      code: '',
      name: '',
      status: 0,
      cdate: '',
      rate: 0.0
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
  _getStockHoldingModel(): stockHoldingData {
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
      qty: 0,
      warehouse: this._getWareHouseModel(),
      stock: this._getStockModel(),
      company: this._getCompanyModel(),
      rate: 0
    }
  }
  _getCompanyModel(): CompanyData {
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
      ownerName: '',
      email: '',
      phone: '',
      warehouses: []
    }
  }
  _getWareHouseModel(): WarehouseData {
    return {
      currentRow: -1,
      maxRowsPerPage: -1,
      rowNumber: 0,
      id: '',
      code: '',
      name: '',
      status: 1,
      cdate: '',
      company: {
        currentRow: -1,
        maxRowsPerPage: -1,
        rowNumber: 0,
        id: '-1',
        code: '',
        name: '',
        status: 1,
        cdate: '',
        mdate: '',
        ownerName: '',
        email: '',
        phone: '',
        warehouses: []
      }
    }
  }
  _getUomModel(): UomData {
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
  _getPriceTypeModel(): PriceTypeData {
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
  _getStockUomModel(): StockUomData {
    return {
      currentRow: -1,
      maxRowsPerPage: -1,
      rowNumber: 0,
      id: '-1',
      code: '',
      name: '',
      status: -1,
      cdate: '',
      specficPrice: 0.0,
      base: this.pgService.ENUM.STATUS.True.code,
      priceType: this._getPriceTypeModel(),
      stock: this._getStockModel(),
      uom: this._getUomModel(),
      currency: this._getCurrencyModel(),
      price: 0.0,
      rate: 0.0,
      ratio: 0
    }
  }

  mapStock(data: StockData) {
    this.myForm.get('stock-id')?.setValue(data.id);
    this.myForm.get('stock-name')?.setValue(data.name);
    this.myForm.get('stock-code')?.setValue(data.code);
    this.myForm.get('stock-status')?.setValue(data.status == 1 ? true : false);
    this.myForm.get('stock-cdate')?.setValue(data.cdate);
    this.myForm.get('brand-owner')?.setValue(data.brand.brandOwner);
    this.myForm.get('brand')?.setValue(data.brand);
    this.myForm.get('category')?.setValue(data.category);
    this.myForm.get('sub-category')?.setValue(data.subCategory);
    this.myForm.get('packtype')?.setValue(data.packType);
    this.myForm.get('packsize')?.setValue(data.packSize)


  }
  mapStockHolding(data: StockData) {
    this.myStockHoldingForm.get('stkhold-list')?.setValue(data.stockHoldings);
    this.myStockHoldingForm.get('cm')?.setValue(data.stockHoldings.length > 0 ? ((data.stockHoldings as any)[0] as stockHoldingData).warehouse.company : '');
  }
  mapStockUnit(data: StockData) {
    let baseUom: any = '';
    for (let i = 0; i < data.uomStocks.length; i++) {
      if ((data.uomStocks[i] as any).base == this.pgService.ENUM.STATUS.True.code) {
        baseUom = data.uomStocks[i];
      } else {
        let suList = this.stockUnitForm.get('stockunit-setup-list')?.value;
        suList.push(data.uomStocks[i])
      }
    }
    if (baseUom !== '') {
      this.base_stockUnitForm.get('id')?.setValue(baseUom.id);
      this.base_stockUnitForm.get('uom')?.setValue(baseUom.uom);
      this.base_stockUnitForm.get('price')?.setValue(baseUom.price);
      this.base_stockUnitForm.get('currency')?.setValue(baseUom.currency);

    }
    this.stockUnitForm.get('stockunit-idstring-list')?.setValue(
      this.generateStringId()
    )
    if (this.stockUnitForm.get('stockunit-setup-list')?.value.length > 0)
      this.base_stockUnitForm.get('uom')?.disable();
  }
  generateStringId(): string {
    const spUom = this.stockUnitForm.get('stockunit-setup-list')?.value;
    const baseId = (this.base_stockUnitForm.get('uom')?.value as UomData).id;
    const setUpId = spUom.reduce((a: any, c: StockUomData) => {
      return a === '' ? (a + '' + c.uom.id) : (a + ',' + c.uom.id);
    }, '');
    return baseId + ',' + setUpId;
  }
  generateStringId_byValue(value: any): string {
    return value.reduce((a: string, c: string) => {
      return a === '' ? (a + '' + c) : (a + ',' + c);
    }, '');

  }

  bindAdditionalStockUnit(): StockUomData {
    let uom = this._getStockUomModel();
    uom.id = this.setup_stockUnitForm.get('id')?.value;
    uom.base = this.pgService.ENUM.STATUS.False.code;
    uom.priceType = (() => {
      if (this.setup_stockUnitForm.get('is-specific')?.value) {
        const pt = this.stockUnitForm.get('pricetype-list')?.value.filter((pt: PriceTypeData) => {
          return pt.code === '' + this.pgService.ENUM.PRICE_TYPE.BySpecific.code;
        });
        return pt[0]
      } else {
        const pt = this.stockUnitForm.get('pricetype-list')?.value.filter((pt: PriceTypeData) => {
          return pt.code == '' + this.pgService.ENUM.PRICE_TYPE.ByRatio.code
        })
        return pt[0]
      }
    })()
    uom.uom = this.setup_stockUnitForm.get('uom')?.value;
    uom.currency = this.base_stockUnitForm.get('currency')?.value;
    uom.price = (() => {
      if (this.setup_stockUnitForm.get('is-specific')?.value) {
        return this.setup_stockUnitForm.get('price')?.value;
      } else {
        return (this.base_stockUnitForm.get('price')?.value * this.setup_stockUnitForm.get('ratio')?.value)
      }
    })();
    uom.ratio = this.setup_stockUnitForm.get('ratio')?.value;
    return uom;
  }
  bindBaseStockUnit(): StockUomData {
    let stockuom = this._getStockUomModel();
    stockuom.id = this.base_stockUnitForm.get('id')?.value;
    stockuom.code = '';
    stockuom.name = '';
    stockuom.status = 1;
    stockuom.cdate = '';
    stockuom.specficPrice = 0.0;
    stockuom.base = this.pgService.ENUM.STATUS.True.code;
    stockuom.priceType = (() => {
      const pt = this.stockUnitForm.get('pricetype-list')?.value.filter((pt: PriceTypeData) => {
        return pt.code == '' + this.pgService.ENUM.PRICE_TYPE.ByRatio.code
      })
      return pt[0]
    })()
    stockuom.uom = this.base_stockUnitForm.get('uom')?.value;
    stockuom.ratio = 1;
    stockuom.currency = this.base_stockUnitForm.get('currency')?.value;
    stockuom.price = this.base_stockUnitForm.get('price')?.value;
    return stockuom;
  }
  bindStock(): StockData {
    let stk = this._getStockModel();
    stk.id = this.myForm.get('stock-id')?.value;
    stk.code = this.myForm.get('stock-code')?.value;
    stk.name = this.myForm.get('stock-name')?.value;
    stk.status = this.myForm.get('stock-status')?.value ? 1 : 2;
    stk.category = this.myForm.get('category')?.value;
    stk.subCategory = this.myForm.get('sub-category')?.value;
    stk.brand = this.myForm.get('brand')?.value;
    stk.packType = this.myForm.get('packtype')?.value;
    stk.packSize = this.myForm.get('packsize')?.value;
    stk.stockHoldings = this.myStockHoldingForm.get('stkhold-list')?.value.map((e: any) => { return e });
    stk.uomStocks = this.stockUnitForm.get('stockunit-setup-list')?.value.map((e: any) => { return e });
    (stk.uomStocks as any).push(this.bindBaseStockUnit());
    return stk;
  }

  companyChange() {
    if (this.myForm.get('stock-id')?.value == '-1') {
      this.pgService.getWarehouseList({
        company: {
          id: this.myStockHoldingForm.get('cm-id')?.value
        }
      }).then((resp: ViewResult<WarehouseData>) => {
        this.myStockHoldingForm.get('stkhold-list')?.setValue(resp.list.map(
          (wh: WarehouseData) => {
            let sh = this._getStockHoldingModel();
            sh.id = '-1';
            sh.warehouse = wh;
            sh.stock.id = '-1';
            sh.company = this.myStockHoldingForm.get('cm')?.value
            return sh;
          }
        ))
      }).catch((e) => {

      })
    } else {

    }

  }
  baseUomChange() {
    const setupUom = this.stockUnitForm.get('stockunit-setup-list')?.value;
    for (let uom of setupUom) {
      const baseUom: UomData = this.base_stockUnitForm.get('uom')?.value;
      const basePrice: number = this.base_stockUnitForm.get('price')?.value;
      if ((uom as StockUomData).priceType.code == '' + this.pgService.ENUM.PRICE_TYPE.ByRatio.code)
        uom.price = basePrice * uom.ratio;
      uom.currency = this.base_stockUnitForm.get('currency')?.value;
      //uom.uom = baseUom
    }
  }
  editStockHolding(index: number) {
    this.mySubStockHoldingForm.setValue({
      'id': (this.myStockHoldingForm.get('stkhold-list')?.value)[index].id,
      'qty': (this.myStockHoldingForm.get('stkhold-list')?.value)[index].qty,
      'wh-name': (this.myStockHoldingForm.get('stkhold-list')?.value)[index].warehouse.name,
      'p-index': index
    });
    $('#modal-stockhold').appendTo("body").modal('show');
  }
  openStockUnitModal(index: number) {
    if (index == -1) {
      this.setup_stockUnitForm.setValue({
        'id': '-1',
        'uom': '',
        'is-specific': false,
        'price': 1,
        'ratio': 1,
      })
      $('#modal-stockunit').appendTo("body").modal('show');
    } else {
      let stkUnitList = this.stockUnitForm.get('stockunit-setup-list')?.value;
      let su: StockUomData = stkUnitList[index];
      this.setup_stockUnitForm.setValue({
        'id': su.id,
        'uom': su.uom,
        'is-specific': su.priceType.code == "" + this.pgService.ENUM.PRICE_TYPE.BySpecific.code ? true : false,
        'price': su.price,
        'ratio': su.ratio,
      });

      this.stockUnitForm.get('stockunit-idstring-list')?.setValue(
        this.generateStringId_byValue(this.stockUnitForm.get('stockunit-idstring-list')?.value.split(',').filter((id: string) => {
          return id !== su.uom.id;
        }))
      )

      $('#modal-stockunit').appendTo("body").modal('show');

      let modal = document.getElementById('modal-stockunit') as HTMLElement;
      modal.addEventListener('hidden.bs.modal', () => {
        this.stockUnitForm.get('stockunit-idstring-list')?.setValue(
          this.generateStringId()
        )
      })
    }
    $(".btn-submit-newstockunit").click(() => {

    });
  }

  selectChange() {
    console.log(document.getElementById('stock-uom-list'))
  }
  bindSelect() {
    let pp = this.stockUnitForm.get('stockunit-list')?.value;
    this.setup_stockUnitForm.get('uom')?.setValue(pp[1])
  }
  submitStockQty() {
    let sh = this.myStockHoldingForm.get('stkhold-list')?.value[this.mySubStockHoldingForm.get('p-index')?.value];
    sh.qty = this.mySubStockHoldingForm.get('qty')?.value;
    $('#modal-stockhold').appendTo("body").modal('hide');
  }
  submitNewStockUnit() {
    let suflist = this.stockUnitForm.get('stockunit-setup-list')?.value;
    const index = suflist.findIndex((e: StockUomData) => {
      return e.uom.id == this.setup_stockUnitForm.get('uom')?.value.id
    })
    if (index !== -1) {
      suflist[index] = this.bindAdditionalStockUnit();
    } else {
      this.stockUnitForm.get('stockunit-setup-list')?.setValue(
        [...suflist, this.bindAdditionalStockUnit()]
      )
    }
    this.stockUnitForm.get('stockunit-idstring-list')?.setValue(
      this.generateStringId()
    )
  }
  getStockById(id: string) {
    return new Promise((pro, reject) => {
      const url = this.pgService.config.url + "stock-setup/getbyid/" + id;
      this.http.get(url, this.pgService.getOptions()).subscribe(
        (resp: any) => {
          if (resp.status == 200) {
            pro(resp.data)
          } else {
            reject("No record!");
          }
        },
        error => {
          reject("Server error");
        }
      )
    })
  }
  submit() {
    let obj = this.bindStock();
    console.log(obj)
    if (obj.id === '-1') {
      this.save(obj);
    } else {
      this.update(obj);
    }

  }
  update(obj: any) {
    console.log(obj)
    
    const url = this.pgService.config.url + 'stock-setup/update'
    this.http.post(url, obj, this.pgService.getOptions()).subscribe(
      (resp:any)=> {
        if(resp.status == 200)
        this.popupService.showAlert("Success");
      },
      (error) => {
        this.popupService.showAlert("Server don't response. Try again!");
       
      }
    )
  }
  save(obj: any) {
    const url = this.pgService.config.url + 'stock-setup/save'
    this.http.post(url, obj, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          this.popupService.showAlert("Success");
        }
        else {
          this.popupService.showAlert(resp.message);
        
        }
      },
      (error) => {
        window.alert("Server Error")
      }
    )
  }
  delete() {
    const url = this.pgService.config.url + 'stock-setup/delete'
    this.http.post(url, {
      'id': this.myForm.get('stock-id')?.value
    }, this.pgService.getOptions()).subscribe(
      (resp: any) => {
        if (resp.status == 200) {
          window.alert("Successfully deleted");
          this.router.navigate(['/price-type-list'])
        } else window.alert(resp.message)
      },
      (error) => {
        window.alert("Server Error")
      }
    )
  }
  openImageCropper(e:any){
    this.imageChooserEvent = e;
    $('#modal-image-chooser').appendTo("body").modal('show');
  }
  setImage(e:any){

  }

}
