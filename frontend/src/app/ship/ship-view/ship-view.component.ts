import { Component, input } from '@angular/core';
import { Ship } from '../../model/ship';

@Component({
  selector: 'app-ship-view',
  imports: [],
  templateUrl: './ship-view.component.html',
  styleUrl: './ship-view.component.css'
})
export class ShipViewComponent {
  ship = input<Ship>({});
}
