import { Component, model, inject } from '@angular/core';
import { ModeloService } from '../../shared/modelo.service';
import { Model } from '../../model/model';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs/internal/operators/switchMap';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-model-edit',
  imports: [FormsModule],
  templateUrl: './model-edit.component.html',
  styleUrl: './model-edit.component.css',
})
export class ModelEditComponent {
  modelService = inject(ModeloService);
  route = inject(ActivatedRoute);
  router = inject(Router);

  modelo = model<Model>({});

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params) => this.modelService.findById(params['id'])))
      .subscribe((resp) => this.modelo.set(resp));
  }

  onSubmit() {
    this.modelService.update(this.modelo().id!, this.modelo()).subscribe({
      next: (resp) => {
        console.log(resp);
        this.router.navigate(['/models']);
      },
      error: (err) => {
        alert('Error updating model');
        console.error(err);
      },
    });
  }
}
