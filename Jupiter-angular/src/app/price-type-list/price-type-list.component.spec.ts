import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PriceTypeListComponent } from './price-type-list.component';

describe('PriceTypeListComponent', () => {
  let component: PriceTypeListComponent;
  let fixture: ComponentFixture<PriceTypeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PriceTypeListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PriceTypeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
