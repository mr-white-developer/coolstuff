import { Component, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { CategoryData, SubCategoryData } from '../model';

@Component({
  selector: 'app-sub-category-setup',
  templateUrl: './sub-category-setup.component.html',
  styleUrls: ['./sub-category-setup.component.css']
})
export class SubCategorySetupComponent implements OnInit,OnChanges {
  @Input('input') input = "";
  @Input('subCategory') subCategory:SubCategoryData = this._getSubCategoryData();
  inputString :string  = "";

  fG:FormGroup = new FormGroup({
    'sub-name':new FormControl('')
  })
  constructor() { }
  ngOnChanges(changes: SimpleChanges): void {
    this.inputString = this.input;
    this.fG.setValue({
      'sub-name': this.subCategory.name
    })
  }

  ngOnInit(): void {
   this.inputString = this.input;
  }
  _getCategoryData(): CategoryData {
    return {
      currentRow: -1,
      maxRowsPerPage: -1,
      rowNumber: -1,
      id: '-1',
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

}
