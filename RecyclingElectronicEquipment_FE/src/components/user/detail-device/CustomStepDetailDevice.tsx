import { Steps } from "antd";
import React from "react";

interface IProps {
  step?: number;
}

const description = "";
const CustomStepDetailDevice: React.FC<IProps> = ({ step = 1 }) => {
  return (
    <Steps
      current={step}
      items={[
        {
          title: "Thông tin thiết bị",
          description,
        },
        {
          title: "Vận chuyển",
          description,
        },
        {
          title: "Nhận hàng & Kiểm tra",
          description,
        },
        {
          title: "Kết quả",
          description,
        },
        {
          title: "Xác nhận",
          description,
        },
      ]}
    />
  );
};

export default CustomStepDetailDevice;
