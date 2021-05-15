import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PacksizeSetupComponent } from './packsize-setup.component';

describe('PacksizeSetupComponent', () => {
  let component: PacksizeSetupComponent;
  let fixture: ComponentFixture<PacksizeSetupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PacksizeSetupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PacksizeSetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
