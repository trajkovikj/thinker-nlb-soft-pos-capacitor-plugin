# Thinker NLB SoftPOS capacitor plugin

This is capacitor plugin to integrate your Hybrid (Ionic) application with SoftPOS application of NLB Bank A.D. Skopje. 
This plugin will allow to issue payment requests as well as refund requests to the NLB SoftPOS application. 
Currently NLB SoftPOS application is only available on Android devices.

## Install

Install from github.com:
```bash
npm install https://github.com/thinker
npx cap sync
```

Install from capacitor store (not yet available):
```bash
npm install thinker-nlb-soft-pos
npx cap sync
```

## Usage

After you installed the plugin and sync with Android platform (`npx cap sync` or `npm run android:sync`)
you need to import the plugin in your javascript code:

```typescript
import { ThinkerNlbSoftPos, TransactionResponse } from 'thinker-nlb-soft-pos';
```

In your function where you want to invoke the plugin you will need to call one of the 2
functions from the plugin:
- `initiatePurchaseTransaction()` - sending purchase (payment) request
- `initiateVoidTransaction()`- sending void (refund) request

Below is a code example of sending a purchase (payment) request:

```typescript

import { ThinkerNlbSoftPos, TransactionResponse } from 'thinker-nlb-soft-pos';

...

public async sendPurchaseRequest(): Promise<void> {
    try {
        const response: TransactionResponse = await ThinkerNlbSoftPos.initiatePurchaseTransaction({
            pin: '1234',
            amount: '100.50',
            packageName: 'com.example.MyApp',
            transactionType: 'POS',
            merchantUniqueID: '123123123',
        });
        
        console.log(response);
    } catch(error) {
        console.error(error);
    }
}
```

### Making purchase (payment) request

Below is an example of making purchase request.

```typescript

import { ThinkerNlbSoftPos, TransactionResponse } from 'thinker-nlb-soft-pos';

...

public async sendPurchaseRequest(): Promise<void> {
    try {
        const response: TransactionResponse = await ThinkerNlbSoftPos.initiatePurchaseTransaction({
            pin: '1234',
            amount: '100.50',
            packageName: 'com.example.MyApp',
            transactionType: 'POS',
            merchantUniqueID: '123123123',
        });
        
        console.log(response);
    } catch(error) {
        console.error(error);
    }
}
```

#### Request parameters

| PARAMETER | MANDATORY | DESCRIPTION                                                                                                                                                        |
| ----------|-----------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| pin       | NO        | This is the PIN code to login to the NLB SoftPOS application. It is not mandatory and if it is not sent it will be asked from the app to login before executing the transaction |
| amount    | YES       | The amount you want to pay. Must be in the following format XXXX or XXXX.YY i.e. maximum 2 decimal places separated by dot (.)                                     |
|packageName| YES       | This is your application package name. This is mandatory because NLB POS app needs to know where to return the response after successful execution                 |
|transactionType   | YES       | Can be one of the 2 following options: 'POS' or 'IPS'                                                                                                              |
|merchantUniqueID       | YES       | This is a reference send from your app, so you can correlate the payment with the transaction                                                                   |


### Making void (refund) request

```typescript

import { ThinkerNlbSoftPos, TransactionResponse } from 'thinker-nlb-soft-pos';

...

public async sendVoidRequest(): Promise<void> {
    try {
        const response: TransactionResponse = await ThinkerNlbSoftPos.initiateVoidTransaction({
            pin: '1234',
            amount: '100.50',
            packageName: 'com.example.MyApp',
            transactionType: 'POS',
            authorizationCode: '1234',
            merchantUniqueID: '123123123',
        });
        
        console.log(response);
    } catch(error) {
        console.error(error);
    }
}
```

#### Request parameters

| PARAMETER | MANDATORY | DESCRIPTION                                                                                                                                                                     |
| ----------|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| pin       | NO        | This is the PIN code to login to the NLB SoftPOS application. It is not mandatory and if it is not sent it will be asked from the app to login before executing the transaction |
| amount    | YES       | The amount you want to pay. Must be in the following format XXXX or XXXX.YY i.e. maximum 2 decimal places separated by dot (.)                                                  |
|packageName| YES       | This is your application package name. This is mandatory because NLB POS app needs to know where to return the response after successful execution                              |
|transactionType   | YES       | Can be one of the 2 following options: 'POS' or 'IPS'                                                                                                                           |
|authorizationCode|YES| This is the code from the PIN slip from the successful transaction. You need to present the same credit card and the authorization code to be able to make the refund           |
|merchantUniqueID       | YES       | This is a reference send from your app, so you can correlate the payment with the transaction                                                                                   |


### Response 

As response you will get an object that looks like the object given below.
In the next 2 sections you can see all status codes and all validation error codes that you may get as a response. You should handle different statuses and validation errors in your application.

```json
{
  "status": "EXECUTED", // listed in the status codes section
  "statusCode": 1000, // listed in the status codes section
  "result": {
    "status": {
      "code": "", // code returned from NLB app
      "message": "", // message returned from NLB app
      "receiptData": "" // pin slip data, if you need to store it or print it
    },
    "paymentIdentificator": "", // this is identification number that you need to use in your Void i.e. refund transactions
  },
  "validationErrors": [
    {
      "message": "Description of validation error", // listed in section for validation error codes
      "errorCode": 1000 // listed in section for validation error codes
    }
  ],
}
```

#### Response status codes

