// sidebarContext.js
import { QuoteResponse } from "@/types/quote";
import { createContext, useContext, useState } from "react";

interface DeviceIProps {
  quote: QuoteResponse | null;
  handleSetQuote: (quote: QuoteResponse) => void;
}

const DeviceContext = createContext({} as DeviceIProps);
export const useDeviceContext = () => useContext(DeviceContext);

export const DeviceProvider = ({ children }: { children: React.ReactNode }) => {
  const [quote, setQuote] = useState<QuoteResponse | null>(null);
  const handleSetQuote = (quote: QuoteResponse) => {
    setQuote(quote);
  };

  return (
    <DeviceContext.Provider
      value={{
        quote,
        handleSetQuote,
      }}
    >
      {children}
    </DeviceContext.Provider>
  );
};
