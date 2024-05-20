/* eslint-disable @typescript-eslint/no-explicit-any */
import { Button, Form, FormProps, Input } from "antd";
import React from "react";

type FieldType = {
  name: string;
  phone: string;
};

interface IProps {
  handleNextStep: () => void;
  handlePrevStep: () => void;
}

const Step2: React.FC<IProps> = ({ handleNextStep, handlePrevStep }) => {
  const onFinish: FormProps<FieldType>["onFinish"] = (values) => {
    console.log("Success:", values);
    handleNextStep();
  };

  const onFinishFailed: FormProps<FieldType>["onFinishFailed"] = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  return (
    <Form
      labelCol={{ span: 8 }}
      wrapperCol={{ span: 16 }}
      initialValues={{ remember: true }}
      onFinish={onFinish}
      onFinishFailed={onFinishFailed}
      autoComplete="off"
      layout="vertical"
    >
      <Form.Item<FieldType>
        label="Tên khách hàng"
        name="name"
        rules={[{ required: true, message: "Please input your name!" }]}
      >
        <Input />
      </Form.Item>

      <Form.Item<FieldType>
        label="Số điện thoại"
        name="phone"
        rules={[{ required: true, message: "Please input your phone!" }]}
      >
        <Input />
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
        <Button onClick={handlePrevStep} type="primary" ghost>
          Quay lại
        </Button>
        <Button type="primary" htmlType="submit" ghost>
          Tiếp tục
        </Button>
      </div>
    </Form>
  );
};

export default Step2;
