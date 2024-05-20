import { BrowserRouter, Route, Routes } from "react-router-dom";
import ContentTable from "./components/admin/ContentTable";
import ResponseDevice from "./components/admin/ResponseDevice";
import AddDevice from "./components/user/AddDevice";
import ContentDevice from "./components/user/ContentDevice";
import DetailDevice from "./components/user/DetailDevice";
import Admin from "./pages/Admin";
import NotFound from "./pages/NotFound";
import User from "./pages/User";
import ProtectRouter from "./routes/ProtectRouter";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/admin"
          element={
            <ProtectRouter>
              <Admin />
            </ProtectRouter>
          }
        >
          <Route index element={<ContentTable />} />
          <Route path="device/:deviceId" element={<ResponseDevice />} />
          <Route path="report" element={<ResponseDevice />} />
        </Route>

        <Route
          path="/"
          element={
            <ProtectRouter>
              <User />
            </ProtectRouter>
          }
        >
          <Route index element={<ContentDevice />} />
          <Route path="device/add-device" element={<AddDevice />} />
          <Route path="device/:deviceId" element={<DetailDevice />} />
        </Route>

        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
