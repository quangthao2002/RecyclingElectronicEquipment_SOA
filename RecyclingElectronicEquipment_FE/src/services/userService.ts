import { StatusDeviceType } from "@/types/device";
import { Quote, QuoteResponse } from "@/types/quote";
import axiosClient from "./axiosClient";

const userService = {
  getList: (id: string) => {
    return axiosClient.get(`api/quotes/users/${id}/quotes`);
  },
  step1: (quote: Quote): Promise<{ data: QuoteResponse }> => {
    return axiosClient.post(`api/quotes/create`, quote);
  },
  step2: (id: string, data: any) => {
    return axiosClient.post(`api/recycling/createRecycleReceipt/${id}`, data);
  },
  getDetailStatus: (id: string) => {
    return axiosClient.get(`api/quotes/${id}`);
  },
  cancel: (id: string) => {
    return axiosClient.post(`api/quotes/cancelQuote/${id}`);
  },
  sell: (id: string, price: number) => {
    return axiosClient.put(`api/recycling/recyclingReceipts/${id}`, {
      finalQuotePrice: price,
      recyclingReceiptStatus: StatusDeviceType.Paid,
    });
  },
};

export default userService;
