# thinker-nlb-soft-pos

Capacitor plugin for NLB Soft POS

## Install

```bash
npm install thinker-nlb-soft-pos
npx cap sync
```

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

| Prop               | Type                 |
| ------------------ | -------------------- |
| **`isSuccessful`** | <code>boolean</code> |
| **`result`**       | <code>any</code>     |
| **`error`**        | <code>any</code>     |


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
