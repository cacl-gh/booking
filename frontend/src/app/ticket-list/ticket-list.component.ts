import { Ticket } from '../model/ticket';
import { TicketService } from '../service/ticket-service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.css']
})
export class TicketListComponent implements OnInit {

  tickets: Ticket[] = [];
  currentIndex = -1;

  page = 1;
  count = 0;
  pageSize = 5;
  pageSizes = [5, 10, 20];

  constructor(private ticketService: TicketService) { }

  ngOnInit(): void {
    this.getTicketsPag();
  }

  getTicketsPag(): void {
    this.ticketService.findAll(this.page - 1, this.pageSize)
      .subscribe(
      response => {
        this.tickets = response.tickets;
        this.page = response.currentPage;
        this.count = response.totalItems;
        console.log(response);
      },
      error => {
        console.log(error);
      });
  }

  // tslint:disable-next-line:typedef
  handlePageChange(event){
    this.page = event;
    this.getTicketsPag();
  }

  handlePageSizeChange(event): void {
    this.pageSize = event.target.value;
    this.page = 1;
    this.getTicketsPag();
  }
}
