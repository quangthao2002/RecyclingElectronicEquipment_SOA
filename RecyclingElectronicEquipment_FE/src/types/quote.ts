type QuoteStatus = "PENDING" | "CONFIRMED" | "ACCEPTED" | "COMPLETED" | "CANCELLED" | "PAID";

export interface Quote {
  model: string;
  deviceAge: number;
  initialPrice: number;
  deviceStatus: string;
  damageLocation: string;
  damageDescription: string;
}

export interface UpdateQuoteStatus {
  finalQuotePrice: string;
  recyclingReceiptStatus: QuoteStatus;
}

export interface QuoteResponse {
  quoteId: string;
  estimatedPrice: string;
  finalQuotePrice: number;
  model: string;
  productCode: string;
  quoteStatus: string;
  recyclingReceiptStatus: QuoteStatus;
  createAt: Date;
}
