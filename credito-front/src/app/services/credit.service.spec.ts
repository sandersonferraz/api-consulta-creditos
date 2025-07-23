import { TestBed } from '@angular/core/testing';
import { CreditService } from './credit.service';

describe('Credit', () => {
  let service: CreditService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreditService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
