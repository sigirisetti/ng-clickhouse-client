import { TestBed } from '@angular/core/testing';

import { TfxStaticDataService } from './tfx-static-data.service';

describe('TfxStaticDataService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TfxStaticDataService = TestBed.get(TfxStaticDataService);
    expect(service).toBeTruthy();
  });
});
