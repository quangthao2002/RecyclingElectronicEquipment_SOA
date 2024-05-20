import React from "react";
import ReactDOM from "react-dom/client";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import App from "./App.tsx";
import AuthProvider from "./context/AuthProvider.tsx";
import "./index.css";
import { DeviceProvider } from "./context/DeviceProvider.tsx";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <AuthProvider>
      <DeviceProvider>
        <App />
      </DeviceProvider>
      <ToastContainer />
    </AuthProvider>
  </React.StrictMode>,
);
