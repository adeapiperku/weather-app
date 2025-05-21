import CustomMaterialTable from "../../../components/dashboard/CustomMaterialTable";
import { useRef } from "react";
import { TextFieldTableCell } from "../../../components/TableCells";
import { QueryKeys } from "../../../service/QueryKeys";
import { AdminService } from "../../../service/AdminService";
import DateFnsUtils from "@date-io/date-fns";
import { DatePicker, MuiPickersUtilsProvider } from "@material-ui/pickers";
import LockIcon from '@material-ui/icons/Lock';

const adminsService = new AdminService();

export default function AdminsView({}) {
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
          {
            title: "Birth Date",
            type:"date",
            field: "birthDate",
            editComponent: (props) => (
              <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <DatePicker
                  value={props.value}
                  onChange={(date) => props.onChange(date)}
                  format="yyyy-MM-dd"
                  inputVariant="outlined"
                  fullWidth
                />
              </MuiPickersUtilsProvider>
            ),
          },
          {
            title: "Phone Number",
            field: "phoneNumber",
            editComponent: (props) => TextFieldTableCell(props, errorRef),
          },
      ];

      return (
        <CustomMaterialTable
          title="Manage Admins"
          columns={columns}
          service={adminsService}
          queryKey={QueryKeys.ADMINS}
          errorRef={errorRef}
        />
      );
  }