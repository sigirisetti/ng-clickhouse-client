import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Currency } from './currency';

import * as globals from '../../globals'

@Injectable({
  providedIn: 'root'
})
export class TfxStaticDataService {

  constructor(private http: HttpClient) { 
  }

  getTfxCurrencies(): Observable<Currency[]> {
    return this.http.get<Currency[]>(globals.restApiBase + "/tfxCurrencies");
  }
}
