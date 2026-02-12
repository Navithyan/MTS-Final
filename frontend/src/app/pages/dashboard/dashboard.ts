import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardComponent implements OnInit {

  user: any;
  balance: number = 0;

  constructor(private router: Router) {}

  ngOnInit(): void {

  const storedUser = localStorage.getItem('loggedInUser');

  if (storedUser) {
    this.user = JSON.parse(storedUser);
    this.balance = this.user.balance;
  } else {
    this.router.navigate(['/login']);
  }
}


  logout() {
    localStorage.removeItem('loggedInUser');   // clear session
    this.router.navigate(['/login']);
  }

}
