import { TicketService } from './ticket-service';
import { TestBed } from '@angular/core/testing';

describe('TicketServiceService', () => {
  let service: TicketService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TicketService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