|   CODE    | STATUS | DESCRIPTION                                                                                                                                                                                                                                              |
|-----------|--------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|   EXECUTED    | 1000   | Transaction was successfuly executed                                                                                                                                                                                                                     |
|   REJECTED    | 1001   | Transaction was rejecte or canceled                                                                                                                                                                                                                      |
|   TIMEOUT    | 1002   | Transaction timeout                                                                                                                                                                                                                                      |
|   NETWORK_ERROR    | 1003   | There was network error. Probably lost internet connection                                                                                                                                                                                               |
|   UNKNOWN_ERROR    | 2000   | This status is returned when the plugin did not handle the exception. In most cases this will be thrown when the response from the NLB app is not handled correctly or there was underline API change                                                    |
|   VALIDATION_ERROR    | 2001   | This status is returned when there are validation errors in your request. The plugin pre-validates your request before it is sent to the NLB SoftPOS app. In addition to this status you will have a list of all validation errors that you need to fix. |
|   RESPONSE_JSON_PARSE_ERROR    | 2002   | This status will be returned in cases when the plugin cannot parse the JSON response from the NLB POS app.                                                                                                                                               |
|   MISSING_RESPONSE_DATA    | 2003   | This status is returned if the NLB POS app responded with an empty message                                                                                                                                                                               |
|   NLB_STATUS_CODE_NOT_MAPPED    | 2004   | This status will be returned when the plugin cannot map the NLB POS app status code to the plugin status codes                                                                                                                                           |
|   NLB_APP_START_FAILED    | 2005   | This status will be returned when the plugin is not able to start the NLB POS app. This can happen if the NLB POS app is not installed on the device.                                                                                                    |
|   REQUEST_DATA_MALFORMED    | 2006   | This status will be retured from the plugin if YOU (as the user of the plugin) did not sent correctly formated request JSON object to the plugin                                                                                                         |


#### Validation error codes

|   CODE    |   MESSAGE     |
|-----------|---------------|
| 3000  |  PIN code is optional parameter, but if you decide to send it via request then it must be exactly 4 characters long! |
| 3001  |  Mandatory parameter [amount]! Amount is mandatory parameter! |
| 3002  | Amount parameter must be in one of the 2 following formats [XXXX] or [XXXX.YY]!  |
| 3003  | Mandatory parameter [packageName]! Package name of your application is mandatory field!  |
|  3004 | Mandatory parameter [transactionType]! Transaction type must be either 'POS' or 'IPS'!  |
|  3005 | Mandatory parameter [transactionClass]! Transaction type must be either 'purchase' or 'void'!  |
|  3006 | Parameter [transactionClass] must be of type 'purchase'!  |
|  3007 | Parameter [transactionClass] must be of type 'void'!  |
| 3008  | Mandatory parameter [authorizationCode]! You must send authorization code to make a void transaction!  |
|  3009 | Mandatory parameter [merchantUniqueID]! Merchant unique ID is reference generated by your application to correlate transactions!  |
| 3010  | Request data sent to the ThinkerNlbSoftPos plugin is malformed! Please check your request object and verify it against the plugin documentation!  |


## API

<docgen-index>

* [`echo(...)`](#echo)
* [`initiatePurchaseTransaction(...)`](#initiatepurchasetransaction)
* [`initiateVoidTransaction(...)`](#initiatevoidtransaction)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### initiatePurchaseTransaction(...)

```typescript
initiatePurchaseTransaction(options: PurchaseTransactionOptions) => Promise<TransactionResponse>
```

| Param         | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#purchasetransactionoptions">PurchaseTransactionOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#transactionresponse">TransactionResponse</a>&gt;</code>

--------------------


### initiateVoidTransaction(...)

```typescript
initiateVoidTransaction(options: VoidTransactionOptions) => Promise<TransactionResponse>
```

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#voidtransactionoptions">VoidTransactionOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#transactionresponse">TransactionResponse</a>&gt;</code>

--------------------


### Interfaces


#### TransactionResponse

| Prop                   | Type                                                            |
| ---------------------- | --------------------------------------------------------------- |
| **`status`**           | <code>string</code>                                             |
| **`statusCode`**       | <code>number</code>                                             |
| **`result`**           | <code><a href="#transactionresult">TransactionResult</a></code> |
| **`validationErrors`** | <code>ValidationError[]</code>                                  |


#### TransactionResult

| Prop                       | Type                                                                        |
| -------------------------- | --------------------------------------------------------------------------- |
| **`status`**               | <code><a href="#transactionresultstatus">TransactionResultStatus</a></code> |
| **`paymentIdentificator`** | <code>string</code>                                                         |


#### TransactionResultStatus

| Prop              | Type                |
| ----------------- | ------------------- |
| **`code`**        | <code>string</code> |
| **`message`**     | <code>string</code> |
| **`receiptData`** | <code>string</code> |


#### ValidationError

| Prop           | Type                |
| -------------- | ------------------- |
| **`message`**  | <code>string</code> |
| **`errorCod`** | <code>number</code> |


#### PurchaseTransactionOptions

| Prop                   | Type                |
| ---------------------- | ------------------- |
| **`pin`**              | <code>string</code> |
| **`amount`**           | <code>string</code> |
| **`packageName`**      | <code>string</code> |
| **`transactionType`**  | <code>string</code> |
| **`merchantUniqueID`** | <code>string</code> |


#### VoidTransactionOptions

| Prop                    | Type                |
| ----------------------- | ------------------- |
| **`pin`**               | <code>string</code> |
| **`amount`**            | <code>string</code> |
| **`packageName`**       | <code>string</code> |
| **`transactionType`**   | <code>string</code> |
| **`authorizationCode`** | <code>string</code> |
| **`merchantUniqueID`**  | <code>string</code> |

</docgen-api>
