import { Typography } from "antd";
import CustomContent from "./CustomContent";
import CustomText from "./CustomText";

const { Title, Paragraph, Text } = Typography;

const DetailDevice = () => {
  return (
    <CustomContent>
      <Title level={2}>Thông tin thiết bị</Title>
      <CustomText title={"Tên thiết bị"} subtitle={"Iphone"} />
      <CustomText title={"Loại thiết bị"} subtitle={"Điện thoại"} />
      <CustomText title={"Tuổi đời"} subtitle={"2 năm"} />
      <CustomText title={"Vị trí thiệt hại "} subtitle={"Màn hình"} />
      <CustomText title={"Mô tả "} subtitle={"Nứt nhẹ"} />
    </CustomContent>
  );
};

export default DetailDevice;
