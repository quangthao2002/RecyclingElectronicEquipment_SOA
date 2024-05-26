import { useDeviceContext } from "@/context/DeviceProvider";
import userService from "@/services/userService";
import { Button, Typography } from "antd";
import React from "react";
import { toast } from "react-toastify";

const { Title, Paragraph, Text } = Typography;

interface IProps {
  handleNextStep: () => void;
}

const Step4: React.FC<IProps> = ({ handleNextStep }) => {
  const { quote } = useDeviceContext();
  const disabled = false;

  const handleSell = async () => {
    try {
      const res = await userService.sell(quote?.quoteId as string, Number(quote?.estimatedPrice));
      console.log(res);
      if (res && res.data) {
        handleNextStep();
      }
    } catch (error) {
      console.log("error: ", error);
      toast.error("Error Step4");
    }
  };

  const handleCancel = async () => {
    try {
      const res = await userService.cancel("");
      console.log(res);
      if (res && res.data) {
        handleNextStep();
      }
    } catch (error) {
      console.log("error: ", error);
      toast.error("Error Step4");
    }
  };

  return (
    <div>
      <Title level={3}>
        Sau khi đơn vị kiểm tra và đánh giá lại thiết bị, chúng tôi đề xuất với bạn mức giá{" "}
        <strong>100.000{quote?.finalQuotePrice} vnd</strong>
      </Title>

      <div
        style={{
          marginTop: "2rem",
          display: "flex",
          alignItems: "center",
          gap: "1rem",
          justifyContent: "end",
        }}
      >
        <Button onClick={handleCancel} type="primary" ghost>
          Hủy
        </Button>
        <Button onClick={handleSell} type="primary" ghost disabled={disabled}>
          Chấp nhận bán
        </Button>
      </div>
    </div>
  );
};

export default Step4;
