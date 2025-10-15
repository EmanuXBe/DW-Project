import { Component, signal, output, inject, OnInit } from '@angular/core';
import { Ship } from '../../model/ship';
import { ShipService } from '../../shared/ship.service';
import { BoardService } from '../../shared/board.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-ship-list',
  imports: [RouterLink],
  templateUrl: './ship-list.component.html',
  styleUrl: './ship-list.component.css',
})
export class ShipListComponent implements OnInit {
  ships = signal<Ship[]>([]);
  firstBoardId = signal<number>(1); // Por defecto 1
  router = inject(Router);

  shipClicked = output<Ship>();
  shipService = inject(ShipService);
  boardService = inject(BoardService);

  ngOnInit() {
    this.shipService.findAll().subscribe((data) => this.ships.set(data));

    // Obtener el primer board disponible
    this.boardService.findAll().subscribe((boards) => {
      if (boards && boards.length > 0) {
        this.firstBoardId.set(boards[0].id!);
        console.log('First board ID:', this.firstBoardId());
      }
    });
  }

  shipSelected(s: Ship) {
    this.shipClicked.emit(s);
  }

  goToUsers() {
    this.router.navigate(['/users']);
  } /*
  goToShips() {
    this.router.navigate(['/ships']);
  }*/
  goToModels() {
    this.router.navigate(['/models']);
  }

  goToBoards() {
    this.router.navigate(['/boards']);
  }
}
