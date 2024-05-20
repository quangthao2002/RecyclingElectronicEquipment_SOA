import { useAuthContext } from "@/context/AuthProvider";
import { DesktopOutlined, LoginOutlined } from "@ant-design/icons";
import type { MenuProps } from "antd";
import { Layout, Menu } from "antd";
import React, { useState } from "react";
import { Link, Outlet } from "react-router-dom";

const { Footer, Sider } = Layout;

type MenuItem = Required<MenuProps>["items"][number];

function getItem(
  label: React.ReactNode,
  key: React.Key,
  icon?: React.ReactNode,
  children?: MenuItem[],
): MenuItem {
  return {
    key,
    icon,
    children,
    label,
  } as MenuItem;
}

const User: React.FC = () => {
  const [collapsed, setCollapsed] = useState(false);
  const { handleLogout } = useAuthContext();

  const items: MenuItem[] = [
    getItem(<Link to={`/`}>Thiết bị cần bán</Link>, "1", <DesktopOutlined />),
    getItem(<div onClick={handleLogout}>Đăng xuất</div>, "2", <LoginOutlined />),
  ];

  return (
    <Layout style={{ minHeight: "100vh" }}>
      <Sider
        style={{ padding: "10px 0" }}
        collapsible
        collapsed={collapsed}
        onCollapse={(value) => setCollapsed(value)}
      >
        <div className="demo-logo-vertical" />
        <Menu theme="dark" defaultSelectedKeys={["1"]} mode="inline" items={items} />
      </Sider>

      <Layout>
        <Outlet />

        <Footer style={{ textAlign: "center" }}>
          Recycle app ©{new Date().getFullYear()} Created by Dav
        </Footer>
      </Layout>
    </Layout>
  );
};

export default User;
