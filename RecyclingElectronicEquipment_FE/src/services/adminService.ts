import { StatusDeviceType } from "@/types/device";
import axiosClient from "./axiosClient";

const adminService = {
  getListWaitingForDevice: (): Promise<{ data: any }> => {
    return axiosClient.get(`api/recycling/recyclingReceipts`);
  },
  getListInProcess: (): Promise<{ data: any }> => {
    return axiosClient.get(`api/recycling/processing`);
  },
  getListPayment: (): Promise<{ data: any }> => {
    return axiosClient.get(`api/recycling/paid`);
  },
  getListRecycle: (): Promise<{ data: any }> => {
    return axiosClient.get(`api/recycling/recyclingReceipts`);
  },
  getDataReport: (): Promise<{ data: any }> => {
    return axiosClient.get(`api/recycling/report`);
  },
  getDetailDevice: (id: string) => {
    return axiosClient.get(`api/recycling/staff/recyclingReceipts/${id}`);
  },
  updatePrice: (id: string, data: number) => {
    return axiosClient.put(`api/recycling/recyclingReceipts/${id}`, {
      finalQuotePrice: data,
      recyclingReceiptStatus: StatusDeviceType.Assessed,
    });
  },
  updateDescription: (id: string, data: any) => {
    return axiosClient.put(`api/devices/${id}`, data);
  },

  updateToReceive: (id: string, price: number) => {
    return axiosClient.put(`api/recycling/recyclingReceipts/${id}`, {
      finalQuotePrice: price,
      recyclingReceiptStatus: StatusDeviceType.Receive,
    });
  },

  updateToRecycle: (id: string, price: number) => {
    return axiosClient.put(`api/recycling/recyclingReceipts/${id}`, {
      finalQuotePrice: price,
      recyclingReceiptStatus: StatusDeviceType.Recycle,
    });
  },
  updateToComplete: (id: string, price: number) => {
    return axiosClient.put(`api/recycling/recyclingReceipts/${id}`, {
      finalQuotePrice: price,
      recyclingReceiptStatus: StatusDeviceType.Complete,
    });
  },
};

export default adminService;
