import { Component, inject, input, WritableSignal } from '@angular/core';
import { Model } from '../../model/model';
import { signal } from '@angular/core';
import { switchMap } from 'rxjs/internal/operators/switchMap';
import { ActivatedRoute, Router } from '@angular/router';
import { ModeloService } from '../../shared/modelo.service';

@Component({
  selector: 'app-model-view',
  imports: [],
  templateUrl: './model-view.component.html',
  styleUrl: './model-view.component.css',
})
export class ModelViewComponent {
  modeloService = inject(ModeloService);

  route = inject(ActivatedRoute);

  router = inject(Router);

  model = signal<Model>({});

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params) => this.modeloService.findById(params['id'])))
      .subscribe((resp) => this.model.set(resp));
  }

  goBack() {
    this.router.navigate(['/models']);
  }
}
