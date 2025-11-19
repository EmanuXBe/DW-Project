import { Component, signal, inject, output, OnInit } from '@angular/core';
import { User } from '../../model/user';
import { UserService } from '../../shared/user.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-user-list',
  imports: [RouterLink],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css',
})
export class UserListComponent implements OnInit {
  users = signal<User[]>([]);

  userClicked = output<User>();
  userService = inject(UserService);

  ngOnInit() {
    this.userService.findAll().subscribe({
      next: (data) => this.users.set(data),
      error: (err) => {
        console.error('Error loading users:', err);
        // El interceptor ya muestra el alert, aquí solo logueamos
      },
    });
  }

  userSelected(u: User) {
    this.userClicked.emit(u);
  }
}
