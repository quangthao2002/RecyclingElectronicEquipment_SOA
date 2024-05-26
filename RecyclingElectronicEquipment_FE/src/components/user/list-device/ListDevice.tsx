"use client";
import { useAuthContext } from "@/context/AuthProvider";
import userService from "@/services/userService";
import { Button, Col, Empty, Row, theme } from "antd";
import { Content } from "antd/es/layout/layout";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import CustomCard from "./CustomCard";

const ListDevice = () => {
  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();
  const router = useNavigate();
  const [data, setData] = useState(Array(6).fill({}));
  const { user } = useAuthContext();

  const handleAddDevice = () => {
    router("/device/add-device");
  };

  const handleGetList = async () => {
    try {
      const res = await userService.getList(user?.id as string);

      if (res?.data) {
        setData(res?.data);
      } else {
      }
    } catch (error) {
      console.log("error: ", error);
    }
  };

  useEffect(() => {
    if (user) {
      // handleGetList();
    }
  }, [user]);

  return (
    <Content style={{ margin: "0 16px" }}>
      <div
        style={{
          padding: 24,
          background: colorBgContainer,
          borderRadius: borderRadiusLG,
        }}
      >
        <Button onClick={handleAddDevice} type="primary">
          Thêm mới thiết bị
        </Button>
      </div>

      <div
        style={{
          padding: 24,
          minHeight: 360,
          background: colorBgContainer,
          borderRadius: borderRadiusLG,
        }}
      >
        <Row gutter={[16, 16]}>
          {data?.length > 0 ? (
            data?.map((item, index) => (
              <Col span={8} key={index}>
                <CustomCard loading data={item} />
              </Col>
            ))
          ) : (
            <Empty />
          )}
        </Row>
      </div>
    </Content>
  );
};

export default ListDevice;
