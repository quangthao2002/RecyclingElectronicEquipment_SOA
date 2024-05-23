import { useState } from "react";
import CustomContent from "../common/CustomContent";
import CustomHeader from "../common/CustomHeader";
import CustomStep from "./CustomStep";
import Step1 from "./Step1";
import Step2 from "./Step2";
import Step3 from "./Step3";
import Step4 from "./Step4";
import Step5 from "./Step5";
import Step6 from "./Step6";

const AddDevice = () => {
  const [step, setStep] = useState<number>(4);

  const handlePrevStep = () => setStep((prev) => (prev > 0 ? prev - 1 : 0));
  const handleNextStep = () => setStep((prev) => (prev < 5 ? prev + 1 : 5));

  return (
    <>
      <CustomHeader title="Thêm thiết bị" />

      <CustomContent>
        <CustomStep step={step} />

        <div style={{ marginTop: "4rem" }}>
          {step === 0 && <Step1 handleNextStep={handleNextStep} />}
          {step === 1 && <Step2 handleNextStep={handleNextStep} handlePrevStep={handlePrevStep} />}
          {step === 2 && <Step3 handleNextStep={handleNextStep} handlePrevStep={handlePrevStep} />}
          {step === 3 && <Step4 handleNextStep={handleNextStep} handlePrevStep={handlePrevStep} />}
          {step === 4 && <Step5 handleNextStep={handleNextStep} handlePrevStep={handlePrevStep} />}
          {step === 5 && <Step6 handlePrevStep={handlePrevStep} />}
        </div>
      </CustomContent>
    </>
  );
};

export default AddDevice;
