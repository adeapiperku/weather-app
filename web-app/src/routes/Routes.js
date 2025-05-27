import { Navigate, Route } from "react-router-dom";
import AdminSignIn from "../page/admin/AdminSignIn";
import ClientSignIn from "../page/client/ClientSignIn";
import ClientSignUp from "../page/client/ClientSignUp";
import PrivateRoute from "../components/PrivateRoute";
import ClientLayout from "../page/client/ClientLayout";
import ClientProfile from "../page/client/ClientProfile";
import AdminLayout from "../page/admin/AdminLayout";

const AppRoutes = [

  <Route
    key={1}
    path="/"
    exact
    element={<Navigate replace to={"/client/home"} />}
  />,
  <Route key={2} path="/client/home" element={<ClientLayout />} />,
  // Admin routes
  <Route key={11} path="/admin/*" element={<PrivateRoute element={AdminLayout}/>} />,
  // // Other routes
  <Route key={11} path="/admin/sign-in" element={<AdminSignIn />} />,
  <Route key={11} path="/client/sign-in" element={<ClientSignIn />} />,
  <Route key={11} path="/client/sign-up" element={<ClientSignUp />} />,
  <Route key={11} path="/client/profile" element={<ClientProfile />} />,
];

export default AppRoutes;