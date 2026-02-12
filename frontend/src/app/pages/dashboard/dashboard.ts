import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';

import { Account, AccountService } from '../../core/services/account.service';
import { AuthService } from '../../core/auth/auth.service';

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
    this.accountService.getAccountById(this.user.id).subscribe({
      next: (account) => {
        this.user = account;
        this.balance = account.balance;
        localStorage.setItem('loggedInUser', JSON.stringify(account));
      },
      error: () => this.router.navigate(['/login'])
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
