function getRandomIntInclusive() {
  const minCeiled = Math.ceil(1000);
  const maxFloored = Math.floor(9999);
  return Math.floor(Math.random() * (maxFloored - minCeiled + 1) + minCeiled); // The maximum is inclusive and the minimum is inclusive
}