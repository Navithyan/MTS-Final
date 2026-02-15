import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { catchError, EMPTY, switchMap } from 'rxjs';

import { AuthService } from '../../core/auth/auth.service';
import { AccountService } from '../../core/services/account.service';

@Component({
  selector: 'app-create-account',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
  ],
  templateUrl: './create-account.html',
  styleUrls: ['./create-account.css']
})
export class CreateAccountComponent {

  newUsername: string = '';
  newPassword: string = '';
  baseAmount: number | null = null;
  errorMessage: string = '';

  constructor(
    private router: Router,
    private authService: AuthService,
    private accountService: AccountService
  ) {}

  createAccount(): void {

    this.errorMessage = '';

    if (!this.newUsername || !this.newPassword) {
      this.errorMessage = 'Username and Password are required';
      return;
    }

    if (this.baseAmount === null || this.baseAmount < 1000) {
      this.errorMessage = 'Minimum amount should be Rs.1000';
      return;
    }

    this.authService.register({
      username: this.newUsername,
      password: this.newPassword
    }).pipe(

      switchMap(() =>
        this.accountService.createAccount({
          id: '',
          holderName: this.newUsername,
          balance: this.baseAmount!,
          status: true,
          version: 1
        })
      ),

      catchError((err) => {
        this.errorMessage =
          err?.error?.message || 'Account creation failed';
        return EMPTY;
      })

    ).subscribe((account) => {

      alert(`Account created successfully!\nAccount ID: ${account.id}`);

      this.router.navigate(['/login']);
    });
  }

  goToLogin(): void {
    this.router.navigate(['/login']);
  }
}
