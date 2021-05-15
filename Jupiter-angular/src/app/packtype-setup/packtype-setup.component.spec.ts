import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PacktypeSetupComponent } from './packtype-setup.component';

describe('PacktypeSetupComponent', () => {
  let component: PacktypeSetupComponent;
  let fixture: ComponentFixture<PacktypeSetupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PacktypeSetupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PacktypeSetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
