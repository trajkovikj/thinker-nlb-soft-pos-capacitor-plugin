export interface ThinkerNlbSoftPosPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;

  initiatePurchaseTransaction(options: PurchaseTransactionOptions): Promise<TransactionResponse>;

  initiateVoidTransaction(options: VoidTransactionOptions): Promise<TransactionResponse>;
}

export interface PurchaseTransactionOptions {
  pin: string | undefined,
  amount: string,
  packageName: string,
  transactionType: string,
  merchantUniqueID: string,
}

export interface VoidTransactionOptions {
  pin: string | undefined,
  amount: string,
  packageName: string,
  transactionType: string,
  authorizationCode: string,
  merchantUniqueID: string,
}

export interface TransactionResponse {
  isSuccessful: boolean;
  result: any;
  error: any;
}
