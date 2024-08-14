import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Transaction } from '../model/Transaction.model';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  private baseUrl = 'http://localhost:8983';
  constructor(private http: HttpClient) { }

  transactiondata(transaction: Transaction){
    console.log(transaction);
    this.http.post(this.baseUrl+"/addTransactionDetails/"+transaction.accno,transaction).subscribe();
  }
}
