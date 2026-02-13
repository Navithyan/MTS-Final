import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
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
  loading = true;
  currentUserId!: string;

  constructor(
    private router: Router,
    private accountService: AccountService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const loggedInUser = localStorage.getItem('loggedInUser');

    if (!loggedInUser) {
      this.router.navigate(['/login']);
      return;
    }

    const user = JSON.parse(loggedInUser);
    this.currentUserId = user.id;

    this.accountService.getTransactions(this.currentUserId).pipe(
      catchError((error) => {
        console.error("Transaction fetch error:", error);
        return of([]);
      })
    ).subscribe((data) => {
      console.log("Transactions API Response:", data);
      this.transactions = data;
      this.loading = false;
      this.cdr.detectChanges(); // ensure UI refresh
    });
  }

  isCredit(txn: Transaction): boolean {
    return txn.toAccountId === this.currentUserId;
  }

  isDebit(txn: Transaction): boolean {
    return txn.fromAccountId === this.currentUserId;
  }
}
