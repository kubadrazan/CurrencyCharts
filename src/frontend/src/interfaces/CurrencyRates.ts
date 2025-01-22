import type Rate from "src/interfaces/Rate";

interface CurrencyRates {
  code: string;
  currency: string;
  rates: Array<Rate>;
}
export default CurrencyRates;
