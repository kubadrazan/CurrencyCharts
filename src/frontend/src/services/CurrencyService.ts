import ApiService from "./ApiService";
import type Currency from "../interfaces/Currency"
import CurrencyRates from "src/interfaces/CurrencyRates";
import { createCurrency } from "../interfaces/Currency"
class CurrencyService extends ApiService
{
  constructor()
  {
    super("/api");
  }
  async getCurrencies(): Promise<Currency[]> {
    const rawData = await this.get<any[]>("/currencies/all");
    return rawData.map(item => createCurrency(item.code, item.currency));
  }

  async getCurrencyRates(code: string, startDate?: string, endDate?: string): Promise<CurrencyRates> {
    const searchParams: Record<string, any> = new URLSearchParams();

    if (!!startDate) {
      searchParams.append("startDate", startDate);
    }

    if (!!endDate) {
      searchParams.append("endDate", endDate);
    }

    let baseUrl = `/currencies/${code}`;

    baseUrl += '?' + searchParams.toString();

    return this.get<CurrencyRates>(baseUrl.toString());
  }

  async getCurrencyMovingAverage(code: string, startDate?: string, endDate?: string, windowSize?: Number): Promise<CurrencyRates> {
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

    let baseUrl = `/currencies/${code}`;

    baseUrl += '?' + searchParams.toString();

    return this.get<CurrencyRates>(baseUrl.toString());
  }
}
export default CurrencyService
