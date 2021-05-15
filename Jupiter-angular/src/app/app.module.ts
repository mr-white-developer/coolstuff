import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { BrandComponent } from './brand/brand.component';
import { DatePipe, HashLocationStrategy, LocationStrategy } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxPaginationModule } from 'ngx-pagination';
import { LoginComponent } from './login/login.component';
import { HttpClientModule } from '@angular/common/http';
import { BrandOwnerComponent } from './brand-owner/brand-owner.component';
import { EntryComponent } from './entry/entry.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrandOwnerListComponent } from './brand-owner-list/brand-owner-list.component';
import { BrandListComponent } from './brand-list/brand-list.component';
import { WarehouseSetupComponent } from './warehouse-setup/warehouse-setup.component';
import { WarehouseListComponent } from './warehouse-list/warehouse-list.component';
import { CompanySetupComponent } from './company-setup/company-setup.component';
import { CompanyListComponent } from './company-list/company-list.component';
import { CategoryComponent } from './category/category.component';
import { CategorySetupComponent } from './category-setup/category-setup.component';
import { SubCategorySetupComponent } from './sub-category-setup/sub-category-setup.component';
import { PacktypeListComponent } from './packtype-list/packtype-list.component';
import { PacktypeSetupComponent } from './packtype-setup/packtype-setup.component';
import { PacksizeListComponent } from './packsize-list/packsize-list.component';
import { PacksizeSetupComponent } from './packsize-setup/packsize-setup.component';
import { StockUnitListComponent } from './stock-unit-list/stock-unit-list.component';
import { StockUnitSetupComponent } from './stock-unit-setup/stock-unit-setup.component';
import { PriceTypeListComponent } from './price-type-list/price-type-list.component';
import { PriceTypeSetupComponent } from './price-type-setup/price-type-setup.component';
import { StockSetupComponent } from './stock-setup/stock-setup.component';
import { StockListComponent } from './stock-list/stock-list.component';
import { CheckedRadioDirective } from './checked-radio.directive';


@NgModule({
  declarations: [
    AppComponent,
    BrandComponent,
    LoginComponent,
    BrandOwnerComponent,
    EntryComponent,
    BrandOwnerListComponent,
    BrandListComponent,
    WarehouseSetupComponent,
    WarehouseListComponent,
    CompanySetupComponent,
    CompanyListComponent,
    CategoryComponent,
    CategorySetupComponent,
    SubCategorySetupComponent,
    PacktypeListComponent,
    PacktypeSetupComponent,
    PacksizeListComponent,
    PacksizeSetupComponent,
    StockUnitListComponent,
    StockUnitSetupComponent,
    PriceTypeListComponent,
    PriceTypeSetupComponent,
    StockSetupComponent,
    StockListComponent,
    CheckedRadioDirective,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production }),
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    NgxPaginationModule,
    ReactiveFormsModule,
    
  ],
  providers: [
     { provide: LocationStrategy, useClass: HashLocationStrategy },
     DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
