import { Component, inject, input, signal } from '@angular/core';
import { Ship } from '../../model/ship';
import { ShipService } from '../../shared/ship.service';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs/internal/operators/switchMap';

@Component({
  selector: 'app-ship-view',
  imports: [],
  templateUrl: './ship-view.component.html',
  styleUrl: './ship-view.component.css',
})
export class ShipViewComponent {
  shipService = inject(ShipService);

  route = inject(ActivatedRoute);

  router = inject(Router);

  ship = signal<Ship>({});

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params) => this.shipService.findById(params['id'])))
      .subscribe((resp) => this.ship.set(resp));
  }

  goBack() {
    this.router.navigate(['/ships']);
  }
}
