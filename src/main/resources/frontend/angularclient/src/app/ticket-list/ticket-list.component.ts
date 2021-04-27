import { Ticket } from '../model/ticket';
import { TicketService } from '../service/ticket-service.service';
import { Component, OnInit } from '@angular/core';
import { filter, map } from 'rxjs/operators';

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.css']
})
export class TicketListComponent implements OnInit {

  tickets: Ticket[] = [];

  constructor(private ticketService : TicketService) { }

  ngOnInit(): void {
    this.ticketService.findAll().pipe(
      filter(data => !!data),
      map((data: any) => data.tickets)
    ).subscribe((data: Ticket[]) => {
      this.tickets = data;
    });
  }
}
