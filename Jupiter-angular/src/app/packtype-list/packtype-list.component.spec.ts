import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PacktypeListComponent } from './packtype-list.component';

describe('PacktypeListComponent', () => {
  let component: PacktypeListComponent;
  let fixture: ComponentFixture<PacktypeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PacktypeListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PacktypeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
