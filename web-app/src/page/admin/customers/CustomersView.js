import { CustomerService } from "../../../service/CustomerService";
import CustomMaterialTable from "../../../components/dashboard/CustomMaterialTable";
import { useRef } from "react";
import { TextFieldTableCell } from "../../../components/TableCells";
import { QueryKeys } from "../../../service/QueryKeys";
import LockIcon from '@material-ui/icons/Lock';

const customersService = new CustomerService();

export default function CustomersView({}) {
  const errorRef = useRef();

  const columns = [
    {
      title: "First Name",
      field: "firstName",
      editComponent: (props) => TextFieldTableCell(props, errorRef),
    },
    {
      title: "Last Name",
      field: "lastName",
      editComponent: (props) => TextFieldTableCell(props, errorRef),
    },
    {
      title: "Email",
      field: "email",
      editComponent: (props) => TextFieldTableCell(props, errorRef),
    },
    {
      title: "Password",
      field: "password",
      render: () => (
        <div style={{ display: 'flex', alignItems: 'center' }}>
          <LockIcon />
          <span style={{ marginLeft: 5 }}>••••••••</span>
        </div>
      ),
      editComponent: (props) => TextFieldTableCell(props, errorRef),
    },
  ];

  return (
      <CustomMaterialTable
        title="Manage Customers"
        columns={columns}
        service={customersService}
        queryKey={QueryKeys.CUSTOMERS}
        errorRef={errorRef}
      />
  );
}
