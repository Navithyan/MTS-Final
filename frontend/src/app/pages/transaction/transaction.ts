import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AccountService } from '../../core/services/account.service';
import { Account } from '../../core/models/account.model';

@Component({
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
  ],
  templateUrl: './transaction.html',
  styleUrls: ['./transaction.css']
})
export class TransactionComponent implements OnInit {

  user!: Account;

  toAccountId: string = '';
  amount: number | null = null;

  receiverName: string = '';
  showConfirmation: boolean = false;

  constructor(
    private router: Router,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {

    const storedUser = localStorage.getItem('loggedInUser');

    if (!storedUser) {
      this.router.navigate(['/login']);
      return;
    }

    this.user = JSON.parse(storedUser);
  }

  // 🔹 Fetch receiver name when account ID entered
  fetchReceiver(): void {

    if (!this.toAccountId) {
      this.receiverName = '';
      return;
    }

    this.accountService.getAccountById(this.toAccountId)
      .subscribe({
        next: (account) => {
          this.receiverName = account.holderName;
        },
        error: () => {
          this.receiverName = 'Invalid Account ID';
        }
      });
  }

  // 🔹 Show confirmation box
  prepareTransfer(): void {

    if (!this.toAccountId || !this.amount || this.amount <= 0) {
      alert('Enter valid details');
      return;
    }

    if (!this.receiverName || this.receiverName === 'Invalid Account ID') {
      alert('Invalid receiver account');
      return;
    }

    this.showConfirmation = true;
  }

  // 🔹 Final transfer happens here
  confirmTransfer(): void {

    this.accountService.transfer({
      fromAccountId: this.user.id,
      toAccountId: this.toAccountId,
      amount: this.amount!
    }).subscribe({
      next: () => {
        alert('Transfer Successful');
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        alert('Transfer Failed');
      }
    });
  }

  cancelTransfer(): void {
    this.showConfirmation = false;
  }
}
