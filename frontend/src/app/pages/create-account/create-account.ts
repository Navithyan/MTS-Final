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
    RouterModule   // ✅ REQUIRED for routerLink
  ],
  templateUrl: './create-account.html',
  styleUrls: ['./create-account.css']
})
export class CreateAccountComponent {

  newUsername: string = '';
  newPassword: string = '';
  baseAmount: number = 1000;
  errorMessage: string = '';

  constructor(
    private router: Router,
    private authService: AuthService,
    private accountService: AccountService
  ) {}

  createAccount(): void {

    this.errorMessage = '';

    if (!this.newUsername || !this.newPassword || this.baseAmount < 1000) {
      this.errorMessage = 'Minimum amount should be 1000';
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
          balance: this.baseAmount,
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

      alert(`Account created successfully!
Account ID: ${account.id}`);

      // Clear form
      this.newUsername = '';
      this.newPassword = '';
      this.baseAmount = 1000;

      // Navigate to login
      this.router.navigate(['/login']);
    });
  }

  // ✅ If using (click) instead of routerLink
  goToLogin(): void {
    this.router.navigate(['/login']);
  }
}
