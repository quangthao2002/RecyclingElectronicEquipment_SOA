import { useDeviceContext } from "@/context/DeviceProvider";
import deviceService from "@/services/deviceService";
import { Button } from "antd";
import React from "react";
import { toast } from "react-toastify";

interface IProps {
  handleNextStep: () => void;
  handlePrevStep: () => void;
}

const Step4: React.FC<IProps> = ({ handleNextStep, handlePrevStep }) => {
  const { quote } = useDeviceContext();
  const disabled = false;

  return (
    <div>
      <div>Hàng của bạn đang được kiểm tra</div>

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

export default Step4;
