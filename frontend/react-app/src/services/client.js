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


export const deleteCustomer = async (id) => {
  try {
    return await axios.delete(
      `${process.env.REACT_APP_API_BASE_URL}/api/v1/customers/${id}`
    );
  } catch (error) {
    throw error;
  }
};


export const updateCustomer = async (customer,id) => {
  try {
    return await axios.put(
      `${process.env.REACT_APP_API_BASE_URL}/api/v1/customers/${id}`,
      customer
    );
  } catch (error) {
    throw error;
  }
};