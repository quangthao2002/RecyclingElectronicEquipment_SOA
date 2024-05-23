import { Button, Col, Form, FormProps, InputNumber, Row, Typography } from "antd";
import TextArea from "antd/es/input/TextArea";
import CustomContent from "../common/CustomContent";

const { Title, Paragraph, Text } = Typography;

type FieldType = {
  description: string;
  price: number;
};

const EvaluateDevice = () => {
  const onFinish: FormProps<FieldType>["onFinish"] = (values) => {
    console.log("Success:", values);
  };

  const onFinishFailed: FormProps<FieldType>["onFinishFailed"] = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  return (
    <CustomContent>
      <Typography>
        <Row>
          {/* Device info */}
          <Col span={12}>
            <Title level={2}>Thông tin thiết bị</Title>
            <Paragraph>
              <Text>
                <strong>Tên thiết bị</strong>: samsung
              </Text>
            </Paragraph>
            <Paragraph>
              <Text>
                <strong>Thời gian sử dung</strong>: 1 năm
              </Text>
            </Paragraph>
          </Col>

          {/* Confirm */}
          <Col span={12}>
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
          </Col>
        </Row>
      </Typography>
    </CustomContent>
  );
};

export default EvaluateDevice;
