import { Component } from '@angular/core';
import { Account } from 'src/app/model/Account.model';
import { Transaction } from 'src/app/model/Transaction.model';
import { AccountService } from '../service/account.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
constructor(private accountService: AccountService){

}
Account =new Account();
Transaction=new Transaction();

adddata(){
  console.log(this.Account);
  this.accountService.registerdata(this.Account);
}

}
