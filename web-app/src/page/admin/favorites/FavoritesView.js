import CustomMaterialTable from "../../../components/dashboard/CustomMaterialTable";
import { useRef } from "react";
import { TextFieldTableCell } from "../../../components/TableCells";
import { QueryKeys } from "../../../service/QueryKeys";
import { AdminService } from "../../../service/AdminService";

const favoritesService = new AdminService();

export default function FavoritesView({}) {
    const errorRef = useRef();

    const columns = [
      {
        title: "Id",
        field: "id",
      },
      {
        title: "Customer",
        field: "customer",
        editComponent: (props) => TextFieldTableCell(props, errorRef),
      },
      {
        title: "City",
        field: "city",
        editComponent: (props) => TextFieldTableCell(props, errorRef),
      },
    ];

    return (
      <CustomMaterialTable
        title="Manage Favorites"
        columns={columns}
        service={favoritesService}
        queryKey={QueryKeys.FAVORITES}
        errorRef={errorRef}
      />
    );
}