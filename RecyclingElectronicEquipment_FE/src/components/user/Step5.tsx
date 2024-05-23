import { useDeviceContext } from "@/context/DeviceProvider";
import { Button } from "antd";
import React from "react";

interface IProps {
  handleNextStep: () => void;
  handlePrevStep: () => void;
}

const Step5: React.FC<IProps> = ({ handleNextStep, handlePrevStep }) => {
  const { quote } = useDeviceContext();
  const disabled = false;

  // const handleSubmit = async () => {
  //   const values = {
  //     deviceId: 1,
  //     quoteId: 1,
  //     paymentMethod: "Momo",
  //   };
  //   try {
  //     const res = await deviceService.createRecycleReceipt(values);

  //     console.log(res);
  //     if (res && res.data) {
  //       handleNextStep();
  //     }
  //   } catch (error) {
  //     console.log("error: ", error);
  //     toast.error("Error Step1");
  //   }
  // };

  return (
    <div>
      <div>
        Sau khi đơn vị kiểm tra và đánh giá lại thiết bị, chúng tôi đề xuất với bạn mức giá{" "}
        <strong>{quote?.finalQuotePrice} vnd</strong>
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

export default Step5;
