/* eslint-disable @typescript-eslint/no-explicit-any */
import { Button, Form, FormProps, Input, Radio, Select } from "antd";
import React, { useState } from "react";
import CustomModal from "./CustomModal";
import { Quote, QuoteResponse } from "@/types/quote";
import deviceService from "@/services/deviceService";
import { toast } from "react-toastify";
import { useDeviceContext } from "@/context/DeviceProvider";

const { Option } = Select;

interface FieldType extends Quote {}

interface IProps {
  handleNextStep: () => void;
}

const Step1: React.FC<IProps> = ({ handleNextStep }) => {
  const { handleSetQuote } = useDeviceContext();
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

  const onFinish: FormProps<FieldType>["onFinish"] = async (values) => {
    console.log("Success:", values);
    try {
      const res = await deviceService.createQuote(values);
      if (res && res.data) {
        handleSetQuote(res.data);
        showModal();
      }
    } catch (error) {
      console.log("error: ", error);
      toast.error("Error Step1");
    }
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
        <Form.Item<FieldType>
          label="Tên thiết bị"
          name={"model"}
          rules={[{ required: true, message: "Please select your device name!" }]}
        >
          <Select placeholder="Chọn tên thiêt bị" onChange={onNameChange} allowClear>
            <Option value="samsung">samsung</Option>
            <Option value="iphone">iphone</Option>
            <Option value="nokia">nokia</Option>
          </Select>
        </Form.Item>

        <Form.Item<FieldType>
          label="Thời hạn sử dụng"
          name={"deviceAge"}
          rules={[{ required: true, message: "Please select your deviceAge!" }]}
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

        <Form.Item<FieldType>
          label="Giá ban đầu"
          name="initialPrice"
          rules={[{ required: true, message: "Please input your initialPrice!" }]}
        >
          <Input />
        </Form.Item>

        <Form.Item<FieldType>
          label="Trạng thái"
          name={"deviceStatus"}
          rules={[{ required: true, message: "Please select your deviceStatus!" }]}
        >
          <Radio.Group optionType="button">
            <Radio value="used">Đã dùng</Radio>
            <Radio value="new">Mới</Radio>
            <Radio value="break">Nứt</Radio>
          </Radio.Group>
        </Form.Item>

        <Form.Item<FieldType>
          label="Vị trí thiệt hại"
          name="damageLocation"
          rules={[{ required: true, message: "Please input your damageLocation!" }]}
        >
          <Input />
        </Form.Item>

        <Form.Item<FieldType>
          label="Mô tả thiệt hại"
          name="damageDescription"
          rules={[{ required: true, message: "Please input your damageDescription!" }]}
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
          <Button type="primary" htmlType="submit" ghost>
            Báo giá
          </Button>
        </div>
      </Form>
    </>
  );
};

export default Step1;
