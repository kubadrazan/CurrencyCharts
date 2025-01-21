
interface Currency {
  code: string;
  currency: string;
  group: string[],
  options: { label: string; value: string }[];
}
function createCurrency(code: string = "", currency: string = ""): Currency {
  return {
    code,
    currency,
    group: [],
    options: [
      { label: 'Moving Average', value: 'ma' },
    ],
  };
}

export default Currency
export { createCurrency };
