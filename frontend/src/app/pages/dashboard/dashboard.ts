import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { catchError, EMPTY } from 'rxjs';

import { AccountService } from '../../core/services/account.service';
import { AuthService } from '../../core/auth/auth.service';
import { Account } from '../../core/models/account.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardComponent implements OnInit {

  user!: Account;
  balance: number = 0;

  constructor(
    private router: Router,
    private accountService: AccountService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const storedUser = localStorage.getItem('loggedInUser');

    if (!storedUser) {
      this.router.navigate(['/login']);
      return;
    }

    this.user = JSON.parse(storedUser);
    this.accountService.getAccountById(this.user.id).pipe(
      catchError(() => {
        this.router.navigate(['/login']);
        return EMPTY;
      })
    ).subscribe((account) => {
      this.user = account;
      this.balance = account.balance;
      localStorage.setItem('loggedInUser', JSON.stringify(account));
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
