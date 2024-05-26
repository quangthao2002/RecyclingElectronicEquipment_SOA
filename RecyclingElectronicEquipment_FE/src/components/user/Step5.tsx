import { Button, Typography } from "antd";
import React from "react";
import { useNavigate } from "react-router-dom";

const { Title, Paragraph, Text } = Typography;

interface IProps {}

const Step5: React.FC<IProps> = ({}) => {
  const disabled = false;
  const router = useNavigate();

  const handleClick = () => {
    router("/");
  };

  return (
    <div>
      <Title level={3}>Yêu cầu thành công !</Title>
      <Title level={3}>Chúng tôi sẽ thanh toán vào tài khoản momo của bạn</Title>

      <div
        style={{
          marginTop: "2rem",
          display: "flex",
          alignItems: "center",
          gap: "1rem",
          justifyContent: "end",
        }}
      >
        <Button onClick={handleClick} type="primary" ghost disabled={disabled}>
          Về trang chủ
        </Button>
      </div>
    </div>
  );
};

export default Step5;
