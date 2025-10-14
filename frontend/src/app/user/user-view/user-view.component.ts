import { Component, inject, input, signal } from '@angular/core';
import { User } from '../../model/user';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../shared/user.service';
import { switchMap } from 'rxjs/internal/operators/switchMap';

@Component({
  selector: 'app-user-view',
  imports: [],
  templateUrl: './user-view.component.html',
  styleUrl: './user-view.component.css',
})
export class UserViewComponent {
  userService = inject(UserService);

  route = inject(ActivatedRoute);

  router = inject(Router);

  user = signal<User>({});

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params) => this.userService.findById(params['id'])))
      .subscribe((resp) => this.user.set(resp));
  }

  goBack() {
    this.router.navigate(['/users']);
  }
  /*gotoUsers() {
    this.router.navigate(['/users/list']);
  }*/
}
