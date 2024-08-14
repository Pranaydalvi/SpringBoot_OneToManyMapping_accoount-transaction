import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Account } from 'src/app/model/Account.model';


@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private baseUrl = 'http://localhost:8983';
  constructor(private http: HttpClient) { }

  registerdata(account: Account){
    console.log("data in account service.ts")
    console.log(account);
    this.http.post(this.baseUrl+"/accountCreate",account).subscribe();
  }

}
