import { QueryClient } from "react-query";
import { QueryKeys } from "./QueryKeys";
import { AdminService } from "./AdminService";
import { CustomerService } from "./CustomerService";
import { FavoritesService } from "./FavoritesService";


export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
      retry: (count, error) =>
        error.response?.status !== 401 &&
        error.response?.status !== 403 &&
        count < 3,
    },
  },
});

export const setQueryDefaults = () => {
  const customersService = new CustomerService();
  const adminsService = new AdminService();
  const favoritesService = new FavoritesService();
  
  queryClient.setQueryDefaults(QueryKeys.CUSTOMERS, {
    queryFn: () => customersService.findAll(),
  });
  queryClient.setQueryDefaults(QueryKeys.ADMINS, {
    queryFn: () => adminsService.findAll(),
  });
  queryClient.setQueryDefaults(QueryKeys.FAVORITES, {
    queryFn: () => favoritesService.findAll(),
  });
};
