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
import { GameComponent } from './game/game.component';
import { GameSetupComponent } from './game/game-setup/game-setup.component';
import { adminGuard } from './guards/admin.guard';
import { userGuard } from './guards/user.guard';
import { LoginComponent } from './security/login/login.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  //User routes - SOLO ADMIN
  { path: 'users', component: UserListComponent, canActivate: [adminGuard] },
  { path: 'user/view/:id', component: UserViewComponent, canActivate: [adminGuard] },
  { path: 'user/edit/:id', component: UserEditComponent, canActivate: [adminGuard] },
  //Ship routes - SOLO ADMIN
  { path: 'ships', component: ShipListComponent, canActivate: [adminGuard] },
  { path: 'ships/view/:id', component: ShipViewComponent, canActivate: [adminGuard] },
  { path: 'ships/edit/:id', component: ShipEditComponent, canActivate: [adminGuard] },
  //Model routes - SOLO ADMIN
  { path: 'models', component: ModelListComponent, canActivate: [adminGuard] },
  { path: 'models/view/:id', component: ModelViewComponent, canActivate: [adminGuard] },
  { path: 'model/edit/:id', component: ModelEditComponent, canActivate: [adminGuard] },
  //Game routes - SOLO USER
  { path: 'game-setup', component: GameSetupComponent, canActivate: [userGuard] },
  { path: 'game', component: GameComponent, canActivate: [userGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
];
