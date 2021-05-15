import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StockSetupComponent } from './stock-setup.component';

describe('StockSetupComponent', () => {
  let component: StockSetupComponent;
  let fixture: ComponentFixture<StockSetupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StockSetupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StockSetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
