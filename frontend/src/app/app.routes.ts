import { Routes } from '@angular/router';
import { CreateAccountComponent } from './pages/create-account/create-account';
import { LoginComponent } from './pages/login/login';
import { DashboardComponent } from './pages/dashboard/dashboard';
import { AccountDetailsComponent } from './pages/account-details/account-details';
import { TransactionComponent } from './pages/transaction/transaction';
import { HistoryComponent } from './pages/history/history';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },   // 👈 SHOW LOGIN FIRST
  { path: 'login', component: LoginComponent },
  { path: 'create-account', component: CreateAccountComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'account-details', component: AccountDetailsComponent },
  { path: 'transaction', component: TransactionComponent },
  { path: 'history', component: HistoryComponent },
  { path: '**', redirectTo: 'login' }   // fallback route
];
