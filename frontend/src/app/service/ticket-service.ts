import { Ticket } from '../model/ticket';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class TicketService {

  private static readonly BOOKING_URL = environment.baseApi + '/bookings';

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(TicketService.BOOKING_URL);
  }

  public save(ticket: Ticket) {
    return this.http.post<Ticket>(TicketService.BOOKING_URL, ticket);
  }
}
