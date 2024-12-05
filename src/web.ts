import { WebPlugin } from '@capacitor/core';

import type { ThinkerNlbSoftPosPlugin } from './definitions';

export class ThinkerNlbSoftPosWeb extends WebPlugin implements ThinkerNlbSoftPosPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async initiatePurchaseTransaction(options: {
    pin: string,
    amount: string,
    packageName: string,
    transactionType: string,
    transactionClass: string,
    merchantUniqueID: string,
  }): Promise<any> {
    console.log('ECHO - INITIATE_PURCHASE', options);
    return options;
  }

  async initiateVoidTransaction(options: {
    pin: string,
    amount: string,
    packageName: string,
    transactionType: string,
    transactionClass: string,
    authorizationCode: string,
    merchantUniqueID: string,
  }): Promise<any> {
    console.log('ECHO - INITIATE_VOID', options);
    return options;
  }
}
