export interface ThinkerNlbSoftPosPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;

  initiatePurchaseTransaction(options: PurchaseTransactionOptions): Promise<TransactionResponse>;

  initiateVoidTransaction(options: VoidTransactionOptions): Promise<TransactionResponse>;
}

export interface PurchaseTransactionOptions {
  pin: string | undefined;
  amount: string;
  packageName: string;
  transactionType: string;
  merchantUniqueID: string;
}

export interface VoidTransactionOptions {
  pin: string | undefined;
  amount: string;
  packageName: string;
  transactionType: string;
  authorizationCode: string;
  merchantUniqueID: string;
}

export interface TransactionResponse {
  status: string;
  statusCode: number;

  result: TransactionResult;
  validationErrors: ValidationError[];
}

export interface TransactionResult {
  status: TransactionResultStatus;
  paymentIdentificator: string;
}

export interface TransactionResultStatus {
  code: string;
  message: string;
  receiptData: string;
}

export interface ValidationError {
  message: string;
  errorCode: number;
}
