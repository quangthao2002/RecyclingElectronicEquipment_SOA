import { useDeviceContext } from "@/context/DeviceProvider";
import { Typography } from "antd";
import React from "react";

const { Title, Paragraph, Text } = Typography;

interface IProps {}

const Step3: React.FC<IProps> = ({}) => {
  const { quote } = useDeviceContext();

  return (
    <div>
      <Title level={3}>Hàng của bạn đã đến nơi và đang trong quá trình kiểm tra</Title>

      <div
        style={{
          marginTop: "2rem",
          display: "flex",
          alignItems: "center",
          gap: "1rem",
          justifyContent: "end",
        }}
      ></div>
    </div>
  );
};

export default Step3;
