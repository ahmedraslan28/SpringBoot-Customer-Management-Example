"use client";

import { Flex, Heading, Stack, Image, Text, Link } from "@chakra-ui/react";

import { useEffect } from "react";

import { CustomerRegistrationForm } from "./CustomerForms";

import { useNavigate } from "react-router-dom";

import { useAuthContext } from "../contexts/AuthContext";

const Register = () => {
  const { isCustomerAuthenticated, setCustomerFromToken } = useAuthContext();
  const navigate = useNavigate();
  useEffect(() => {
    if (isCustomerAuthenticated()) navigate("/dashboard");
  }, []);
  return (
    <Stack minH={"100vh"} direction={{ base: "column", md: "row" }}>
      <Flex p={8} flex={1} align={"center"} justify={"center"}>
        <Stack spacing={4} w={"full"} maxW={"md"}>
          <Heading fontSize={"2xl"}>Create new account</Heading>
          <CustomerRegistrationForm onSuccess={(token)=>{
            localStorage.setItem("token", token);
            setCustomerFromToken(token);
            navigate("/dashboard")
          }}/>
          <Text align={"center"}>
            Have an account ? <Link href='/' color={"blue.400"}>Login</Link>
          </Text>
        </Stack>
      </Flex>
      <Flex
        flex={1}
        p={10}
        flexDirection={"column"}
        alignItems={"center"}
        justifyContent={"center"}
        bgGradient={{ sm: "linear(to-r, blue.600, purple.600)" }}
      >
        <Image
          alt={"Login Image"}
          objectFit={"scale-down"}
          src={
            "https://user-images.githubusercontent.com/40702606/215539167-d7006790-b880-4929-83fb-c43fa74f429e.png"
          }
        />
      </Flex>
    </Stack>
  );
};
export default Register;
