import {Ticket} from './ticket';

export interface TicketsPage {
  totalItems: number;
  tickets: Ticket[];
  totalPages: number;
  currentPage: number;
}
