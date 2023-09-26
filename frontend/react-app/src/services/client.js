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
