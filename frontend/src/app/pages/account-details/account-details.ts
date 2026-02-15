import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AccountService } from '../../core/services/account.service';
import { Account } from '../../core/models/account.model';

@Component({
  standalone: true,
  imports: [
    CommonModule,
    RouterModule   // ✅ REQUIRED FOR routerLink
  ],
  templateUrl: './account-details.html',
  styleUrls: ['./account-details.css']
})
export class AccountDetailsComponent implements OnInit {

  user: Account = {
    id: '',
    holderName: '',
    balance: 0,
    status: false,
    version: 0
  };

  loading = true;

  constructor(
    private router: Router,
    private accountService: AccountService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    const storedUser = localStorage.getItem('loggedInUser');

    if (!storedUser) {
      this.router.navigate(['/login']);
      return;
    }

    const parsedUser: Account = JSON.parse(storedUser);

    this.accountService.getAccountById(parsedUser.id)
      .subscribe({
        next: (account) => {

          console.log("API RESPONSE:", account);

          this.user = account;
          this.loading = false;

          localStorage.setItem('loggedInUser', JSON.stringify(account));

          this.cdr.detectChanges();
        },
        error: (err) => {
          console.error("Fetch failed", err);
          this.loading = false;
        }
      });
  }

  // ✅ OPTIONAL SAFE METHOD (if you prefer click instead of routerLink)
  goBack(): void {
    this.router.navigate(['/dashboard']);
  }
}
