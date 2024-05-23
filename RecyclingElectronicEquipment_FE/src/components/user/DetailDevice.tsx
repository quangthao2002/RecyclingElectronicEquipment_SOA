import { useState } from "react";
import CustomContent from "../common/CustomContent";
import CustomHeader from "../common/CustomHeader";
import CustomStep from "./CustomStep";
import Step1 from "./Step1";
import Step3 from "./Step2";
import Step4 from "./Step5";
import Step5 from "./Step6";

const AddDevice = () => {
  const [step, setStep] = useState<number>(0);

  const handlePrevStep = () => setStep((prev) => (prev > 0 ? prev - 1 : 0));
  const handleNextStep = () => setStep((prev) => (prev < 3 ? prev + 1 : 3));

  return (
    <>
      <CustomHeader title="Thông tin thiết bị" />

      <CustomContent>
        <CustomStep step={step} />

        <div style={{ marginTop: "4rem" }}>
          {step === 0 && <Step1 handleNextStep={handleNextStep} />}
          {step === 1 && <Step3 handleNextStep={handleNextStep} handlePrevStep={handlePrevStep} />}
          {step === 2 && <Step4 handleNextStep={handleNextStep} handlePrevStep={handlePrevStep} />}
          {step === 3 && <Step5 handlePrevStep={handlePrevStep} />}
        </div>
      </CustomContent>
    </>
  );
};

export default AddDevice;
