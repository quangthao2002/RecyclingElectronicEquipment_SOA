import React from "react";
import { Content } from "antd/es/layout/layout";
import { theme } from "antd";

interface IProps {
  children: React.ReactNode;
}
const CustomContent: React.FC<IProps> = ({ children }) => {
  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();
  return (
    <Content style={{ margin: "16px" }}>
      <div
        style={{
          padding: "24px 34px",
          minHeight: 360,
          background: colorBgContainer,
          borderRadius: borderRadiusLG,
        }}
      >
        {children}
      </div>
    </Content>
  );
};

export default CustomContent;
