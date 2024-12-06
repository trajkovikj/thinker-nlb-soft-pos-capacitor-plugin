import { WebPlugin } from '@capacitor/core';

import type {
  ThinkerNlbSoftPosPlugin,
  PurchaseTransactionOptions,
  VoidTransactionOptions,
  TransactionResponse,
} from './definitions';

export class ThinkerNlbSoftPosWeb extends WebPlugin implements ThinkerNlbSoftPosPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async initiatePurchaseTransaction(options: PurchaseTransactionOptions): Promise<TransactionResponse> {
    console.log('ECHO - INITIATE_PURCHASE', options);
    return new NullTransactionResponse(false);
  }

  async initiateVoidTransaction(options: VoidTransactionOptions): Promise<TransactionResponse> {
    console.log('ECHO - INITIATE_VOID', options);
    return new NullTransactionResponse(false);
  }
}

export class NullTransactionResponse implements TransactionResponse {
  isSuccessful: boolean;
  result: any;
  error: any;

  constructor(isSuccessful: boolean, result: any = undefined, error: any = undefined) {
    this.isSuccessful = isSuccessful;
    this.result = result;
    this.error = error;
  }
}
