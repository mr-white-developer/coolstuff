import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PriceTypeSetupComponent } from './price-type-setup.component';

describe('PriceTypeSetupComponent', () => {
  let component: PriceTypeSetupComponent;
  let fixture: ComponentFixture<PriceTypeSetupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PriceTypeSetupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PriceTypeSetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
