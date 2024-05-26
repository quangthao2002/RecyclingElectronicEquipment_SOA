import { Avatar, Card } from "antd";
import React from "react";
import { Link } from "react-router-dom";

const { Meta } = Card;

interface IProps {
  data: unknown;
  loading?: boolean;
}

const CustomCard: React.FC<IProps> = ({ loading, data }) => {
  return (
    <Link to={`/device/${1}`}>
      <Card style={{}} loading={loading}>
        <Meta
          avatar={<Avatar src="https://api.dicebear.com/7.x/miniavs/svg?seed=1" />}
          title="Card title"
          description="This is the description"
        />
      </Card>
    </Link>
  );
};

export default CustomCard;
