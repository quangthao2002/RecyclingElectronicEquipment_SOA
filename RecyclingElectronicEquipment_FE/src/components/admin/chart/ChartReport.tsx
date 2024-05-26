import { Col, Row } from "antd";
import { useEffect, useState } from "react";
import CustomContent from "../../common/CustomContent";
import LineChart from "./LineChart";
import BarChart from "./BarChart";
import adminService from "@/services/adminService";
import { useAuthContext } from "@/context/AuthProvider";
import { toast } from "react-toastify";

const examLabels = ["Doanh thu", "Chi phí", "Lợi nhuận"];
const examValues = [12, 19, 3];

const ChartReport = () => {
  const [values, setValues] = useState(examValues);
  const [labels, setLabels] = useState(examLabels || []);
  const { user } = useAuthContext();

  const handleGetList = async () => {
    try {
      const res = await adminService.getDataReport();
      if (res?.data) {
        setValues([...res?.data]);
      } else {
        toast.error("Error handleGetList ChartReport");
      }
    } catch (error) {
      console.log("error: ", error);
      toast.error("Error handleGetList ChartReport");
    }
  };

  useEffect(() => {
    if (user) {
      handleGetList();
    }
  }, [user]);

  return (
    <CustomContent>
      <Row>
        <Col span={12}>
          <LineChart labels={labels} values={values} />
        </Col>

        <Col span={12}>
          <BarChart labels={labels} values={values} />
        </Col>
      </Row>
    </CustomContent>
  );
};

export default ChartReport;
