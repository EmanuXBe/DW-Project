import { Component, input } from '@angular/core';
import { User } from '../../model/user';

@Component({
  selector: 'app-user-view',
  imports: [],
  templateUrl: './user-view.component.html',
  styleUrl: './user-view.component.css'
})
export class UserViewComponent {
  user = input<User>({});
}
