import { Component, inject, output, signal, OnInit } from '@angular/core';
import { Model } from '../../model/model';
import { ModeloService } from '../../shared/modelo.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-model-list',
  imports: [RouterLink],
  templateUrl: './model-list.component.html',
  styleUrl: './model-list.component.css',
})
export class ModelListComponent implements OnInit {
  models = signal<Model[]>([]);

  modelClicked = output<Model>();

  modeloService = inject(ModeloService);

  ngOnInit() {
    this.modeloService.findAll().subscribe({
      next: (data) => this.models.set(data),
      error: (err) => {
        console.error('Error loading models:', err);
        // El interceptor ya muestra el alert
      },
    });
  }

  modelSelected(m: Model) {
    this.modelClicked.emit(m);
  }
}
