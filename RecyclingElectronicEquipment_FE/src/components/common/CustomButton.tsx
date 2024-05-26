import { Button } from "antd";
import { MouseEventHandler } from "react";

interface IProps {
  isLoading?: boolean;
  children: string;
  onClick: MouseEventHandler<HTMLElement>;
}

const CustomButton = ({ isLoading, children, onClick }: IProps) => {
  return (
    <Button type="primary" loading={isLoading} onClick={onClick}>
      {children}
    </Button>
  );
};

export default CustomButton;
