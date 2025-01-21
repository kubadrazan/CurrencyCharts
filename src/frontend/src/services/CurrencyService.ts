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

  async getCurrencyRates(code: string, startDate?: string, endDate?: string): Promise<CurrencyRates> {
    const searchParams: Record<string, any> = new URLSearchParams();

    if (!!startDate) {
      searchParams.append("startDate", startDate);
    }

    if (!!endDate) {
      searchParams.append("endDate", endDate);
    }

    // Construct the base URL
    let baseUrl = `/currencies/${code}`;

    // Attach search parameters to the base URL
    baseUrl += '?' + searchParams.toString();

    return this.get<CurrencyRates>(baseUrl.toString());
  }

  async getCurrencyMovingAverage(code: string, startDate?: Date, endDate?: Date, windowSize?: Number): Promise<CurrencyRates> {
    const searchParams: Record<string, any> = new URLSearchParams();

    if (!!startDate) {
      searchParams.append("startDate", startDate);
    }

    if (!!endDate) {
      searchParams.append("endDate", endDate);
    }

    if (!!windowSize) {
      searchParams.append("windowSize", windowSize);
    }

    // Construct the base URL
    const baseUrl = new URL(`/currencies/${code}/ma`);

    // Attach search parameters to the base URL
    baseUrl.search = searchParams.toString();

    return this.get<CurrencyRates>(baseUrl.toString());
  }
}
export default CurrencyService
