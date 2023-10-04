import axios from "axios";
const { REACT_APP_API_BASE_URL } = process.env;
export const getCustomers = async () => {
  try {
    return await axios.get(`${REACT_APP_API_BASE_URL}/api/v1/customers`);
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
      `${REACT_APP_API_BASE_URL}/api/v1/customers/${id}`
    );
  } catch (error) {
    throw error;
  }
};

export const updateCustomer = async (customer, id) => {
  try {
    return await axios.put(
      `${REACT_APP_API_BASE_URL}/api/v1/customers/${id}`,
      customer
    );
  } catch (error) {
    throw error;
  }
};


/*
http://customer-api-env.eba-yk5r2usj.me-south-1.elasticbeanstalk.com
*/