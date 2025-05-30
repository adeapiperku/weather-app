import { useRef } from "react";
import { FavoritesService } from "../../../service/FavoritesService";
import CustomMaterialTable from "../../../components/dashboard/CustomMaterialTable";

const favoritesService = new FavoritesService();

export default function FavoritesView() {
  const errorRef = useRef(null);

  const columns = [
    {
      title: "Customer",
      field: "customer",
      render: (rowData) => <div>{rowData.customerName}</div>
    },
    {
      title: "Favorite Cities",
      field: "city",
      render: (rowData) => {
        if (
          !rowData.favoriteCities ||
          !Array.isArray(rowData.favoriteCities) ||
          rowData.favoriteCities.length === 0
        ) {
          return <div>No favorite cities</div>;
        }
        return (
          <div>
            {rowData.favoriteCities.map((city, index) => (
              <div key={city.id || index}>
                {city.cityName || "Unknown"}
                {index < rowData.favoriteCities.length - 1 && (
                  <hr style={{ margin: "5px 0" }} />
                )}
              </div>
            ))}
          </div>
        );
      }
    }
  ];

  const wrappedService = {
    findAll: async () => {
      const response = await favoritesService.getFavoritesView();
      const data = Array.isArray(response)
        ? response
        : response?.data || [];

      return data.map((favorite) => ({
        id: favorite.customerId || "N/A",
        customerName: favorite.customerName || "Unknown",
        favoriteCities: Array.isArray(favorite.favoriteCities)
          ? favorite.favoriteCities
          : [],
        customer: favorite.customerName || "Unknown",
        city: Array.isArray(favorite.favoriteCities)
          ? favorite.favoriteCities
              .map((city) => city.cityName || "Unknown City")
              .join(", ")
          : "No cities"
      }));
    }
  };

  wrappedService.delete = async (id) => {
    const data = await wrappedService.findAll();
    const customerData = data.find(item => item.id === id);
    
    if (!customerData || !customerData.favoriteCities || customerData.favoriteCities.length === 0) {
      throw new Error('No favorites found for this customer');
    }
    const cityToDelete = customerData.favoriteCities[0];
    const customerId = id;
    
    return favoritesService.removeFavorite(customerId, cityToDelete);
  };

  return (
    <CustomMaterialTable
      title="Favorites View"
      queryKey="favoritesView"
      service={wrappedService}
      columns={columns}
      errorRef={errorRef}
      disableDeleteAction={false}
      disableEditAction
    />
  );
}
