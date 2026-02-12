import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { AccountService, Transaction } from '../../core/services/account.service';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './history.html',
  styleUrls: ['./history.css']
})
export class HistoryComponent implements OnInit {

  transactions: Transaction[] = [];

  constructor(private router: Router, private accountService: AccountService) {}

  ngOnInit(): void {
    const loggedInUser = localStorage.getItem('loggedInUser');
    if (!loggedInUser) {
      this.router.navigate(['/login']);
      return;
    }

    const user = JSON.parse(loggedInUser);
    this.accountService.getTransactions(user.id).subscribe({
      next: (transactions) => {
        this.transactions = transactions;
      },
      error: () => {
        this.transactions = [];
      }
    });
  }
}
