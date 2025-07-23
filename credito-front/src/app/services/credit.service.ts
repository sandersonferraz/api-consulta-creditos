import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { CONSTS } from '../consts/consts';
import { ICreditoResponse } from '../models/credito-response.model';

@Injectable({
  providedIn: 'root',
})
export class CreditService {
  private readonly apiUrl = CONSTS.API_URL;
  constructor(private http: HttpClient) {}

  getByNumeroNfse(numeroNfse: string): Observable<ICreditoResponse[]> {
    return this.http.get<ICreditoResponse[]>(`${this.apiUrl}/${numeroNfse}`);
  }

  getByNumeroCredito(numeroCredito: string): Observable<ICreditoResponse> {
    return this.http.get<ICreditoResponse>(`${this.apiUrl}/credito/${numeroCredito}`);
  }
}
