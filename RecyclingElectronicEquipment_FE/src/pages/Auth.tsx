import CustomTab from "@/components/auth/CustomTab";
import CustomContent from "@/components/common/CustomContent";

const Login = () => {
  return (
    <CustomContent>
      <div style={{ textAlign: "center" }}>
        <h1>Welcome!</h1>
        <CustomTab />
      </div>
    </CustomContent>
  );
};

export default Login;
