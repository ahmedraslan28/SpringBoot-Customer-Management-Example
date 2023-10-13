import { useState, useContext, createContext, useEffect } from "react";
import {
  login as performLogin,
  getCustomersWithEmail,
} from "../services/client";
import jwtDecode from "jwt-decode";
const AuthContext = createContext(null);

export const AuthContextProvider = ({ children }) => {
  let defaultCustomer = {
    name: "loading",
    email: "",
    age: undefined,
    gender: "",
    rules: ["LOADING"],
  };
  const [customer, setCustomer] = useState(defaultCustomer);

  const setCustomerFromToken = () => {
    let token = localStorage.getItem("token");
    if (token) {
      token = jwtDecode(token);
      console.log(token);
      const email = token.sub;
      getCustomersWithEmail(email).then((res) => {
        setCustomer(res.data[0]);
      });
    }
  };

  useEffect(() => {
    console.log("inside authContext useeffect");
    setCustomerFromToken();
  }, []);

  const login = async (usernameAndPassword) => {
    try {
      const response = await performLogin(usernameAndPassword);
      const jwtToken = response.data.token;
      setCustomer(response.data.customer);
      localStorage.setItem("token", jwtToken);
      return response;
    } catch (error) {
      throw error;
    }
  };

  const logout = () => {
    localStorage.removeItem("token");
    setCustomer(defaultCustomer);
  };

  const isCustomerAuthenticated = () => {
    const token = localStorage.getItem("token");

    if (!token) return false;
    const decodeToken = jwtDecode(token);
    if (Date.now() > decodeToken.exp * 1000) {
      console.log("token expired");
      logout();
      return false;
    }

    return true;
  };

  const values = {
    customer,
    login,
    logout,
    isCustomerAuthenticated,
    setCustomer,
    setCustomerFromToken
  };

  return <AuthContext.Provider value={values}>{children}</AuthContext.Provider>;
};

export const useAuthContext = () => useContext(AuthContext);
