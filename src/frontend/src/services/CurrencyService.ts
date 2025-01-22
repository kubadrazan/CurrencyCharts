import ApiService from "./ApiService";
import type Currency from "../interfaces/Currency"
import type CurrencyRates from "src/interfaces/CurrencyRates";
import {createCurrency} from "../interfaces/Currency"

class CurrencyService extends ApiService {
  constructor() {
    super("/api");
  }

  options = [
    {label: 'Moving Average', value: 'ma'},
    {label: 'Exp Moving Average', value: 'ema'},
  ];

  methodDictionary: Record<string, Function> = {
    ma: this.getCurrencyMovingAverage.bind(this),
    ema: this.getCurrencyExponentialMovingAverage.bind(this),
  };

  // Example usage of the dictionary
  async executeMethod(methodKey: string, code: string, startDate?: string, endDate?: string, param?: number
  ): Promise<CurrencyRates> {
    const method = this.methodDictionary[methodKey];
    if (method) {
      return await method(code, startDate, endDate, param);
    } else {
      throw new Error(`Method for key ${methodKey} not found.`);
    }
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

  async getCurrencyMovingAverage(code: string, startDate?: string, endDate?: string, windowSize?: number): Promise<CurrencyRates> {
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

    let baseUrl = `/currencies/${code}/ma`;

    baseUrl += '?' + searchParams.toString();

    return this.get<CurrencyRates>(baseUrl);
  }

  async getCurrencyExponentialMovingAverage(code: string, startDate?: string, endDate?: string, alpha?: number): Promise<CurrencyRates> {
    const searchParams: Record<string, any> = new URLSearchParams();

    if (!!startDate) {
      searchParams.append("startDate", startDate);
    }

    if (!!endDate) {
      searchParams.append("endDate", endDate);
    }

    if (!!alpha) {
      searchParams.append("alpha", alpha);
    }

    let baseUrl = `/currencies/${code}/ema`;

    baseUrl += '?' + searchParams.toString();

    return this.get<CurrencyRates>(baseUrl);
  }
}

export default CurrencyService
