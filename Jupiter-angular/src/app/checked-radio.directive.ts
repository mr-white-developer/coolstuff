import { AfterViewInit, Directive, ElementRef, Input, OnChanges, OnInit, Renderer2, SimpleChanges } from '@angular/core';
declare var $: any;
@Directive({
  selector: '[appCheckedRadio]'
})
export class CheckedRadioDirective implements OnInit, AfterViewInit,OnChanges {
  @Input('appCheckedRadio')
  compareWith!: (o1: any, o2: any) => boolean;
  constructor(
    private elementRef: ElementRef, private render: Renderer2
  ) { }
  ngOnChanges(changes: SimpleChanges): void {
   console.log('ngon changes')
  }
  ngAfterViewInit(): void {

  console.log(this.compareWith(1, 2))
   
  }
  ngOnInit(): void {

  }
  ngDoCheck() {
   let select: HTMLSelectElement = this.elementRef.nativeElement;
   for(const  p in select.options){
     if(typeof parseInt(p) === 'number'){
       if(this.compareWith(select.options[p].value, select.value)){
         select.options[p].selected = true;
       }
     }
   }
  //  [selected]="uom.id == base_stockUnitForm.get('uom')?.value.id"
  }

}
