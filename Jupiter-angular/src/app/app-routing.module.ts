import { NgModule } from '@angular/core';
import { RouterModule, PreloadAllModules, Routes } from '@angular/router';
import { BrandListComponent } from './brand-list/brand-list.component';
import { BrandOwnerListComponent } from './brand-owner-list/brand-owner-list.component';
import { BrandOwnerComponent } from './brand-owner/brand-owner.component';
import { BrandComponent } from './brand/brand.component';
import { CategorySetupComponent } from './category-setup/category-setup.component';
import { CategoryComponent } from './category/category.component';
import { CompanyListComponent } from './company-list/company-list.component';
import { CompanySetupComponent } from './company-setup/company-setup.component';
import { EntryComponent } from './entry/entry.component';
import { LoginComponent } from './login/login.component';
import { PacksizeListComponent } from './packsize-list/packsize-list.component';
import { PacksizeSetupComponent } from './packsize-setup/packsize-setup.component';
import { PacktypeListComponent } from './packtype-list/packtype-list.component';
import { PacktypeSetupComponent } from './packtype-setup/packtype-setup.component';
import { PopupComponent } from './popup/popup.component';
import { PriceTypeListComponent } from './price-type-list/price-type-list.component';
import { PriceTypeSetupComponent } from './price-type-setup/price-type-setup.component';
import { StockListComponent } from './stock-list/stock-list.component';
import { StockSetupComponent } from './stock-setup/stock-setup.component';
import { StockUnitListComponent } from './stock-unit-list/stock-unit-list.component';
import { StockUnitSetupComponent } from './stock-unit-setup/stock-unit-setup.component';
import { WarehouseListComponent } from './warehouse-list/warehouse-list.component';
import { WarehouseSetupComponent } from './warehouse-setup/warehouse-setup.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'entry',
    pathMatch: 'full'
  },
  {
    path: 'brand', component: BrandComponent
  },
  {
    path: 'brand-list', component: BrandListComponent
  },
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'brand-owner', component: BrandOwnerComponent
  },
  {
    path: 'entry', component: EntryComponent
  },
  {
    path: 'brand-owner-list', component: BrandOwnerListComponent
  },
  {
    path: 'warehouse-setup', component: WarehouseSetupComponent
  },
  {
    path: 'warehouse-list', component: WarehouseListComponent
  },
  {
    path: 'company-setup', component: CompanySetupComponent
  },
  {
    path: 'company-list', component: CompanyListComponent
  },
  {
    path: 'category', component: CategoryComponent
  },
  {
    path: 'category-setup', component: CategorySetupComponent
  },
  {
    path: 'packtype-list', component: PacktypeListComponent
  },
  {
    path: 'packtype-setup', component: PacktypeSetupComponent
  },
  {
    path: 'packsize-list', component: PacksizeListComponent
  },
  {
    path: 'packsize-setup', component: PacksizeSetupComponent
  },
  {
    path: 'uom-list', component: StockUnitListComponent
  },
  {
    path: 'uom-setup', component: StockUnitSetupComponent
  },
  {
    path: 'price-type-list', component: PriceTypeListComponent
  },
  {
    path: 'price-type-setup', component: PriceTypeSetupComponent
  },
  {
    path: 'stock-list', component: StockListComponent
  },
  {
    path: 'stock-setup', component: StockSetupComponent
  },
  {
    path: 'popup', component: PopupComponent
  },
];
@NgModule({
  imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
