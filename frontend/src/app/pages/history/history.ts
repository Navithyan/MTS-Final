import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './history.html',
  styleUrls: ['./history.css']
})
export class HistoryComponent implements OnInit {

  transactions: any[] = [];

  ngOnInit(): void {
    this.transactions = JSON.parse(localStorage.getItem('transactions') || '[]');
  }
}
