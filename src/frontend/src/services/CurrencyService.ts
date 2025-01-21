import ApiService from "./ApiService";
import type Currency from "../interfaces/Currency"
import CurrencyRates from "src/interfaces/CurrencyRates";
class CurrencyService extends ApiService
{
  constructor()
  {
    super("/api");
  }
  async getCurrencies(): Promise<Currency[]> {
    return this.get<Currency[]>("/currencies/all");
  }

  async getCurrencyRates(code: string, startDate: Date, endDate: Date): Promise<CurrencyRates> {
    return this.get<CurrencyRates>("/currencies/" + code +
      "?startDate=" + startDate.toISOString() +
      "&endDate=" + endDate.toISOString());
  }

  async getCurrencyMovingAverage(code: string, startDate: Date, endDate: Date, windowSize: Number): Promise<CurrencyRates> {
    return this.get<CurrencyRates>("/currencies/" + code + "/ma" +
      "?startDate=" + startDate.toISOString() +
      "&endDate=" + endDate.toISOString() +
      "&windowSize=" + windowSize);
  }
}
export default CurrencyService
