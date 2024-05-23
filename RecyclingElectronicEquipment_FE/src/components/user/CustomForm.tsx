/* eslint-disable @typescript-eslint/no-explicit-any */
import { Button, Form, FormProps, Radio, Select } from "antd";
import React, { useState } from "react";
import CustomModal from "./CustomModal";

const { Option } = Select;

type FieldType = {
  name: string;
  year: string;
  screen: string;
};

interface IProps {
  handleNextStep: () => void;
}

const CustomForm: React.FC<IProps> = ({ handleNextStep }) => {
  const [form] = Form.useForm();
  const [open, setOpen] = useState(false);

  const showModal = () => {
    setOpen(true);
  };

  const onNameChange = (value: string) => {
    form.setFieldsValue({ name: value });
  };

  const onTimeUseChange = (value: string) => {
    form.setFieldsValue({ time: value });
  };

  const onFinish: FormProps<FieldType>["onFinish"] = (values) => {
    console.log("Success:", values);
    showModal();
  };

  const onFinishFailed: FormProps<FieldType>["onFinishFailed"] = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  return (
    <>
      <CustomModal open={open} setOpen={setOpen} handleNextStep={handleNextStep} />
      <Form
        labelCol={{ span: 8 }}
        wrapperCol={{ span: 16 }}
        initialValues={{ remember: true }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
        layout="vertical"
      >
        <Form.Item
          label="Tên thiết bị"
          name={"name"}
          rules={[{ required: true, message: "Please select your device name!" }]}
        >
          <Select placeholder="Chọn tên thiêt bị" onChange={onNameChange} allowClear>
            <Option value="samsung">samsung</Option>
            <Option value="iphone">iphone</Option>
            <Option value="nokia">nokia</Option>
          </Select>
        </Form.Item>

        <Form.Item
          label="Thời hạn sử dụng"
          name={"year"}
          rules={[{ required: true, message: "Please select your device time use!" }]}
        >
          <Select placeholder="Chọn năm sử dụng" onChange={onTimeUseChange} allowClear>
            {Array(10)
              .fill({})
              .map((_, i) => (
                <Option key={i} value={i + 1}>
                  {i + 1} năm
                </Option>
              ))}
          </Select>
        </Form.Item>

        <Form.Item
          label="Màn hình"
          name={"screen"}
          rules={[{ required: true, message: "Please select your device screen!" }]}
        >
          <Radio.Group optionType="button">
            <Radio value="break">Vỡ</Radio>
            <Radio value="normal">Bình thường</Radio>
            <Radio value="good">Tốt</Radio>
          </Radio.Group>
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
            Báo giá
          </Button>
        </div>
      </Form>
    </>
  );
};

export default CustomForm;
