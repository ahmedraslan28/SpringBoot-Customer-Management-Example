import axios from "axios";

export const getCustomers = async () => {
  try {
    return await axios.get(
      `${process.env.REACT_APP_API_BASE_URL}/api/v1/customers`
    );
  } catch (error) {
    throw error;
  }
};

export const saveCustomer = async (customer) => {
  try {
    return await axios.post(
      `${process.env.REACT_APP_API_BASE_URL}/api/v1/customers`,
      customer
    );
  } catch (error) {
    throw error;
  }
};
