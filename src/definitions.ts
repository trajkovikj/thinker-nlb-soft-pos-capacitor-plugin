export interface ThinkerNlbSoftPosPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;

  initiatePurchaseTransaction(options: {
    pin: string,
    amount: string,
    packageName: string,
    transactionType: string,
    transactionClass: string,
    merchantUniqueID: string,
  }): Promise<any>;

  initiateVoidTransaction(options: {
    pin: string,
    amount: string,
    packageName: string,
    transactionType: string,
    transactionClass: string,
    authorizationCode: string,
    merchantUniqueID: string,
  }): Promise<any>;
}
