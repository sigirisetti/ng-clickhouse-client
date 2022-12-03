import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { TfxLargeComponent } from './tfx-large.component';

describe('TfxLargeComponent', () => {
  let component: TfxLargeComponent;
  let fixture: ComponentFixture<TfxLargeComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ TfxLargeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TfxLargeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
