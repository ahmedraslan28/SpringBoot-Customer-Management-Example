import { Alert, AlertIcon, AlertDescription, AlertTitle } from "@chakra-ui/react";

export const FieldError = ({ message }) => {
  return (
    <Alert className="error" mt={2} status="error">
      <AlertIcon />
      {message}
    </Alert>
  );
};

export const SubmitAlert = ({status, title, description}) => {
  return (
    <Alert
      status={`${status}`}
      variant="subtle"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      textAlign="center"
      height="120px"
    >
      <AlertIcon boxSize="28px" mr={0} />
      <AlertTitle mt={2} mb={1} fontSize="lg">
        {title}
      </AlertTitle>
      <AlertDescription maxWidth="sm">{description}</AlertDescription>
    </Alert>
  );
};
