import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PageUnderConstructionComponent } from './page-under-construction.component';

describe('PageUnderConstructionComponent', () => {
  let component: PageUnderConstructionComponent;
  let fixture: ComponentFixture<PageUnderConstructionComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PageUnderConstructionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PageUnderConstructionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
