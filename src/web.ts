import { WebPlugin } from '@capacitor/core';

import type {
  ThinkerNlbSoftPosPlugin,
  PurchaseTransactionOptions,
  VoidTransactionOptions,
  TransactionResponse,
  ValidationError,
} from './definitions';

export class ThinkerNlbSoftPosWeb extends WebPlugin implements ThinkerNlbSoftPosPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async initiatePurchaseTransaction(options: PurchaseTransactionOptions): Promise<TransactionResponse> {
    console.log('ECHO - INITIATE_PURCHASE', options);
    return new NullTransactionResponse('NULL_TRANSACTION_RESPONSE', -1);
  }

  async initiateVoidTransaction(options: VoidTransactionOptions): Promise<TransactionResponse> {
    console.log('ECHO - INITIATE_VOID', options);
    return new NullTransactionResponse('NULL_TRANSACTION_RESPONSE', -1);
  }
}

export class NullTransactionResponse implements TransactionResponse {
  status: string;
  statusCode: number;

  result: any;
  validationErrors: ValidationError[] = [];

  constructor(status: string, statusCode: number) {
    this.status = status;
    this.statusCode = statusCode;
  }
}
