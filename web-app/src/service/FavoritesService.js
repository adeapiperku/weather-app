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
}
 