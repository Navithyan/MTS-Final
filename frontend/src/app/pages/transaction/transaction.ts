import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

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

  constructor(private fb: FormBuilder, private router: Router) {}

  ngOnInit(): void {

    const loggedUser = localStorage.getItem('loggedInUser');

    if (!loggedUser) {
      this.router.navigate(['/login']);
      return;
    }

    const user = JSON.parse(loggedUser);
    this.fromAccount = user.accountId;

    this.transactionForm = this.fb.group({
      toAccount: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(1)]]
    });
  }

  transfer(): void {

  if (this.transactionForm.invalid) {
    alert("Please fill all fields correctly");
    return;
  }

  const loggedUser = JSON.parse(localStorage.getItem('loggedInUser') || '{}');
  const amount = this.transactionForm.value.amount;
  const toAccount = this.transactionForm.value.toAccount;

  if (amount > loggedUser.balance) {
    alert("Insufficient balance");
    return;
  }

  // Deduct balance
  loggedUser.balance -= amount;

  // 🔥 Create transaction object
  const transaction = {
    from: loggedUser.accountId,
    to: toAccount,
    amount: amount,
    date: new Date().toLocaleString()
  };

  // 🔥 Get existing history
  const history = JSON.parse(localStorage.getItem('transactions') || '[]');

  // Add new transaction
  history.push(transaction);

  // Save updated history
  localStorage.setItem('transactions', JSON.stringify(history));

  // Update user data
  localStorage.setItem('loggedInUser', JSON.stringify(loggedUser));
  localStorage.setItem('user', JSON.stringify(loggedUser));

  alert("Money sent successfully!");

  this.router.navigate(['/dashboard']);
}

}
