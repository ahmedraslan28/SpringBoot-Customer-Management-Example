"use client";

import {
  Heading,
  Avatar,
  Box,
  Center,
  Image,
  Flex,
  Text,
  Stack,
  Tag,
  useColorModeValue,
} from "@chakra-ui/react";

export default function CardWithImage({ id, name, email, age, gender, imageNumber }) {
  return (
    <Center py={6} className="mohamed">
      <Box
        maxW={"300px"}
        minW={"300px"}
        w={"full"}
        bg={useColorModeValue("white", "gray.800")}
        boxShadow={"2xl"}
        rounded={"md"}
        overflow={"hidden"}
      >
        <Image
          h={"120px"}
          w={"full"}
          src={
            "https://images.unsplash.com/photo-1612865547334-09cb8cb455da?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80"
          }
          objectFit="cover"
          alt="#"
        />
        <Flex justify={"center"} mt={-12}>
          <Avatar
            size={"xl"}
            src={`https://randomuser.me/api/portraits/${
              gender === "MALE" ? "men" : "women"
            }/${imageNumber}.jpg`}
            css={{
              border: "2px solid white",
            }}
          />
        </Flex>

        <Box p={6}>
          <Stack spacing={2} align={"center"} mb={5}>
            <Tag borderRadius="full">{id}</Tag>
            <Heading fontSize={"2xl"} fontWeight={500} fontFamily={"body"}>
              {name}
            </Heading>
            <Text color={"gray.500"}>{email}</Text>
            <Text color={"gray.500"}>
              {age} | {gender}
            </Text>
          </Stack>
        </Box>
      </Box>
    </Center>
  );
}
