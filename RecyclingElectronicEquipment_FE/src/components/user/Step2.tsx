import { useDeviceContext } from "@/context/DeviceProvider";
import deviceService from "@/services/deviceService";
import { Button } from "antd";
import React from "react";
import { toast } from "react-toastify";

interface IProps {
  handleNextStep: () => void;
  handlePrevStep: () => void;
}

const Step2: React.FC<IProps> = ({ handleNextStep, handlePrevStep }) => {
  const { quote } = useDeviceContext();
  const disabled = false;

  const handleSubmit = async () => {
    const values = {
      deviceId: 1,
      quoteId: 1,
      paymentMethod: "Momo",
    };
    try {
      const res = await deviceService.createRecycleReceipt(values);

      console.log(res);
      if (res && res.data) {
        handleNextStep();
      }
    } catch (error) {
      console.log("error: ", error);
      toast.error("Error Step1");
    }
  };

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
        <Button onClick={handleSubmit} type="primary" ghost disabled={disabled}>
          Xác nhận vận chuyển
        </Button>
      </div>
    </div>
  );
};

export default Step2;
