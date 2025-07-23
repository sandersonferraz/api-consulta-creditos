import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

import { ICreditoResponse } from './models/credito-response.model';
import { CreditService } from './services/credit.service';
import { map, Observable } from 'rxjs';
import { IExceptionDetails } from './models/exception-details.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  searchForm: FormGroup;
  creditos: ICreditoResponse[] = [];
  loading = false;
  errorMessage = '';
  searchPerformed = false;

  constructor(private fb: FormBuilder, private creditService: CreditService) {
    this.searchForm = this.fb.group({
      searchType: ['nfse', Validators.required],
      searchValue: ['', [Validators.required, Validators.minLength(1)]],
    });
  }

  onSearch(): void {
    if (this.searchForm.valid) {
      this.loading = true;
      this.errorMessage = '';
      this.searchPerformed = true;
      this.creditos = [];

      const searchType = this.searchForm.get('searchType')?.value;
      const searchValue = this.searchForm.get('searchValue')?.value.trim();

      let searchObservable: Observable<ICreditoResponse[]>;

      if (searchType === 'nfse') {
        searchObservable = this.creditService.getByNumeroNfse(searchValue);
      } else {
        searchObservable = this.creditService
          .getByNumeroCredito(searchValue)
          .pipe(map((result: ICreditoResponse) => [result]));
      }

      searchObservable.subscribe({
        next: (response: ICreditoResponse[]) => {
          this.loading = false;
          this.creditos = response;
        },
        error: (error: any) => {
          this.loading = false;
          this.creditos = [];

          try {
            const exceptionDetails = error.error as IExceptionDetails;

            this.errorMessage = exceptionDetails.message || 'Erro inesperado.';
            if (exceptionDetails.details) {
              this.errorMessage += ` (${exceptionDetails.details})`;
            }
          } catch (e) {
            this.errorMessage =
              'Erro desconhecido. Tente novamente mais tarde.';
          }
        },
      });
    }
  }

  formatDate(dateString: string): string {
    try {
      const date = new Date(dateString);
      return date.toLocaleDateString('pt-BR');
    } catch {
      return dateString;
    }
  }

  formatCurrency(value: number): string {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(value);
  }

  getBadgeClass(tipoCredito: string): string {
    switch (tipoCredito.toUpperCase()) {
      case 'ISSQN':
        return 'badge-primary';
      case 'OUTROS':
        return 'badge-warning';
      default:
        return 'badge-secondary';
    }
  }

  trackByNumeroCredito(index: number, credito: ICreditoResponse): string {
    return credito.numeroCredito;
  }
}
