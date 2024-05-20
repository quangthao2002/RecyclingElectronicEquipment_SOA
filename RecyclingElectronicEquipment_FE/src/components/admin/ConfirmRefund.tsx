import { Button, Col, Form, FormProps, Row, Typography } from "antd";
import CustomContent from "../common/CustomContent";

const { Title, Paragraph, Text } = Typography;

type FieldType = {};

const ConfirmRefund = () => {
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
          {/* User info */}
          <Col span={12}>
            <Title level={2}>Thông tin khách hàng</Title>
            <Paragraph>
              <Text>
                <strong>Tên</strong>: Le Dav
              </Text>
            </Paragraph>
            <Paragraph>
              <Text>
                <strong>Số tiền cần hoàn</strong>: 1243234
              </Text>
            </Paragraph>
          </Col>

          {/* Confirm */}
          <Col span={12}>
            <Form
              initialValues={{ remember: true }}
              onFinish={onFinish}
              onFinishFailed={onFinishFailed}
              autoComplete="off"
              layout="vertical"
            >
              <div
                style={{
                  marginTop: "2rem",
                  display: "flex",
                  alignItems: "center",
                  gap: "1rem",
                  justifyContent: "end",
                }}
              >
                <Button type="primary" htmlType="submit">
                  Hoàn tiền
                </Button>
              </div>
            </Form>
          </Col>
        </Row>
      </Typography>
    </CustomContent>
  );
};

export default ConfirmRefund;
