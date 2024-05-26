import { StatusDeviceType } from "@/types/device";

export const handleGetColorTag = (status: string): string => {
  let color;
  switch (status) {
    case StatusDeviceType.Pending:
      color = "volcano";
      break;
    case StatusDeviceType.Receive:
      color = "orange";
      break;
    case StatusDeviceType.Assessed:
      color = "green";
      break;
    case StatusDeviceType.Paid:
      color = "cyan";
      break;
    case StatusDeviceType.Recycle:
      color = "purple";
      break;
    default:
      color = "blue";
  }
  return color;
};

export const handleGetStatusDeviceUser = (status: string): number => {
  let step;
  switch (status) {
    case StatusDeviceType.Pending:
      step = 1;
      break;
    case StatusDeviceType.Receive:
      step = 2;
      break;
    case StatusDeviceType.Assessed:
      step = 3;
      break;

    default:
      step = 1;
  }
  return step;
};

export function compactNumber(value: number): string {
  // Suffixes for different scales
  let precision: number = 0;
  const suffixes = ["", "K", "M", "B", "T", "P", "E"];
  // Determine the scale based on the absolute value
  const scale = !value ? 0 : Math.floor(Math.log10(Math.abs(value)) / 3);

  // Ensure scale is within the bounds of the suffixes array
  if (scale >= suffixes.length) {
    throw new Error("Number is too large to format");
  }

  // Calculate the formatted number
  const formattedNumber = (value / Math.pow(10, scale * 3)).toFixed(precision);
  // Append the appropriate suffix
  return `${formattedNumber}${suffixes[scale]}`;
}
