import { Routes } from '@angular/router';
import { UserViewComponent } from './user/user-view/user-view.component';
import { ShipViewComponent } from './ship/ship-view/ship-view.component';
import { ModelViewComponent } from './modelo/model-view/model-view.component';
import { ShipListComponent } from './ship/ship-list/ship-list.component';
import { UserListComponent } from './user/user-list/user-list.component';
import { ModelListComponent } from './modelo/model-list/model-list.component';
import { ShipEditComponent } from './ship/ship-edit/ship-edit.component';
import { UserEditComponent } from './user/user-edit/user-edit.component';
import { ModelEditComponent } from './modelo/model-edit/model-edit.component';
import { BoardViewComponent } from './board/board-view/board-view.component';
import { BoardListComponent } from './board/board-list/board-list.component';
import { BoardEditComponent } from './board/board-edit/board-edit.component';
import { GameComponent } from './game/game.component';

export const routes: Routes = [
  //User routes
  { path: 'users', component: UserListComponent },
  { path: 'user/view/:id', component: UserViewComponent },
  { path: 'user/edit/:id', component: UserEditComponent },
  //Ship routes
  { path: 'ships', component: ShipListComponent },
  { path: 'ships/view/:id', component: ShipViewComponent },
  { path: 'ships/edit/:id', component: ShipEditComponent },
  //Model routes
  { path: 'models', component: ModelListComponent },
  { path: 'models/view/:id', component: ModelViewComponent },
  { path: 'model/edit/:id', component: ModelEditComponent },
  //Board routes
  { path: 'boards', component: BoardListComponent },
  { path: 'board/view/:id', component: BoardViewComponent },
  { path: 'board/edit/:id', component: BoardEditComponent },
  //Game routes
  { path: 'game', component: GameComponent },
  { path: '', redirectTo: 'boards', pathMatch: 'full' },
];
