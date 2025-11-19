import { Component, signal, output, inject, OnInit } from '@angular/core';
import { Ship } from '../../model/ship';
import { ShipService } from '../../shared/ship.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-ship-list',
  imports: [RouterLink],
  templateUrl: './ship-list.component.html',
  styleUrl: './ship-list.component.css',
})
export class ShipListComponent implements OnInit {
  ships = signal<Ship[]>([]);
  router = inject(Router);

  shipClicked = output<Ship>();
  shipService = inject(ShipService);

  ngOnInit() {
    this.shipService.findAll().subscribe({
      next: (data) => this.ships.set(data),
      error: (err) => {
        console.error('Error loading ships:', err);
        // El interceptor ya muestra el alert
      },
    });
  }

  shipSelected(s: Ship) {
    this.shipClicked.emit(s);
  }
}
