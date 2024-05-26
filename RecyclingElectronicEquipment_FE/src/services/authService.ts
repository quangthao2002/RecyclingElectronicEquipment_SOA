import { UserLogin, UserRegister, UserIResponse } from "@/types/user";
import axiosClient from "./axiosClient";

const authService = {
  registerUser: (user: UserRegister): Promise<{ data: string }> => {
    return axiosClient.post(`auth/register-user`, user);
  },
  registerAdmin: (user: UserRegister): Promise<{ data: string }> => {
    return axiosClient.post(`auth/register-admin`, user);
  },
  login: (user: UserLogin): Promise<{ data: UserIResponse }> => {
    return axiosClient.post(`auth/login`, user);
  },
};

export default authService;
