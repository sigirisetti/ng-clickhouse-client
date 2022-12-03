import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TfxComponent } from './tfx.component';

describe('TfxComponent', () => {
  let component: TfxComponent;
  let fixture: ComponentFixture<TfxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TfxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TfxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
