import { Typography } from "antd";

const { Title, Paragraph, Text } = Typography;

interface LineProps {
  title: string;
  subtitle: string;
}
const CustomText: React.FC<LineProps> = ({ title, subtitle }) => (
  <Paragraph>
    <Text>
      <strong>{title}</strong>: {subtitle}
    </Text>
  </Paragraph>
);

export default CustomText;
