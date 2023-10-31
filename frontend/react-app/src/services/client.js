import axios from "axios";
const { REACT_APP_API_BASE_URL } = process.env;

const getAuthConfig = () => ({
  headers: {
    Authorization: `Bearer ${localStorage.getItem("token")}`,
  },
});

export const getCustomers = async () => {
  try {
    return await axios.get(
      `${REACT_APP_API_BASE_URL}/api/v1/customers`,
      getAuthConfig()
    );
  } catch (error) {
    throw error;
  }
};

export const getCustomersWithEmail = async (email) => {
  try {
    return await axios.get(
      `${REACT_APP_API_BASE_URL}/api/v1/customers?email=${email}`,
      getAuthConfig()
    );
  } catch (error) {
    throw error;
  }
};

export const saveCustomer = async (customer) => {
  try {
    return await axios.post(
      `${REACT_APP_API_BASE_URL}/api/v1/customers`,
      customer
    );
  } catch (error) {
    throw error;
  }
};

export const deleteCustomer = async (id) => {
  try {
    return await axios.delete(
      `${REACT_APP_API_BASE_URL}/api/v1/customers/${id}`,
      getAuthConfig()
    );
  } catch (error) {
    throw error;
  }
};

export const updateCustomer = async (customer, id) => {
  try {
    return await axios.put(
      `${REACT_APP_API_BASE_URL}/api/v1/customers/${id}`,
      customer,
      getAuthConfig()
    );
  } catch (error) {
    throw error;
  }
};

export const login = async (usernameAndPassword) => {
  try {
    return await axios.post(
      `${REACT_APP_API_BASE_URL}/api/v1/auth/login`,
      usernameAndPassword
    );
  } catch (error) {
    throw error;
  }
};

/*
http://customer-api-env.eba-yk5r2usj.me-south-1.elasticbeanstalk.com
*/
