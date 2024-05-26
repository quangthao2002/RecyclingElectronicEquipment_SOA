import { Typography } from "antd";
import CustomContent from "../common/CustomContent";
import CustomText from "./CustomText";

const { Title, Paragraph, Text } = Typography;

const DetailUser = () => {
  return (
    <CustomContent>
      <Title level={2}>Thông tin khách hàng</Title>
      <CustomText title={"Tên"} subtitle={"Le Dav"} />
      <CustomText title={"Email"} subtitle={"test@gmail.com"} />
      <CustomText title={"Địa chỉ"} subtitle={"Hiepbinh street"} />
      <CustomText title={"Số điện thoại"} subtitle={"1243234"} />
    </CustomContent>
  );
};

export default DetailUser;
