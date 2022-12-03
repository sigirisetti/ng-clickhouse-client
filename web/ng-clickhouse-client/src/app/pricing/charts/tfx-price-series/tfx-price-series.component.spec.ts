import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TfxPriceSeriesComponent } from './tfx-price-series.component';

describe('TfxPriceSeriesComponent', () => {
  let component: TfxPriceSeriesComponent;
  let fixture: ComponentFixture<TfxPriceSeriesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TfxPriceSeriesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TfxPriceSeriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
