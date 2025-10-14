import { Component, model, inject } from '@angular/core';
import { UserService } from '../../shared/user.service';
import { User } from '../../model/user';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs/internal/operators/switchMap';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-edit',
  imports: [FormsModule],
  templateUrl: './user-edit.component.html',
  styleUrl: './user-edit.component.css',
})
export class UserEditComponent {
  userService = inject(UserService);
  route = inject(ActivatedRoute);
  router = inject(Router);

  user = model<User>({});

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params) => this.userService.findById(params['id'])))
      .subscribe((resp) => this.user.set(resp));
  }

  onSubmit() {
    this.userService.update(this.user()).subscribe({
      next: (resp) => {
        console.log(resp);
        this.router.navigate(['/users']);
      },
      error: (err) => {
        alert('Error updating user');
        console.error(err);
      },
    });
  }
}
