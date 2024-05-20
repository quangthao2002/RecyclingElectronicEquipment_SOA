import CustomHeader from "../common/CustomHeader";
import ConfirmRefund from "./ConfirmRefund";
import EvaluateDevice from "./EvaluateDevice";

const ResponseDevice = () => {
  const isEvaluate = true;
  return (
    <div>
      <CustomHeader title="" />

      <EvaluateDevice />
      {isEvaluate && (
        <div style={{ marginTop: "4rem" }}>
          <ConfirmRefund />
        </div>
      )}
    </div>
  );
};

export default ResponseDevice;
