import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubCategorySetupComponent } from './sub-category-setup.component';

describe('SubCategorySetupComponent', () => {
  let component: SubCategorySetupComponent;
  let fixture: ComponentFixture<SubCategorySetupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubCategorySetupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubCategorySetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
