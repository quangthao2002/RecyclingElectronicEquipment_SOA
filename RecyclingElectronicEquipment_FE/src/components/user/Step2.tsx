import { useDeviceContext } from "@/context/DeviceProvider";
import userService from "@/services/userService";
import { Button, Typography } from "antd";
import React from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const { Title, Paragraph, Text } = Typography;

interface IProps {
  isTransport?: boolean;
  handlePrevStep: () => void;
}

const Step2: React.FC<IProps> = ({ isTransport, handlePrevStep }) => {
  const disabled = false;
  const { quote } = useDeviceContext();
  const router = useNavigate();

  const handleSubmit = async () => {
    const values = {
      deviceId: 1,
      quoteId: 1,
      paymentMethod: "Momo",
    };
    try {
      const res = await userService.step2(quote?.quoteId as string, 3);
      console.log(res);
      if (res && res.data) {
        toast.success("Xác nhận vận chuyển thành công!");
        router("/");
      } else {
        toast.error("Error Step2");
      }
    } catch (error) {
      console.log("error: ", error);
      toast.error("Error Step2");
    }
  };

  return (
    <div>
      <Title level={3}>
        Mã bưu kiện của bạn là: <strong>SADFASDF{quote?.productCode}</strong>
      </Title>

      {isTransport ? (
        <Title level={3}>Bưu kiện đang trong trạng thái vận chuyển</Title>
      ) : (
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
      )}
    </div>
  );
};

export default Step2;
