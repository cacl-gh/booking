import { Ticket } from '../model/ticket';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class TicketService {

  private static readonly LIST_URL = environment.baseApi + '/list';
  private static readonly BOOK_URL = environment.baseApi + '/book';

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(TicketService.LIST_URL);
  }

  public save(user: Ticket) {
    return this.http.post<Ticket>(TicketService.BOOK_URL, user);
  }
}
