import { TestBed } from '@angular/core/testing';

import { ShortenLinkService } from './shorten-link.service';

describe('ShortenLinkService', () => {
  let service: ShortenLinkService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShortenLinkService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
