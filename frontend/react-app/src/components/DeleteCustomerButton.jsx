import {
  Button,
  AlertDialog,
  useDisclosure,
  AlertDialogOverlay,
  AlertDialogFooter,
  AlertDialogBody,
  AlertDialogHeader,
  AlertDialogContent,
} from "@chakra-ui/react";
import React from "react";
import { deleteCustomer } from "../services/client";
import {
  errorNotification,
  successNotification,
} from "../services/Notification";
const DeleteButton = ({ id, name, fetchCustomers }) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const cancelRef = React.useRef();
  const onDelete = ({ setCustomers }) => {
    deleteCustomer(id)
      .then((res) => {
        successNotification(
          "Customer Deleted",
          `${name} was successfully deleted`
        );
        fetchCustomers();
      })
      .catch((err) => {
        errorNotification("Request Failed", err.response.data.message);
      })
      .finally(() => {
        onClose();
      });
  };
  return (
    <>
      <Button colorScheme="red" onClick={onOpen}>
        Delete
      </Button>

      <AlertDialog
        isOpen={isOpen}
        leastDestructiveRef={cancelRef}
        onClose={onClose}
      >
        <AlertDialogOverlay>
          <AlertDialogContent>
            <AlertDialogHeader fontSize="lg" fontWeight="bold">
              Delete Customer
            </AlertDialogHeader>

            <AlertDialogBody>
              Are you sure? You can't undo this action afterwards.
            </AlertDialogBody>

            <AlertDialogFooter>
              <Button ref={cancelRef} onClick={onClose}>
                Cancel
              </Button>
              <Button colorScheme="red" onClick={onDelete} ml={3}>
                Delete
              </Button>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialogOverlay>
      </AlertDialog>
    </>
  );
};

export default DeleteButton;
