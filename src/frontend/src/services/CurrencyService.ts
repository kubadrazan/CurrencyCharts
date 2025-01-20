import ApiService from "./ApiService";
import type Currency from "../interfaces/Currency"
class CurrencyService extends ApiService
{
  constructor()
  {
    super("/api");
  }
  async getCurrencies(): Promise<Currency[]> {
    return this.get<Currency[]>("/currencies/all");
  }
}
export default CurrencyService
