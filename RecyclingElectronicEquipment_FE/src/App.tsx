import { BrowserRouter, Route, Routes } from "react-router-dom";
import ChartReport from "./components/admin/chart/ChartReport";
import DetailResponseDevice from "./components/admin/DetailResponseDevice";
import ListTable from "./components/admin/ListTable";
import AddDevice from "./components/user/add-device/AddDevice";
import DetailDevice from "./components/user/detail-device/DetailDevice";
import ListDevice from "./components/user/list-device/ListDevice";
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
          <Route index element={<ListTable />} />
          <Route path="device/:deviceId" element={<DetailResponseDevice />} />
          <Route path="report" element={<ChartReport />} />
        </Route>

        <Route
          path="/"
          element={
            <ProtectRouter>
              <User />
            </ProtectRouter>
          }
        >
          <Route index element={<ListDevice />} />
          <Route path="device/add-device" element={<AddDevice />} />
          <Route path="device/:deviceId" element={<DetailDevice />} />
        </Route>

        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
