import * as Yup from "yup";
import { Box, Button, FormLabel, Input, Select, Stack } from "@chakra-ui/react";
import { Formik, Form, useField } from "formik";
import { saveCustomer } from "../services/client";
import { FieldError } from "./Alerts";
import {
  errorNotification,
  successNotification,
} from "../services/Notification";

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

const CustomerRegistrationForm = ({ onClose }) => {
  return (
    <>
      <Formik
        initialValues={{
          name: "",
          email: "",
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
        })}
        onSubmit={(customer, { setSubmitting }) => {
          setSubmitting(true);
          saveCustomer(customer)
            .then((res) => {
              onClose();
              successNotification(
                "Customer Saved",
                `${customer.name} was successfully saved`
              );
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
export default CustomerRegistrationForm;
