// import { Component } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { FormsModule } from '@angular/forms'; 
// import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { Router } from '@angular/router';

// @Component({
//   selector: 'app-create-account',
//   standalone: true,
//   imports: [CommonModule, FormsModule],
//   templateUrl: './create-account.html',
//   styleUrls: ['./create-account.css']
// })
// export class CreateAccountComponent {

//   createForm: FormGroup;

//   constructor(private fb: FormBuilder, private router: Router) {

//     this.createForm = this.fb.group({
//       username: ['', Validators.required],
//       password: ['', Validators.required]
//     });

//   }

//   createAccount() {

//     if (this.createForm.invalid) return;

//     const { username, password } = this.createForm.value;

//     const accountId = 'ACC' + Math.floor(100000 + Math.random() * 900000);

//     const newUser = {
//       username: username,
//       password: password,
//       accountId: accountId,
//       balance: 10000,
//       transactions: []
//     };

//     localStorage.setItem('user', JSON.stringify(newUser));

//     alert('Account Created Successfully!\nYour Account ID: ' + accountId);

//     this.router.navigate(['/login']);
//   }

//   goToLogin() {
//     this.router.navigate(['/login']);
//   }

// }

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-create-account',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './create-account.html',
  styleUrls: ['./create-account.css']
})
export class CreateAccountComponent {

  newUsername: string = '';
  newPassword: string = '';
  baseAmount: number = 0;

  constructor(private router: Router) {}

  createAccount() {

  if (this.newUsername && this.newPassword && this.baseAmount >= 0) {

    // 🔥 Generate random 6-digit account ID
    const accountId = Math.floor(100000 + Math.random() * 900000);

    const userData = {
      username: this.newUsername,
      password: this.newPassword,
      balance: this.baseAmount,
      accountId: accountId
    };

    localStorage.setItem('user', JSON.stringify(userData));

    alert("Account created successfully!");

    this.router.navigate(['/login']);

  } else {
    alert("Please fill all fields correctly");
  }
}



}
