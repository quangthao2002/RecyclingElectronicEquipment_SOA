import { useDeviceContext } from "@/context/DeviceProvider";
import { Button, Modal } from "antd";
import React, { useRef, useState } from "react";
import type { DraggableData, DraggableEvent } from "react-draggable";
import Draggable from "react-draggable";

interface IProps {
  handleNextStep: () => void;
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}
const CustomModal: React.FC<IProps> = ({ open, setOpen, handleNextStep }) => {
  const { quote } = useDeviceContext();
  const [disabled, setDisabled] = useState(true);
  const [bounds, setBounds] = useState({ left: 0, top: 0, bottom: 0, right: 0 });
  const draggleRef = useRef<HTMLDivElement>(null);

  const handleOk = (e: React.MouseEvent<HTMLElement>) => {
    console.log(e);
    handleNextStep();
    setOpen(false);
  };

  const handleCancel = (e: React.MouseEvent<HTMLElement>) => {
    console.log(e);
    setOpen(false);
  };

  const onStart = (_event: DraggableEvent, uiData: DraggableData) => {
    const { clientWidth, clientHeight } = window.document.documentElement;
    const targetRect = draggleRef.current?.getBoundingClientRect();
    if (!targetRect) {
      return;
    }
    setBounds({
      left: -targetRect.left + uiData.x,
      right: clientWidth - (targetRect.right - uiData.x),
      top: -targetRect.top + uiData.y,
      bottom: clientHeight - (targetRect.bottom - uiData.y),
    });
  };

  return (
    <Modal
      title={
        <div
          style={{
            width: "100%",
            cursor: "move",
          }}
          onMouseOver={() => {
            if (disabled) {
              setDisabled(false);
            }
          }}
          onMouseOut={() => {
            setDisabled(true);
          }}
          onFocus={() => {}}
          onBlur={() => {}}
        >
          Báo giá
        </div>
      }
      open={open}
      onOk={handleOk}
      onCancel={handleCancel}
      modalRender={(modal) => (
        <Draggable
          disabled={disabled}
          bounds={bounds}
          nodeRef={draggleRef}
          onStart={(event, uiData) => onStart(event, uiData)}
        >
          <div ref={draggleRef}>{modal}</div>
        </Draggable>
      )}
      footer={
        <div
          style={{
            marginTop: "2rem",
            display: "flex",
            alignItems: "center",
            gap: "1rem",
            justifyContent: "end",
          }}
        >
          <Button onClick={handleCancel} type="primary" ghost>
            Quay lại
          </Button>
          <Button onClick={handleOk} type="primary" ghost>
            Tiếp theo
          </Button>
        </div>
      }
    >
      <div>
        <strong>Tên thiết bị</strong>: {quote?.model}
      </div>
      <div>
        <strong>Số năm sử dụng</strong>: {quote?.estimatedPrice.toLocaleString()} năm
      </div>
      <div>
        <strong>Chất lượng màn hình</strong>: tốt
      </div>
      <div>
        <strong>Kết quả báo giá</strong>: {quote?.estimatedPrice.toLocaleString()} usd
      </div>
    </Modal>
  );
};

export default CustomModal;
