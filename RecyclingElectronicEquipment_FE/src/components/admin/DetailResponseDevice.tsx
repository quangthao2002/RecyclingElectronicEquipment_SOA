import { Col, Row } from "antd";
import CustomHeader from "../common/CustomHeader";
import DetailUser from "../common/DetailUser";
import EvaluateDevice from "./EvaluateDevice";
import DetailDevice from "../common/DetailDevice";

const ResponseDevice = () => {
  return (
    <div>
      <CustomHeader title="Chi tiết" />
      <Row>
        <Col span={12}>
          <DetailUser />
        </Col>

        <Col span={12}>
          <DetailDevice />
        </Col>

        <EvaluateDevice />
      </Row>
    </div>
  );
};

export default ResponseDevice;
