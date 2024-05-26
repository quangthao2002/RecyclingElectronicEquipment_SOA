import { StatusDevice } from "@/types/device";
import { handleGetColorTag } from "@/utils/func";
import type { TableProps } from "antd";
import { Space, Table, Tag } from "antd";
import React, { useEffect, useState } from "react";
import CustomButton from "../common/CustomButton";
import { useAuthContext } from "@/context/AuthProvider";
import adminService from "@/services/adminService";
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
    status: StatusDevice.Pending,
  },
  {
    key: "2",
    name: "Jim Green",
    phone: 123456789,
    price: "1000000",
    status: StatusDevice.Pending,
  },
  {
    key: "3",
    name: "Joe Black",
    phone: 123456789,
    price: "1000000",
    status: StatusDevice.Pending,
  },
  {
    key: "4",
    name: "Jim Red",
    phone: 123456789,
    price: "1000000",
    status: StatusDevice.Pending,
  },
];

const TableWaitingForDevice: React.FC = () => {
  const [data, setData] = useState<any[]>([]);
  const { user } = useAuthContext();

  const handleClick = async () => {
    try {
      const res = await adminService.updateToReceive("1", 100);
      if (res?.data) {
        handleGetList();
      } else {
        toast.error("Error click in TableWaitingForDevice");
      }
    } catch (error) {
      console.log("error: ", error);
      toast.error("Error click in TableWaitingForDevice");
    }
  };

  const handleGetList = async () => {
    try {
      const res = await adminService.getListWaitingForDevice();
      if (res?.data) {
        setData(res?.data);
      } else {
        toast.error("Error handleGetList TableWaitingForDevice");
      }
    } catch (error) {
      console.log("error: ", error);
      toast.error("Error handleGetList TableWaitingForDevice");
    }
  };

  useEffect(() => {
    if (user) {
      handleGetList();
    }
  }, [user]);

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
      title: "Đã nhận hàng",
      key: "action",
      render: (_, record) => (
        <Space size="middle">
          <CustomButton onClick={() => handleClick()}>Xác nhận</CustomButton>
        </Space>
      ),
    },
  ];

  return (
    <>
      <Table columns={columns} dataSource={data} />
    </>
  );
};

export default TableWaitingForDevice;
