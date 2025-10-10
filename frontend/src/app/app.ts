import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ModelViewComponent } from './modelo/model-view/model-view.component';
import { ModeloService } from './shared/modelo.service';
import { ModelListComponent } from './modelo/model-list/model-list.component';
import { Model } from './model/model';
import { UserViewComponent } from './user/user-view/user-view.component';
import { User } from './model/user';
import { UserListComponent } from './user/user-list/user-list.component';
import { ShipViewComponent } from './ship/ship-view/ship-view.component';
import { ShipListComponent } from './ship/ship-list/ship-list.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ModelViewComponent, ModelListComponent, UserViewComponent, UserListComponent, ShipViewComponent, ShipListComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');

  selectedModel = signal<Model>({});
  selectedUser = signal<User>({});
  selectedShip = signal<any>({});

  selectModel(m: Model){
    this.selectedModel.set(m);
  }

  selectUser(u: User){
    this.selectedUser.set(u);
  }

  selectShip(s: any){
    this.selectedShip.set(s);
  }

}
