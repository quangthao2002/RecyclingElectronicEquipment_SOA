import { Button, Form, FormProps, InputNumber, Typography } from "antd";
import TextArea from "antd/es/input/TextArea";
import CustomContent from "../common/CustomContent";
import adminService from "@/services/adminService";
import { useParams } from "react-router-dom";
import { toast } from "react-toastify";

const { Title, Paragraph, Text } = Typography;

type FieldType = {
  description: string;
  price: number;
};

const EvaluateDevice = () => {
  const { deviceId = "" } = useParams<{ deviceId: string }>();

  const onFinish: FormProps<FieldType>["onFinish"] = async (values) => {
    try {
      const [res1, res2] = await Promise.all([
        adminService.updatePrice(deviceId, values.price),
        adminService.updateDescription(deviceId, values.description),
      ]);
      if (res1 || res2) {
        toast.success(`Updated success ${deviceId}`);
      } else {
        toast.error("Error EvaluateDevice");
      }
    } catch (error) {
      console.log("error: ", error);
      toast.error("Error EvaluateDevice");
    }
  };

  const onFinishFailed: FormProps<FieldType>["onFinishFailed"] = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  return (
    <>
      <CustomContent>
        <Typography>
          <Title level={2}>Đánh giá thiết bị</Title>
          <Form
            initialValues={{ remember: true }}
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            autoComplete="off"
            layout="vertical"
          >
            <Form.Item label="Ý kiến của thợ">
              <TextArea rows={10} />
            </Form.Item>

            <Form.Item label="Giá trị thiết bị sau khi đánh giá">
              <InputNumber /> VND
            </Form.Item>

            <div
              style={{
                marginTop: "2rem",
                display: "flex",
                alignItems: "center",
                gap: "1rem",
                justifyContent: "end",
              }}
            >
              <Button type="primary" htmlType="submit" ghost>
                Trả kết quả đánh giá cho khách
              </Button>
            </div>
          </Form>
        </Typography>
      </CustomContent>
    </>
  );
};

export default EvaluateDevice;
