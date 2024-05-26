import { useAuthContext } from "@/context/AuthProvider";
import adminService from "@/services/adminService";
import { StatusDevice } from "@/types/device";
import { handleGetColorTag } from "@/utils/func";
import type { TableProps } from "antd";
import { Space, Table, Tag } from "antd";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";

interface DataType {
  key: string;
  name: string;
  phone: number;
  price: string;
  status: StatusDevice;
}

const data: DataType[] = [
  {
    key: "1",
    name: "John Brown",
    phone: 123456789,
    price: "1000000",
    status: StatusDevice.Receive,
  },
  {
    key: "2",
    name: "Jim Green",
    phone: 123456789,
    price: "1000000",
    status: StatusDevice.Receive,
  },
  {
    key: "3",
    name: "Joe Black",
    phone: 123456789,
    price: "1000000",
    status: StatusDevice.Assessed,
  },
];

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
      const color = handleGetColorTag(status);
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

const TableInProcess: React.FC = () => {
  const [data, setData] = useState<any[]>([]);
  const { user } = useAuthContext();

  const handleGetList = async () => {
    try {
      const res = await adminService.getListInProcess();
      if (res?.data) {
        setData(res?.data);
      } else {
        toast.error("error TableInProcess ");
      }
    } catch (error) {
      toast.error("error TableInProcess ");
    }
  };

  useEffect(() => {
    if (user) {
      handleGetList();
    }
  }, [user]);

  return (
    <>
      <Table columns={columns} dataSource={data} />
    </>
  );
};

export default TableInProcess;
