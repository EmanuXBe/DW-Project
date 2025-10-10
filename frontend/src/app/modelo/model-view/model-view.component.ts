import { Component, input, WritableSignal } from '@angular/core';
import { Model } from '../../model/model';
import { signal } from '@angular/core';

@Component({
  selector: 'app-model-view',
  imports: [],
  templateUrl: './model-view.component.html',
  styleUrl: './model-view.component.css'
})
export class ModelViewComponent {
  model = input<Model>({});
}
