import { Steps } from "antd";
import React from "react";

interface IProps {
  step?: number;
}

const description = "";
const CustomStepAddDevice: React.FC<IProps> = ({ step = 1 }) => {
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
      ]}
    />
  );
};

export default CustomStepAddDevice;
