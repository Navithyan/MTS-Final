import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private baseUrl = environment.apiUrl + '/a-api';

  constructor(private http: HttpClient) {}

  getAllAccounts() {
    return this.http.get<any[]>(`${this.baseUrl}/public/accounts`);
  }

  getAccountById(id: string) {
    return this.http.get<any>(`${this.baseUrl}/secure/accounts/${id}`);
  }

  createAccount(data: any) {
    return this.http.post(`${this.baseUrl}/secure/create-account`, data);
  }
}
