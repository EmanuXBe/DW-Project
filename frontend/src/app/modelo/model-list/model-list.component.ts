import { Component, inject,output,signal } from '@angular/core';
import { Model } from '../../model/model';
import { ModeloService } from '../../shared/modelo.service'; 

@Component({
  selector: 'app-model-list',
  imports: [],
  templateUrl: './model-list.component.html',
  styleUrl: './model-list.component.css'
})
export class ModelListComponent {
   models = signal<Model[]>([]);

   modelClicked = output<Model>();

   modeloService = inject(ModeloService);

   ngOnInit() {
    this.modeloService.findAll().subscribe(data => this.models.set(data));
   }


    modelSelected(m: Model) {
        this.modelClicked.emit(m);
    }
}
