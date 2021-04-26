import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Ticket } from '../model/ticket';
// @ts-ignore
import { Observable } from 'rxjs/Observable';

@Injectable()
export class UserService {

  private listUrl: string;
  private bookUrl: string;

  constructor(private http: HttpClient) {
    this.listUrl = 'http://localhost:8080/list';
    this.bookUrl = 'http://localhost:8080/book';
  }

  public findAll(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(this.listUrl);
  }

  public save(user: Ticket) {
    return this.http.post<Ticket>(this.bookUrl, user);
  }
}
