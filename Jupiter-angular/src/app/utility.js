import { DatePipe } from '@angular/common';
import { transform, transpile } from 'typescript';

function getDateString(date,format){
    return transform(date,format)
}
module.exports =  getDateString;