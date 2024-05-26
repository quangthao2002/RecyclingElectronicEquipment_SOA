export interface Device {
  model: string;
  name: string;
  type: string;
  status: string;
  location: string;
  description: string;
  createdDate: string;
  updatedDate: string;
}

export enum StatusDevice {
  Pending = "Đang chờ",

  Receive = "Đã nhận được thiết bị",
  Assessed = "Đã đánh giá",

  Paid = "Khách xác nhận bán",
  Recycle = "Yêu cầu tái chế",
  Complete = "Hoàn thành",
}

export enum StatusDeviceType {
  Pending = "PENDING",

  Receive = "RECEIVE",
  Assessed = "ASSESSED",

  Paid = "PAID",
  Recycle = "RECYCLE",
  Complete = "COMPLETE",
}
