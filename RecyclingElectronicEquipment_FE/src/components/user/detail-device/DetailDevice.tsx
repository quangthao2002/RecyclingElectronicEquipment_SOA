"use client";

import userService from "@/services/userService";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import CustomContent from "../../common/CustomContent";
import CustomHeader from "../../common/CustomHeader";
import Step1 from "../Step1";
import Step2 from "../Step2";
import Step3 from "../Step3";
import Step4 from "../Step4";
import Step5 from "../Step5";
import CustomStepDetailDevice from "./CustomStepDetailDevice";
import { toast } from "react-toastify";
import { handleGetStatusDeviceUser } from "@/utils/func";

const DetailDevice = () => {
  const { deviceId = "" } = useParams<{ deviceId: string }>();
  const [step, setStep] = useState<number>(1);

  const handlePrevStep = () => setStep((prev) => (prev > 0 ? prev - 1 : 0));
  const handleNextStep = () => setStep((prev) => (prev < 5 ? prev + 1 : 5));

  const handleGetDetailStatus = async () => {
    try {
      const res = await userService.getDetailStatus(deviceId as string);
      if (res?.data) {
        const step = handleGetStatusDeviceUser(res?.data?.recyclingReceiptStatus);
        setStep(step);
      } else {
        setStep(1);
      }
    } catch (error) {
      console.log("error: ", error);
      toast.error("Error getDetailStatus");
    }
  };

  useEffect(() => {
    if (deviceId) {
      handleGetDetailStatus();
    }
  }, [deviceId]);

  return (
    <>
      <CustomHeader title="Thông tin thiết bị" />

      <CustomContent>
        <CustomStepDetailDevice step={step} />

        <div style={{ marginTop: "4rem" }}>
          {step === 0 && <Step1 handleNextStep={handleNextStep} />}
          {step === 1 && <Step2 isTransport handlePrevStep={handlePrevStep} />}
          {step === 2 && <Step3 />}
          {step === 3 && <Step4 handleNextStep={handleNextStep} />}
          {step === 4 && <Step5 />}
        </div>
      </CustomContent>
    </>
  );
};

export default DetailDevice;
