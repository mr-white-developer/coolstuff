import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategorySetupComponent } from './category-setup.component';

describe('CategorySetupComponent', () => {
  let component: CategorySetupComponent;
  let fixture: ComponentFixture<CategorySetupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CategorySetupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CategorySetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
