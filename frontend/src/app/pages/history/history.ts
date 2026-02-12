import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { catchError, of } from 'rxjs';

import { AccountService } from '../../core/services/account.service';
import { Transaction } from '../../core/models/account.model';

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
    this.accountService.getTransactions(user.id).pipe(
      catchError(() => of([]))
    ).subscribe((transactions) => {
      this.transactions = transactions;
    });
  }
}
