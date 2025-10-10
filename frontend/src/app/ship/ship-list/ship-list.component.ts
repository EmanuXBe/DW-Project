import { Component, signal, output, inject } from '@angular/core';
import { Ship } from '../../model/ship';
import { ShipService } from '../../shared/ship.service';

@Component({
  selector: 'app-ship-list',
  imports: [],
  templateUrl: './ship-list.component.html',
  styleUrl: './ship-list.component.css'
})
export class ShipListComponent {
  ships = signal<Ship[]>([]);

  shipClicked = output<Ship>();
  shipService = inject(ShipService);

  ngOnInit() {
    this.shipService.findAll().subscribe(data => this.ships.set(data));
  }

  shipSelected(s: Ship) {
    this.shipClicked.emit(s);
  }

}
