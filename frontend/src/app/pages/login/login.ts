import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';   // 👈 ADD THIS

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],  // 👈 ADD RouterModule
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {

  username: string = '';
  password: string = '';

  constructor(private router: Router) {}

  login() {

  const storedUser = localStorage.getItem('user');

  if (!storedUser) {
    alert("No account found. Please create account.");
    return;
  }

  const user = JSON.parse(storedUser);

  if (this.username === user.username && this.password === user.password) {

    // Save logged-in user
    localStorage.setItem('loggedInUser', JSON.stringify(user));

    this.router.navigate(['/dashboard']);

  } else {
    alert("Invalid credentials");
  }
}


}
