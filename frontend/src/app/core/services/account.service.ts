import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { ApiResponse } from '../models/api-response.model';
import { Account, TransferPayload, Transaction } from '../models/account.model';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private baseUrl = environment.accountApiUrl;

  constructor(private http: HttpClient) {}

  getAllAccounts(): Observable<Account[]> {
    return this.http
      .get<ApiResponse<Account[]>>(`${this.baseUrl}/public/accounts`)
      .pipe(map((response) => response.data));
  }

  getAccountById(id: string): Observable<Account> {
    return this.http
      .get<ApiResponse<Account>>(`${this.baseUrl}/secure/accounts/${id}`)
      .pipe(map((response) => response.data));
  }

  createAccount(data: Account): Observable<Account> {
    return this.http
      .post<ApiResponse<Account>>(`${this.baseUrl}/secure/create-account`, data)
      .pipe(map((response) => response.data));
  }

  transfer(data: TransferPayload): Observable<Transaction> {
    return this.http
      .post<ApiResponse<Transaction>>(`${this.baseUrl}/secure/transfer`, data)
      .pipe(map((response) => response.data));
  }

  getTransactions(accountId: string): Observable<Transaction[]> {
    return this.http
      .get<ApiResponse<Transaction[]>>(`${this.baseUrl}/secure/accounts/${accountId}/transactions`)
      .pipe(map((response) => response.data));
  }
}
