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
  transactionId: string;
  fromAccountId: string;
  toAccountId: string;
  amount: number;
  status: boolean;
  failureReason: string;
  createdOn: string;
}
