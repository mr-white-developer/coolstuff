import { Pipe, PipeTransform } from '@angular/core';
import { StockUomData, UomData } from '../model';

@Pipe({
  name: 'stockUnitFilter',
})
export class StockUnitFilterPipe implements PipeTransform {

  transform(value: any, setup:string): any {
    if(setup == '') return value;
    let result:any = [];
    let setuplist = setup.split(',');
    value.map( (v:UomData)=>{
      const index = setuplist.findIndex( (i)=>{
        return i == v.id
      });
      if(index == -1) result.push(v)
    })
    return result;
  }

}
