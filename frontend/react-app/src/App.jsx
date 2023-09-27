import { Spinner, Text, Wrap, WrapItem, Center } from "@chakra-ui/react";
import SidebarWithHeader from "./shared/SideBar";
import { useEffect, useState } from "react";
import { getCustomers } from "./services/client";
import CardWithImage from "./components/Card";
const App = () => {
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getCustomers()
      .then((res) => {
        console.log(res);
        setCustomers(res.data);
      })
      .catch((err) => {
        console.log(err);
      })
      .finally(() => {
        setLoading(false);
      });
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
  if (!customers.length) {
    return (
      <SidebarWithHeader>
        <Text>No customers available</Text>
      </SidebarWithHeader>
    );
  }
  return (
    <SidebarWithHeader>
      <Wrap spacing={"30px"} justify={"center"}>
        {customers.map((customer, index) => (
          <WrapItem>
            <CardWithImage {...customer} imageNumber={index} />
          </WrapItem>
        ))}
      </Wrap>
    </SidebarWithHeader>
  );
};

export default App;
