import { Component, signal, inject, output } from '@angular/core';
import { User } from '../../model/user';
import { UserService } from '../../shared/user.service';

@Component({
  selector: 'app-user-list',
  imports: [],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent {
  users = signal<User[]>([]);

  userClicked = output<User>();
  userService = inject(UserService);

  ngOnInit() {
    this.userService.findAll().subscribe(data => this.users.set(data));
  }

  userSelected(u: User) {
    this.userClicked.emit(u);
  }

}
