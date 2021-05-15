import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StockUnitListComponent } from './stock-unit-list.component';

describe('StockUnitListComponent', () => {
  let component: StockUnitListComponent;
  let fixture: ComponentFixture<StockUnitListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StockUnitListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StockUnitListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
