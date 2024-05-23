export interface UserLogin {
  email: string;
  password: string;
}

export interface UserRegister extends UserLogin {
  fullName: string;
  phoneNumber: string;
  address: string;
}

export interface UserIProps {
  id: string;
  email: string;
  roles: string[];
  jwt: string;
}
