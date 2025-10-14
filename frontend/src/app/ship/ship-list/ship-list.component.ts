import { Component, signal, output, inject } from '@angular/core';
import { Ship } from '../../model/ship';
import { ShipService } from '../../shared/ship.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-ship-list',
  imports: [RouterLink],
  templateUrl: './ship-list.component.html',
  styleUrl: './ship-list.component.css',
})
export class ShipListComponent {
  ships = signal<Ship[]>([]);
  router = inject(Router);

  shipClicked = output<Ship>();
  shipService = inject(ShipService);

  ngOnInit() {
    this.shipService.findAll().subscribe((data) => this.ships.set(data));
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
}
