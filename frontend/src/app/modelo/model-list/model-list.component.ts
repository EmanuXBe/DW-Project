import { Component, inject, output, signal } from '@angular/core';
import { Model } from '../../model/model';
import { ModeloService } from '../../shared/modelo.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-model-list',
  imports: [RouterLink],
  templateUrl: './model-list.component.html',
  styleUrl: './model-list.component.css',
})
export class ModelListComponent {
  models = signal<Model[]>([]);
  router = inject(Router);

  modelClicked = output<Model>();

  modeloService = inject(ModeloService);

  ngOnInit() {
    this.modeloService.findAll().subscribe((data) => this.models.set(data));
  }

  modelSelected(m: Model) {
    this.modelClicked.emit(m);
  }
  goToUsers() {
    this.router.navigate(['/users']);
  }
  goToShips() {
    this.router.navigate(['/ships']);
  }
}
