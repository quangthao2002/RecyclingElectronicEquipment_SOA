import { useAuthContext } from "@/context/AuthProvider";
import Login from "@/pages/Auth";

interface IProps {
  children: React.ReactNode;
}
const ProtectRouter = ({ children }: IProps) => {
  const { user } = useAuthContext();

  return user ? children : <Login />;
};

export default ProtectRouter;
