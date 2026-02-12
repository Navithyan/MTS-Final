import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { Account, AccountService } from '../../core/services/account.service';

@Component({
  selector: 'app-transaction',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './transaction.html',
  styleUrls: ['./transaction.css']
})
export class TransactionComponent implements OnInit {

  transactionForm!: FormGroup;
  fromAccount: string = '';
  loggedUser!: Account;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    const loggedUser = localStorage.getItem('loggedInUser');

    if (!loggedUser) {
      this.router.navigate(['/login']);
      return;
    }

    this.loggedUser = JSON.parse(loggedUser);
    this.fromAccount = this.loggedUser.id;

    this.transactionForm = this.fb.group({
      toAccount: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(1)]]
    });
  }

  transfer(): void {
    if (this.transactionForm.invalid) {
      alert('Please fill all fields correctly');
      return;
    }

    const amount = Number(this.transactionForm.value.amount);
    const toAccountId = this.transactionForm.value.toAccount;

    this.accountService.transfer({
      fromAccountId: this.loggedUser.id,
      toAccountId,
      amount
    }).subscribe({
      next: () => {
        alert('Money sent successfully!');
        this.accountService.getAccountById(this.loggedUser.id).subscribe((updatedAccount) => {
          localStorage.setItem('loggedInUser', JSON.stringify(updatedAccount));
          this.router.navigate(['/dashboard']);
        });
      },
      error: (err) => {
        alert(err?.error?.message || 'Transfer failed');
      }
    });
  }
}
