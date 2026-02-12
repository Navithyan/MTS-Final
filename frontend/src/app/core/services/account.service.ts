import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

export interface Account {
  id: string;
  holderName: string;
  balance: number;
  status: boolean;
  version: number;
  lastUpdated?: string;
}

export interface TransferPayload {
  fromAccountId: string;
  toAccountId: string;
  amount: number;
}

export interface Transaction {
  fromAccountId: string;
  toAccountId: string;
  amount: number;
  status: string;
  message: string;
  createdOn: string;
}

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private baseUrl = environment.apiUrl + '/a-api';

  constructor(private http: HttpClient) {}

  getAllAccounts() {
    return this.http.get<Account[]>(`${this.baseUrl}/public/accounts`);
  }

  getAccountById(id: string) {
    return this.http.get<Account>(`${this.baseUrl}/secure/accounts/${id}`);
  }

  createAccount(data: Account) {
    return this.http.post<Account>(`${this.baseUrl}/secure/create-account`, data);
  }

  transfer(data: TransferPayload) {
    return this.http.post<Transaction>(`${this.baseUrl}/secure/transfer`, data);
  }

  getTransactions(accountId: string) {
    return this.http.get<Transaction[]>(`${this.baseUrl}/secure/accounts/${accountId}/transactions`);
  }
}
