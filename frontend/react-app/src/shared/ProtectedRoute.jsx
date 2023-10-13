import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuthContext } from "../contexts/AuthContext";

const ProtectedRoute = ({ children }) => {
  const navigate = useNavigate();
  const { isCustomerAuthenticated } = useAuthContext();

  useEffect(() => {
    if (!isCustomerAuthenticated()) {
      navigate("/");
    }
  });

  return isCustomerAuthenticated() ? children : null;
};

export default ProtectedRoute;
