import { Component, model, inject } from '@angular/core';
import { ShipService } from '../../shared/ship.service';
import { Ship } from '../../model/ship';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs/internal/operators/switchMap';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ship-edit',
  imports: [FormsModule],
  templateUrl: './ship-edit.component.html',
  styleUrl: './ship-edit.component.css',
})
export class ShipEditComponent {
  shipService = inject(ShipService);
  route = inject(ActivatedRoute);
  router = inject(Router);

  ship = model<Ship>({});

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params) => this.shipService.findById(params['id'])))
      .subscribe((resp) => this.ship.set(resp));
  }

  onSubmit() {
    this.shipService.updateShip(this.ship().id!, this.ship()).subscribe({
      next: (resp) => {
        console.log(resp);
        this.router.navigate(['/ships']);
      },
      error: (err) => {
        alert('Error updating ship');
        console.error(err);
      },
    });
  }
}
