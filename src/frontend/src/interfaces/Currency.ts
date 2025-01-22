interface Currency {
  code: string;
  currency: string;
  group: string[],
}
function createCurrency(code: string = "", currency: string = ""): Currency {
  return {
    code,
    currency,
    group: [],
  };
}

export default Currency
export { createCurrency };
