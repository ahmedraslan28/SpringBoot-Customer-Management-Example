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
import { CustomerRegistrationForm, CustomerUpdateForm } from "./CustomerForms";

const AddIcon = () => "+";
const CloseIcon = () => "x";

export const CreateCustomerDrawer = ({ fetchCustomers }) => {
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
            <CustomerRegistrationForm
              onSuccess={fetchCustomers}
              onClose={onClose}
            />
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

export const UpdateCustomerDrawer = ({
  fetchCustomers,
  id,
  name,
  email,
  age,
  gender,
}) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  return (
    <>
      <Button colorScheme="blue" onClick={onOpen}>
        Update
      </Button>
      <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
        <DrawerOverlay />
        <DrawerContent>
          <DrawerCloseButton />
          <DrawerHeader>Update your profile</DrawerHeader>

          <DrawerBody>
            <CustomerUpdateForm
              id={id}
              name={name}
              age={age}
              gender={gender}
              email={email}
              fetchCustomers={fetchCustomers}
              onClose={onClose}
            />
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
