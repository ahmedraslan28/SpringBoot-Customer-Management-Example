import {
  useDisclosure,
  Button,
  Drawer,
  DrawerOverlay,
  DrawerContent,
  DrawerCloseButton,
  DrawerHeader,
  DrawerBody,
  DrawerFooter,
} from "@chakra-ui/react";
import CustomerRegistrationForm from "./CustomerRegistrationForm";

const AddIcon = () => "+";
const CloseIcon = () => "x";

export const CreateCustomerDrawer = () => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  return (
    <>
      <Button leftIcon={<AddIcon />} colorScheme="teal" onClick={onOpen}>
        Create Customer
      </Button>
      <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
        <DrawerOverlay />
        <DrawerContent>
          <DrawerCloseButton />
          <DrawerHeader>Create your account</DrawerHeader>

          <DrawerBody>
            <CustomerRegistrationForm onClose={onClose}/>
          </DrawerBody>

          <DrawerFooter>
            <Button
              leftIcon={<CloseIcon />}
              colorScheme="teal"
              onClick={onClose}
            >
              Close
            </Button>
          </DrawerFooter>
        </DrawerContent>
      </Drawer>
    </>
  );
};
