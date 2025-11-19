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

  // Campos separados para el formulario
  shipModel_ = '';
  shipColor_ = '';
  shipOwner_ = '';

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params) => this.shipService.findById(params['id'])))
      .subscribe((resp) => {
        this.ship.set(resp);

        // Extraer valores para el formulario
        if (typeof resp.model === 'string') {
          this.shipModel_ = resp.model;
        } else if (resp.model && typeof resp.model === 'object') {
          this.shipModel_ = resp.model.name || '';
          this.shipColor_ = resp.model.color || '';
        }

        if (typeof resp.owner === 'string' || typeof resp.owner === 'number') {
          this.shipOwner_ = String(resp.owner);
        } else if (resp.owner && typeof resp.owner === 'object') {
          this.shipOwner_ = String(resp.owner.id || '');
        }
      });
  }

  onSubmit() {
    // Crear el objeto a enviar con los valores del formulario
    const shipToUpdate: any = {
      id: this.ship().id,
      name: this.ship().name,
      model: this.shipModel_,
      color: this.shipColor_,
      owner: this.shipOwner_,
    };

    this.shipService.updateShip(this.ship().id!, shipToUpdate).subscribe({
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
