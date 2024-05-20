import type { TableProps } from "antd";
import { Space, Table, Tag } from "antd";
import React from "react";
import CustomContent from "../common/CustomContent";
import { Link } from "react-router-dom";

type StatusType = "Đang chờ" | "Đã phản hồi" | "Đã duyệt";

interface DataType {
  key: string;
  name: string;
  phone: number;
  price: string;
  status: StatusType;
}

const columns: TableProps<DataType>["columns"] = [
  {
    title: "Tên",
    dataIndex: "name",
    key: "name",
    render: (text) => <div>{text}</div>,
  },

  {
    title: "Số điện thoại",
    dataIndex: "phone",
    key: "phone",
    render: (text) => <div>{text}</div>,
  },
  {
    title: "Ước lượng giá",
    dataIndex: "price",
    key: "price",
    render: (text) => <div>{text}</div>,
  },
  {
    title: "Trạng thái",
    dataIndex: "status",
    key: "status",
    render: (_, { status }) => {
      const color =
        status === "Đang chờ" ? "volcano" : status === "Đã phản hồi" ? "warning" : "green";

      return <Tag color={color}>{status.toUpperCase()}</Tag>;
    },
  },
  {
    title: "Hành động",
    key: "action",
    render: (_, record) => (
      <Space size="middle">
        <Link to={`/admin/device/${1}`}>Chi Tiết</Link>
      </Space>
    ),
  },
];

const data: DataType[] = [
  {
    key: "1",
    name: "John Brown",
    phone: 123456789,
    price: "1000000",
    status: "Đang chờ",
  },
  {
    key: "2",
    name: "Jim Green",
    phone: 123456789,
    price: "1000000",
    status: "Đã phản hồi",
  },
  {
    key: "3",
    name: "Joe Black",
    phone: 123456789,
    price: "1000000",
    status: "Đã duyệt",
  },
  {
    key: "4",
    name: "Jim Red",
    phone: 123456789,
    price: "1000000",
    status: "Đã duyệt",
  },
];

const ContentTable: React.FC = () => {
  return (
    <CustomContent>
      <Table columns={columns} dataSource={data} />
    </CustomContent>
  );
};

export default ContentTable;
