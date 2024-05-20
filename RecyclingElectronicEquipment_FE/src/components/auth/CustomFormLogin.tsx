import { useAuthContext } from "@/context/AuthProvider";
import authService from "@/services/authService";
import { UserLogin } from "@/types/user";
import type { FormProps } from "antd";
import { Button, Form, Input } from "antd";
import React from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

interface FieldType extends UserLogin {}

const CustomFormLogin: React.FC = () => {
  const navigate = useNavigate();
  const { handleSetUser } = useAuthContext();

  const onFinish: FormProps<FieldType>["onFinish"] = async (values) => {
    try {
      const res = await authService.login(values);
      if (res) {
        console.log("res: ", res);
        handleSetUser(res?.data);
        const isAdmin = res?.data?.roles?.find((item: string) => item === "ROLES_ADMIN");
        navigate(isAdmin ? "/admin" : "/");
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
        label="Email"
        name="email"
        rules={[{ required: true, message: "Please input your email!" }]}
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

export default CustomFormLogin;
