import {
  Spinner,
  Alert,
  AlertIcon,
  Wrap,
  WrapItem,
  AlertTitle,
} from "@chakra-ui/react";
import SidebarWithHeader from "./shared/SideBar";
import { useEffect, useState } from "react";
import { getCustomers } from "./services/client";
import CardWithImage from "./components/Card";
import { CreateCustomerDrawer } from "./components/Drawer";
const App = () => {
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [err, setError] = useState("");
  const fetchCustomers = () => {
    getCustomers()
      .then((res) => {
        setCustomers(res.data);
      })
      .catch((err) => {
        setError("Network Error Please Try Again");
      })
      .finally(() => {
        setLoading(false);
      });
  };
  useEffect(() => {
    fetchCustomers();
  }, []);

  if (loading) {
    return (
      <SidebarWithHeader>
        <Spinner
          thickness="4px"
          speed="0.65s"
          emptyColor="gray.200"
          color="blue.500"
          size="xl"
        />
      </SidebarWithHeader>
    );
  }
  if (err) {
    return (
      <SidebarWithHeader>
        <CreateCustomerDrawer />
        <Alert width={"308px"} mt={3} status="error">
          <AlertIcon mr={3} />
          <AlertTitle mt={0} mb={1} fontSize="md">
            {err}
          </AlertTitle>
        </Alert>
      </SidebarWithHeader>
    );
  }
  if (!customers.length) {
    return (
      <SidebarWithHeader>
        <CreateCustomerDrawer />
        <Alert width={"260px"} mt={3} status="info">
          <AlertIcon mr={3} />
          <AlertTitle mt={0} mb={1} fontSize="md">
            No Customers Available
          </AlertTitle>
        </Alert>
      </SidebarWithHeader>
    );
  }
  return (
    <SidebarWithHeader>
      <CreateCustomerDrawer fetchCustomers={fetchCustomers} />
      <Wrap spacing={"50px"} py={"5"} justify={"center"}>
        {customers.map((customer, index) => (
          <WrapItem key={index}>
            <CardWithImage fetchCustomers = {fetchCustomers} {...customer} imageNumber={index} />
          </WrapItem>
        ))}
      </Wrap>
    </SidebarWithHeader>
  );
};

export default App;
