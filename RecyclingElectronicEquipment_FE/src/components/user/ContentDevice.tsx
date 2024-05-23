import { Button, Col, Empty, Row, theme } from "antd";
import { Content } from "antd/es/layout/layout";
import { useNavigate } from "react-router-dom";
import CustomCard from "./CustomCard";

const ContentDevice = () => {
  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();
  const router = useNavigate();

  const handleAddDevice = () => {
    router("/device/add-device");
  };
  const data = Array(6).fill({});

  // const handleSearchUser = async () => {
  //   setIsLoading(true)
  //   try {
  //     const res = await friendServices.searchUser(debounce)
  //     if (res?.data?.status === 404) {
  //       setResultSearch(null)
  //     } else if (res && res?.data) {
  //       setResultSearch(res.data)
  //     } else {
  //       setResultSearch(null)
  //     }
  //   } catch (error) {
  //     console.error("Error occurred while fetching user data:", error)
  //   } finally {
  //     setIsLoading(false)
  //   }
  // }

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
            data.map((item, index) => (
              <Col span={8} key={index}>
                <CustomCard loading data={index} />
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

export default ContentDevice;
