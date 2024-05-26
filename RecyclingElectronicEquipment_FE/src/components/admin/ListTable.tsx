import type { TabsProps } from "antd";
import { Tabs } from "antd";
import React from "react";
import CustomContent from "../common/CustomContent";
import TableInProcess from "./TableInProcess";
import TablePayment from "./TablePayment";
import TableRecycle from "./TableRecycle";
import TableWaitingForDevice from "./TableWaitingForDevice";

const items: TabsProps["items"] = [
  {
    key: "1",
    label: "Thiết bị giao tới",
    children: <TableWaitingForDevice />,
  },
  {
    key: "2",
    label: "Thiết bị chờ đánh giá",
    children: <TableInProcess />,
  },
  {
    key: "3",
    label: "Thiết bị đã thanh toán",
    children: <TablePayment />,
  },
  {
    key: "4",
    label: "Thiết bị tái chế",
    children: <TableRecycle />,
  },
];

const onChange = (key: string) => {
  console.log(key);
};

const ListTable: React.FC = () => {
  return (
    <CustomContent>
      <Tabs defaultActiveKey="1" items={items} onChange={onChange} />
    </CustomContent>
  );
};

export default ListTable;
