import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PacksizeListComponent } from './packsize-list.component';

describe('PacksizeListComponent', () => {
  let component: PacksizeListComponent;
  let fixture: ComponentFixture<PacksizeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PacksizeListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PacksizeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
