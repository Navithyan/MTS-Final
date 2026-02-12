import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { switchMap } from 'rxjs';

import { AuthService } from '../../core/auth/auth.service';
import { AccountService } from '../../core/services/account.service';

@Component({
  selector: 'app-create-account',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './create-account.html',
  styleUrls: ['./create-account.css']
})
export class CreateAccountComponent {

  newUsername: string = '';
  newPassword: string = '';
  baseAmount: number = 1000;

  constructor(
    private router: Router,
    private authService: AuthService,
    private accountService: AccountService
  ) {}

  createAccount() {
    if (!this.newUsername || !this.newPassword || this.baseAmount < 1000) {
      alert('Please provide username/password and minimum amount of 1000');
      return;
    }

    const accountId = `ACC${Math.floor(100000 + Math.random() * 900000)}`;

    this.authService.register({
      username: this.newUsername,
      password: this.newPassword
    }).pipe(
      switchMap(() => this.accountService.createAccount({
        id: accountId,
        holderName: this.newUsername,
        balance: this.baseAmount,
        status: true,
        version: 1
      }))
    ).subscribe({
      next: () => {
        alert(`Account created successfully! Your Account ID: ${accountId}`);
        this.router.navigate(['/login']);
      },
      error: (err) => {
        alert(err?.error?.message || 'Account creation failed');
      }
    });
  }
}
