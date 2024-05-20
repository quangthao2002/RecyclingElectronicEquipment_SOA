import { useDeviceContext } from "@/context/DeviceProvider";
import { Button } from "antd";
import React from "react";

interface IProps {
  handleNextStep: () => void;
  handlePrevStep: () => void;
}

const Step3: React.FC<IProps> = ({ handleNextStep, handlePrevStep }) => {
  const { quote } = useDeviceContext();
  const disabled = false;

  return (
    <div>
      <div>
        Mã bưu kiện của bạn là: <strong>{quote?.productCode}</strong>
      </div>
      <div>Bưu kiện đang trong trạng thái vận chuyển</div>

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
        <Button onClick={handleNextStep} type="primary" ghost disabled={disabled}>
          Tiếp tục
        </Button>
      </div>
    </div>
  );
};

export default Step3;
