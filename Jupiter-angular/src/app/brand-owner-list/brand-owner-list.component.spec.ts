import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrandOwnerListComponent } from './brand-owner-list.component';

describe('BrandOwnerListComponent', () => {
  let component: BrandOwnerListComponent;
  let fixture: ComponentFixture<BrandOwnerListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BrandOwnerListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BrandOwnerListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
