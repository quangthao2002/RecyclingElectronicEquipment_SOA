import authService from "@/services/authService";
import { UserRegister } from "@/types/user";
import type { FormProps } from "antd";
import { Button, Form, Input } from "antd";
import React from "react";
import { toast } from "react-toastify";

interface FieldType extends UserRegister {
  admin: boolean;
}

const CustomFormSignUp: React.FC = () => {
  const onFinish: FormProps<FieldType>["onFinish"] = async (values) => {
    const admin = !!values?.admin;
    const formatValues: UserRegister = {
      address: values.address,
      email: values.email,
      fullName: values.fullName,
      password: values.password,
      phoneNumber: values.phoneNumber,
    };

    try {
      const res = admin
        ? await authService.registerAdmin(formatValues)
        : await authService.registerUser(formatValues);
      if (res) {
        console.log("res: ", res);
        toast.success(res.data || "Register successfully");
      }
    } catch (error) {
      console.log("error: ", error);
      toast.error("Error Login");
    }
  };
  const onFinishFailed: FormProps<FieldType>["onFinishFailed"] = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  return (
    <Form
      name="basic"
      labelCol={{ span: 8 }}
      wrapperCol={{ span: 16 }}
      style={{ maxWidth: 600, margin: "0 auto" }}
      initialValues={{ remember: true }}
      onFinish={onFinish}
      onFinishFailed={onFinishFailed}
      autoComplete="off"
    >
      <Form.Item<FieldType>
        label="Tên"
        name="fullName"
        rules={[{ required: true, message: "Please input your username!" }]}
      >
        <Input />
      </Form.Item>

      <Form.Item<FieldType>
        label="Số điện thoại"
        name="phoneNumber"
        rules={[{ required: true, message: "Please input your phone!" }]}
      >
        <Input />
      </Form.Item>

      <Form.Item<FieldType>
        label="Email"
        name="email"
        rules={[{ required: true, message: "Please input your email!" }]}
      >
        <Input />
      </Form.Item>

      <Form.Item<FieldType>
        label="Địa chỉ"
        name="address"
        rules={[{ required: true, message: "Please input your address!" }]}
      >
        <Input />
      </Form.Item>

      <Form.Item<FieldType>
        label="Mật khẩu"
        name="password"
        rules={[{ required: true, message: "Please input your password!" }]}
      >
        <Input.Password />
      </Form.Item>

      <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
        <Button type="primary" htmlType="submit">
          Submit
        </Button>
      </Form.Item>
    </Form>
  );
};

export default CustomFormSignUp;
