import { Tabs } from "antd";
import React from "react";
import CustomFormLogin from "./CustomFormLogin";
import CustomFormSignUp from "./CustomFormSignUp";

const CustomTab: React.FC = () => {
  const items = [
    {
      label: "Đăng nhập",
      key: "1",
      children: <CustomFormLogin />,
    },
    {
      label: "Đăng kí",
      key: "2",
      children: <CustomFormSignUp />,
    },
  ];
  return <Tabs defaultActiveKey="1" centered items={items} />;
};

export default CustomTab;
