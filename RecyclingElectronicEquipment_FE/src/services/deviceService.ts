import { Quote, QuoteResponse, UpdateQuoteStatus } from "@/types/quote";
import axiosClient from "./axiosClient";

const deviceService = {
  createQuote: (quote: Quote): Promise<{ data: QuoteResponse }> => {
    return axiosClient.post(`api/quotes/confirm`, quote);
  },
  updateQuoteStatus: (id: string, data: UpdateQuoteStatus) => {
    return axiosClient.post(`api/quotes/createRecycleReceipt/${id}`, data);
  },
  createRecycleReceipt: (id: string, data: UpdateQuoteStatus) => {
    return axiosClient.post(`api/quotes/createRecycleReceipt`, data);
  },
};

export default deviceService;
