import { Component, OnInit } from '@angular/core';
import { Ticket } from '../model/ticket';
import { TicketService } from '../service/ticket.service';

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.css']
})
export class TicketListComponent implements OnInit {

  tickets : Ticket[];

  constructor(private ticketService : TicketService) { }

  ngOnInit(): void {
    this.ticketService.findAll().subscribe(data => {
      this.tickets = data;
    });
  }
}
