import { BaseService } from "./BaseService";

export class CustomerService extends BaseService {
  constructor() {
    super("/customers");
  }
}
