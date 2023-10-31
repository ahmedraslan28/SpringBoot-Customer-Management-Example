import * as Yup from "yup";
import { Box, Button, FormLabel, Input, Select, Stack, Text, Link } from "@chakra-ui/react";
import { Formik, Form, useField } from "formik";
import { updateCustomer, saveCustomer } from "../services/client";
import { FieldError } from "./Alerts";
import {
  errorNotification,
  successNotification,
} from "../services/Notification";
import { useAuthContext } from "../contexts/AuthContext";
import { useNavigate } from "react-router-dom";

const MyTextInput = ({ label, ...props }) => {
  const [field, meta] = useField(props);
  return (
    <Box>
      <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
      <Input className="text-input" {...field} {...props} />
      {meta.touched && meta.error ? <FieldError message={meta.error} /> : null}
    </Box>
  );
};

const MySelect = ({ label, ...props }) => {
  const [field, meta] = useField(props);
  return (
    <Box>
      <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
      <Select {...field} {...props} />
      {meta.touched && meta.error ? <FieldError message={meta.error} /> : null}
    </Box>
  );
};

export const CustomerRegistrationForm = ({ onClose, onSuccess }) => {
  return (
    <>
      <Formik
        initialValues={{
          name: "",
          email: "",
          password: "",
          age: null,
          gender: "",
        }}
        validationSchema={Yup.object({
          name: Yup.string()
            .max(50, "Must be 50 characters or less")
            .required("Required"),
          age: Yup.number()
            .min(16, "customer must be at least 16 years old")
            .max(99, "customer must be at most 99 years old")
            .required("Required"),
          gender: Yup.string()
            .oneOf(["MALE", "FEMALE"], "invalid gender type")
            .required("Required"),
          email: Yup.string()
            .email("Invalid email address")
            .required("Required"),
          password: Yup.string()
            .min(7, "password must be at least 7 characters long")
            .max(20, "password must be at most 7 characters long"),
        })}
        onSubmit={(customer, { setSubmitting }) => {
          setSubmitting(true);
          saveCustomer(customer)
            .then((res) => {
              onClose && onClose();
              successNotification(
                "Customer Saved",
                `${customer.name} was successfully saved`
              );
              onSuccess(res.data.token);
            })
            .catch((err) => {
              console.log(err);
              errorNotification(
                "Request Failed",
                `${err.response.data.message}`
              );
            })
            .finally(() => {
              setSubmitting(false);
            });
        }}
      >
        {({ isSubmitting, isValid }) => {
          return (
            <Form>
              <Stack spacing={"15px"}>
                <MyTextInput
                  label="Name"
                  name="name"
                  type="text"
                  placeholder="Jane"
                />

                <MyTextInput
                  label="Email"
                  name="email"
                  type="email"
                  placeholder="jane@formik.com"
                />

                <MyTextInput
                  label="Password"
                  name="password"
                  type="password"
                  placeholder="pick a secure password"
                />

                <MyTextInput
                  label="Age"
                  name="age"
                  type="number"
                  placeholder="18"
                />

                <MySelect label="Gender" name="gender">
                  <option value="">Select gender</option>
                  <option value="MALE">Male</option>
                  <option value="FEMALE">Female</option>
                </MySelect>

                <Button type="submit" isDisabled={!isValid || isSubmitting}>
                  Submit
                </Button>
              </Stack>
            </Form>
          );
        }}
      </Formik>
    </>
  );
};

export const CustomerUpdateForm = ({
  onClose,
  fetchCustomers,
  id,
  name,
  email,
  age,
  gender,
}) => {
  return (
    <>
      <Formik
        initialValues={{
          name: `${name}`,
          email: `${email}`,
          age: `${age}`,
        }}
        validationSchema={Yup.object({
          name: Yup.string()
            .max(50, "Must be 50 characters or less")
            .required("Required"),
          age: Yup.number()
            .min(16, "customer must be at least 16 years old")
            .max(99, "customer must be at most 99 years old")
            .required("Required"),
          email: Yup.string()
            .email("Invalid email address")
            .required("Required"),
        })}
        onSubmit={(customer, { setSubmitting }) => {
          setSubmitting(true);
          updateCustomer(customer, id)
            .then((res) => {
              onClose();
              successNotification(
                "Customer Updated",
                `${customer.name} was successfully saved`
              );
              fetchCustomers();
            })
            .catch((err) => {
              console.log(err);
              errorNotification(
                "Request Failed",
                `${err.response.data.message}`
              );
            })
            .finally(() => {
              setSubmitting(false);
            });
        }}
      >
        {({ isSubmitting, isValid, dirty }) => {
          return (
            <Form>
              <Stack spacing={"20px"}>
                <MyTextInput
                  label="Name"
                  name="name"
                  type="text"
                  placeholder="Jane"
                />

                <MyTextInput
                  label="Email"
                  name="email"
                  type="email"
                  placeholder="jane@formik.com"
                />

                <MyTextInput
                  label="Age"
                  name="age"
                  type="number"
                  placeholder="18"
                />

                <Button
                  type="submit"
                  isDisabled={!(isValid && dirty) || isSubmitting}
                >
                  Submit
                </Button>
              </Stack>
            </Form>
          );
        }}
      </Formik>
    </>
  );
};

export const CustomerLoginForm = () => {
  const { customer, login, setCustomer, isCustomerAuthenticated } =
    useAuthContext();
  const navigate = useNavigate();
  return (
    <Formik
      initialValues={{
        username: "",
        password: "",
      }}
      validationSchema={Yup.object({
        username: Yup.string()
          .email("Must be valid email")
          .required("Email is required"),
        password: Yup.string().required("Password is required"),
      })}
      onSubmit={(values, { setSubmitting }) => {
        console.log(values);
        setSubmitting(true);
        login(values)
          .then((res) => {
            console.log(customer);
            console.log(res.data.customer);
            navigate("/dashboard");
          })
          .catch((err) => {
            errorNotification(`${err.code}`, `${err.response.data.message}`);
          })
          .finally(() => setSubmitting(false));
      }}
    >
      {({ isValid, isSubmitting }) => (
        <>
          <Form>
            <Stack mt={4} spacing={15}>
              <MyTextInput
                label={"Email"}
                name={"username"}
                type={"email"}
                placeholder={"hello@amigoscode.com"}
              />
              <MyTextInput
                label={"Password"}
                name={"password"}
                type={"password"}
                placeholder={"Type your password"}
              />

              <Button
                type={"submit"}
                disabled={!isValid || isSubmitting}
                colorScheme={"blue"}
                variant={"solid"}
              >
                Sign in
              </Button>
            </Stack>
          </Form>
          <Stack mt={4}>

          <Text align={"center"}>
            New user ? <Link href="/register" color={"blue.400"}>SignUp</Link>
          </Text>
          </Stack>
        </>
      )}
    </Formik>
  );
};
