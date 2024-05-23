/* eslint-disable @typescript-eslint/no-explicit-any */
import { UserIProps } from "@/types/user";
import {
  LocalStorage,
  getLocalStorage,
  setLocalStorage,
  storageConstants,
} from "@/utils/localStorage";
import { createContext, useContext, useEffect, useState } from "react";

interface AuthContextIProps {
  user: UserIProps | null;
  handleLogout: () => void;
  handleSetUser: (values: UserIProps) => void;
}

const AuthContext = createContext({} as AuthContextIProps);
export const useAuthContext = () => useContext(AuthContext);

const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [user, setUser] = useState<UserIProps | null>(null);
  const userStorage = getLocalStorage(storageConstants.recycle_user);

  const handleLogout = () => {
    localStorage.removeItem(storageConstants.recycle_user);
    setUser(null);
  };

  const handleSetUser = (values: UserIProps) => {
    setLocalStorage(storageConstants.recycle_user, values);
    LocalStorage.setToken(values.jwt);
    setUser(values);
  };

  useEffect(() => {
    userStorage && setUser(userStorage);
  }, []);

  return (
    <AuthContext.Provider value={{ user, handleSetUser, handleLogout }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;
