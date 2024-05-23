import { Button, theme } from "antd";
import { Header } from "antd/es/layout/layout";
import { useNavigate } from "react-router-dom";

interface IProps {
  title: string;
}

const CustomHeader: React.FC<IProps> = ({ title = "" }) => {
  const {
    token: { colorBgContainer },
  } = theme.useToken();
  const router = useNavigate();

  const handleBack = () => {
    router(-1);
  };

  return (
    <Header
      style={{
        background: colorBgContainer,
        display: "flex",
        alignItems: "center",
        justifyContent: "space-between",
      }}
    >
      <Button onClick={handleBack} type="primary" ghost>
        Quay về
      </Button>
      <h1 style={{ textAlign: "center" }}>{title}</h1>
      <h1 style={{ textAlign: "center" }}></h1>
    </Header>
  );
};

export default CustomHeader;
