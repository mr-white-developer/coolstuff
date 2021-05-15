import { Pipe, PipeTransform } from '@angular/core';
import { StockUomData, UomData } from './model';

@Pipe({
  name: 'stkUnitFilter',
  pure: false
})
export class StkUnitFilterPipe implements PipeTransform {

  transform(value: [], data:[]): [] {
    let resultList:any = [];
   if(value.length==0){
     return value;
   }else{
    resultList =   value.filter( (uom:UomData)=>{
      return data.filter( (su:StockUomData)=>{
        return uom.id == su.uom.id
      }).length > 0 ? false:true
    });
    console.log(resultList)
   }
   return resultList;
  }

}
