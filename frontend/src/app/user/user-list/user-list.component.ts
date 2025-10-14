import { Component, signal, inject, output } from '@angular/core';
import { User } from '../../model/user';
import { UserService } from '../../shared/user.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-user-list',
  imports: [RouterLink],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css',
})
export class UserListComponent {
  users = signal<User[]>([]);
  router = inject(Router);

  userClicked = output<User>();
  userService = inject(UserService);

  ngOnInit() {
    this.userService.findAll().subscribe((data) => this.users.set(data));
  }

  userSelected(u: User) {
    this.userClicked.emit(u);
  }
  /*gotoUsers() {
    this.router.navigate(['/users/list']);
  }*/
  goToShips() {
    this.router.navigate(['/ships']);
  }
  goToModels() {
    this.router.navigate(['/models']);
  }
}
