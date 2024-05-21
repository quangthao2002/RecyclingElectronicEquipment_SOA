import { Button } from "antd";
import React from "react";
import { useNavigate } from "react-router-dom";

interface IProps {
  handlePrevStep: () => void;
}

const Step6: React.FC<IProps> = ({ handlePrevStep }) => {
  const disabled = false;
  const router = useNavigate();

  const handleClick = () => {
    router("/");
  };

  return (
    <div>
      <h1>Yêu cầu thành công !</h1>
      <div>Chúng tôi sẽ thanh toán vào tài khoản momo của bạn</div>

      <div
        style={{
          marginTop: "2rem",
          display: "flex",
          alignItems: "center",
          gap: "1rem",
          justifyContent: "end",
        }}
      >
        <Button onClick={handlePrevStep} type="primary" ghost>
          Quay lại
        </Button>
        <Button onClick={handleClick} type="primary" ghost disabled={disabled}>
          Tiếp tục
        </Button>
      </div>
    </div>
  );
};

export default Step6;
