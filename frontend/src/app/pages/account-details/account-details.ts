import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { catchError, EMPTY } from 'rxjs';

import { AccountService } from '../../core/services/account.service';
import { Account } from '../../core/models/account.model';

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './account-details.html',
  styleUrls: ['./account-details.css']
})
export class AccountDetailsComponent implements OnInit {
  user!: Account;

  constructor(private router: Router, private accountService: AccountService) {}

  ngOnInit(): void {
    const loggedInUser = localStorage.getItem('loggedInUser');
    if (!loggedInUser) {
      this.router.navigate(['/login']);
      return;
    }

    const parsedUser = JSON.parse(loggedInUser) as Account;
    this.accountService.getAccountById(parsedUser.id).pipe(
      catchError(() => {
        this.router.navigate(['/login']);
        return EMPTY;
      })
    ).subscribe((account) => {
      this.user = account;
      localStorage.setItem('loggedInUser', JSON.stringify(account));
    });
  }
}
