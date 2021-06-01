export interface Pagination {
    currentRow: number,
    maxRowsPerPage: number
}
export interface CompanyData {
    currentRow: number,
    maxRowsPerPage: number,
    rowNumber: number,
    id: string,
    code: string,
    name: string,
    status: number,
    cdate: string,
    mdate: string,
    ownerName: string,
    email: string,
    phone: string,
    warehouses: []
}
export interface WarehouseData {
    currentRow: number,
    maxRowsPerPage: number,
    rowNumber: number,
    id: string,
    code: string,
    name: string,
    status: number,
    cdate: string,
    company: CompanyData
}
export interface BrandData {
    currentRow: number,
    maxRowsPerPage: number,
    rowNumber: number,
    id: string,
    code: string,
    name: string,
    status: number,
    cdate: string,
    mdate: string
    brandOwner: BrandOwnerData
}
export interface BrandOwnerData {
    currentRow: number,
    maxRowsPerPage: number,
    rowNumber: number,
    id: string,
    code: string,
    name: string,
    status: number,
    cdate: string,
    mdate: string
}
export interface CategoryData {
    currentRow: number,
    maxRowsPerPage: number,
    rowNumber: number,
    id: string,
    code: string,
    name: string,
    status: number,
    cdate: string,
    subCategories: []
}
export interface SubCategoryData {
    currentRow: number,
    maxRowsPerPage: number,
    rowNumber: number,
    id: string,
    code: string,
    name: string,
    status: number,
    cdate: string,
    category: CategoryData
}
export interface PackTypeData {
    currentRow: number,
    maxRowsPerPage: number,
    rowNumber: number,
    id: string,
    code: string,
    name: string,
    status: number,
    cdate: string,
}
export interface PackSizeData {
    currentRow: number,
    maxRowsPerPage: number,
    rowNumber: number,
    id: string,
    code: string,
    name: string,
    status: number,
    cdate: string,
}
export interface UomData {
    currentRow: number,
    maxRowsPerPage: number,
    rowNumber: number,
    id: string,
    code: string,
    name: string,
    status: number,
    cdate: string,
}
export interface PriceTypeData {
    currentRow: number,
    maxRowsPerPage: number,
    rowNumber: number,
    id: string,
    code: string,
    name: string,
    status: number,
    cdate: string,
}
export interface StockData {
    currentRow: number,
    maxRowsPerPage: number,
    rowNumber: number,
    id: string,
    code: string,
    name: string,
    status: number,
    cdate: string,
    category: CategoryData,
    subCategory: SubCategoryData,
    brand: BrandData,
    packType: PriceTypeData,
    packSize: PackSizeData,
    stockHoldings: any,
    uomStocks: any,
    images:any
}
export interface StockSetupData {
    stockHolding: [],
    uomStocks: [],
    stock: StockData,
    currency: CurrencyData
}
export interface stockHoldingData {
    currentRow: number,
    maxRowsPerPage: number,
    rowNumber: number,
    id: string,
    code: string,
    name: string,
    status: number,
    cdate: string,
    mdate: string,
    qty: number,
    warehouse: WarehouseData,
    stock: StockData,
    company: CompanyData,
    rate: number
}
export interface CurrencyData {
    currentRow: number,
    maxRowsPerPage: number,
    rowNumber: number,
    id: string,
    code: string,
    name: string,
    status: number,
    cdate: string,
    rate: number
}
export interface PricingData {
    currentRow: number,
    maxRowsPerPage: number,
    rowNumber: number,
    id: string,
    code: string,
    name: string,
    status: number,
    cdate: string,
    currency: CurrencyData,

    price: number,
    rate: number
}
export interface StockUomData {
    currentRow: number,
    maxRowsPerPage: number,
    rowNumber: number,
    id: string,
    code: string,
    name: string,
    status: number,
    cdate: string,
    specficPrice: number,
    base: number,
    priceType: PriceTypeData,
    stock: StockData,
    uom: UomData,
    ratio: number,
    currency: CurrencyData,
    price: number,
    rate: number
}
export interface ViewResult<T> {
    data: T;
    list: [];
    message: string;
    status: number
}
export interface sysconfig {
    url: string,
    name: string,
    token:string,
    img_url:string
}
export interface ImageModelData {
    id:string,
    foreignKey: string,
    path: any,
    defaults: boolean,
    comment: string,
    code: string,
    name: string

}
export interface ImageProp {
    id:string,
    color: string,
    src: any,
    isDef: boolean,
    comment: string,
    name: string,
    fileType:string
}