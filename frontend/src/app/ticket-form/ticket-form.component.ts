import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TicketService } from '../service/ticket-service';
import { Ticket } from '../model/ticket';

@Component({
  selector: 'app-ticket-form',
  templateUrl: './ticket-form.component.html',
  styleUrls: ['./ticket-form.component.css']
})
export class TicketFormComponent {

  ticket: Ticket;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private ticketService: TicketService) {
    this.ticket = {};
  }

  // tslint:disable-next-line:typedef
  onSubmit() {
    this.ticketService.save(this.ticket).subscribe(() => this.gotoTicketList());
  }

  // tslint:disable-next-line:typedef
  gotoTicketList() {
    this.router.navigate(['/getBookings']);
  }
}
