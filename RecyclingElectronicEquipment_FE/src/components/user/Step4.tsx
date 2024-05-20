import { Button } from "antd";
import React from "react";

interface IProps {
  handleNextStep: () => void;
  handlePrevStep: () => void;
}

const Step4: React.FC<IProps> = ({ handleNextStep, handlePrevStep }) => {
  const disabled = false;

  return (
    <div>
      <div>
        Sau khi đơn vị kiểm tra và đánh giá lại thiết bị, chúng tôi đề xuất với bạn mức giá{" "}
        <strong>10 usd</strong>
      </div>

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
          Hủy
        </Button>
        <Button onClick={handleNextStep} type="primary" ghost disabled={disabled}>
          Chấp nhận bán
        </Button>
      </div>
    </div>
  );
};

export default Step4;
