import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StockUnitSetupComponent } from './stock-unit-setup.component';

describe('StockUnitSetupComponent', () => {
  let component: StockUnitSetupComponent;
  let fixture: ComponentFixture<StockUnitSetupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StockUnitSetupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StockUnitSetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
