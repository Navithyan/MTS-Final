import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './account-details.html',
  styleUrls: ['./account-details.css']
})
export class AccountDetailsComponent {
  user = JSON.parse(localStorage.getItem('user') || '{}');
}
