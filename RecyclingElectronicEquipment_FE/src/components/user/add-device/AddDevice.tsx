import { useState } from "react";
import CustomContent from "../../common/CustomContent";
import CustomHeader from "../../common/CustomHeader";
import CustomStepAddDevice from "./CustomStepAddDevice";
import Step1 from "../Step1";
import Step2 from "../Step2";

const AddDevice = () => {
  const [step, setStep] = useState<number>(0);

  const handlePrevStep = () => setStep((prev) => (prev > 0 ? prev - 1 : 0));
  const handleNextStep = () => setStep((prev) => (prev < 1 ? prev + 1 : 1));

  return (
    <>
      <CustomHeader title="Thêm thiết bị" />

      <CustomContent>
        <CustomStepAddDevice step={step} />

        <div style={{ marginTop: "4rem" }}>
          {step === 0 && <Step1 handleNextStep={handleNextStep} />}
          {step === 1 && <Step2 handlePrevStep={handlePrevStep} />}
        </div>
      </CustomContent>
    </>
  );
};

export default AddDevice;
