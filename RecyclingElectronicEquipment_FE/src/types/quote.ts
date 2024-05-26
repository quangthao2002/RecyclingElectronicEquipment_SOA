export enum ReceiptStatus {
  PENDING = "PENDING",
  CONFIRMED = "CONFIRMED",
  ACCESSED = "ACCESSED",
  COMPLETED = "COMPLETED",
  CANCELLED = "CANCELLED",
  PAID = "PAID",
}
export interface Quote {
  model: string;
  deviceAge: number;
  initialPrice: number;
  deviceStatus: string;
  deviceType: string;
  damageLocation: string;
  damageDescription: string;
}

export interface QuoteResponse {
  quoteId: string;
  deviceType: string;
  estimatedPrice: string;
  finalQuotePrice: number;
  model: string;
  productCode: string;
  quoteStatus: string;
  recyclingReceiptStatus: ReceiptStatus;
  createAt: Date;
}

export interface CreateRecycleReceipt {
  deviceId: number;
  quoteId: number;
  paymentMethod: string;
}

export interface UpdateRecycleReceipt {
  finalQuotePrice: number;
  recyclingReceiptStatus: ReceiptStatus;
}
