import { BaseService } from "./BaseService";

export class FavoritesService extends BaseService {
   constructor() {
     super("/favorites");
   }
 
   addFavorite(customerId, city) {
     return this.client.post(`${this.requestMapping}?customerId=${customerId}`, city);
   }
 
   removeFavorite(customerId, city) {
     return this.client.delete(`${this.requestMapping}?customerId=${customerId}`, {
        data: city, 
      });
    }
 
   getFavoriteCities(customerId) {
     return this.client.get(`${this.requestMapping}/${customerId}`);
   }

   getFavoritesByCustomerId(customerId) {
     return this.client.get(`${this.requestMapping}/customer/${customerId}/full`);
   }

   getFavoritesView() {
     return this.client.get(`${this.requestMapping}/view`);
   }
   
   create(favorite) {
     return this.addFavorite(favorite.customerId, favorite.city);
   }

   getFavoritesViewByCustomerId(customerId) {
     return this.client.get(`${this.requestMapping}/view/${customerId}`);
   }
}