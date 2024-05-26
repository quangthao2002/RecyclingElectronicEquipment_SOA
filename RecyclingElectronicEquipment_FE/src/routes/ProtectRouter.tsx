import Login from "@/pages/Auth";

interface IProps {
  children: React.ReactNode;
}
const ProtectRouter = ({ children }: IProps) => {
  // const { user } = useAuthContext();
  const user = true;

  return user ? children : <Login />;
};

export default ProtectRouter;
