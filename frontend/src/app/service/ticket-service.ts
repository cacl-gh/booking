import { Ticket } from '../model/ticket';
import { environment } from '../../environments/environment';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {TicketsPage} from '../model/ticketsPage';


@Injectable()
export class TicketService {

  private static readonly BOOKING_URL = environment.baseApi + '/bookings';

  constructor(private http: HttpClient) {
  }

  public findAll(page: number, pageSize: number): Observable<any> {
    const params = new HttpParams().set('page', String(page)).set('maxResults', String(pageSize));
    return this.http.get<TicketsPage>(TicketService.BOOKING_URL, {params});
  }

  // tslint:disable-next-line:typedef
  public save(ticket: Ticket) {
    return this.http.post<Ticket>(TicketService.BOOKING_URL, ticket);
  }
}
