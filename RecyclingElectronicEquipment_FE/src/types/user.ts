export interface UserLogin {
  email: string;
  password: string;
}

export interface UserIResponse {
  id: string;
  email: string;
  roles: string[];
  jwt: string;
}
export interface UserRegister extends UserLogin {
  fullName: string;
  phoneNumber: string;
  address: string;
}
