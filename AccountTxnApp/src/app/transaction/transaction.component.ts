import { Component } from '@angular/core';
import { TransactionService } from '../service/transaction.service';
import { Account } from '../model/Account.model';
import { Transaction } from '../model/Transaction.model';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent {
  
  constructor(private transactionService: TransactionService){

  }
  Account =new Account();
  Transaction=new Transaction();
  transactionType: string = '';
  addtransactiondata(){
    console.log(this.Transaction);
    this.transactionService.transactiondata(this.Transaction);
  }
  onTransactionTypeChange(type: string) {
    this.transactionType = type;
    if (type === 'credit') {
      this.Transaction.amountDebited != "";
    } else if (type === 'debit') {
      this.Transaction.amountCredited != "";
    }
  }
}
