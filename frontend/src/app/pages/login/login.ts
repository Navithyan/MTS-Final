import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { catchError, EMPTY, switchMap } from 'rxjs';

import { AuthService } from '../../core/auth/auth.service';
import { AccountService } from '../../core/services/account.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {

  username: string = '';
  password: string = '';

  constructor(
    private router: Router,
    private authService: AuthService,
    private accountService: AccountService
  ) {}

  login() {
    this.authService.login({ username: this.username, password: this.password }).pipe(
      switchMap((res) => {
        if (res.token) {
          this.authService.saveToken(res.token);
        }
        return this.accountService.getAllAccounts();
      }),
      catchError(() => {
        alert('Invalid credentials');
        return EMPTY;
      })
    ).subscribe((accounts) => {
      const account = accounts.find((a) => a.holderName === this.username);
      if (!account) {
        alert('No account mapped to this user. Please create account first.');
        this.authService.logout();
        return;
      }

      localStorage.setItem('loggedInUser', JSON.stringify(account));
      this.router.navigate(['/dashboard']);
    });
  }
}
