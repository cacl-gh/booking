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

  onSubmit() {
    this.ticketService.save(this.ticket).subscribe(result => this.gotoTicketList());
  }

  gotoTicketList() {
    this.router.navigate(['/list']);
  }
}
