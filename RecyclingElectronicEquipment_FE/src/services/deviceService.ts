import { CreateRecycleReceipt, Quote, QuoteResponse, UpdateRecycleReceipt } from "@/types/quote";
import axiosClient from "./axiosClient";

const deviceService = {
  createQuote: (quote: Quote): Promise<{ data: QuoteResponse }> => {
    return axiosClient.post(`api/quotes/create`, quote);
  },
  updateRecycleReceipt: (id: string, data: UpdateRecycleReceipt) => {
    return axiosClient.post(`api/quotes/createRecycleReceipt/${id}`, data);
  },
  createRecycleReceipt: (data: CreateRecycleReceipt) => {
    return axiosClient.post(`api/recycling/createRecycleReceipt`, data);
  },
};

export default deviceService;
