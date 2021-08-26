package org.galatea.starter.service;

import java.util.List;
import org.galatea.starter.domain.IexHistoricalPrice;
import org.galatea.starter.domain.IexLastTradedPrice;
import org.galatea.starter.domain.IexSymbol;
import org.galatea.starter.domain.Token;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A Feign Declarative REST Client to access endpoints from the Free and Open IEX API to get market
 * data. See https://iextrading.com/developer/docs/
 */
@FeignClient(name = "IEX", url = "${spring.rest.iexBasePath}")
public interface IexClient {

  /**
   * Get a list of all stocks supported by IEX. See https://iextrading.com/developer/docs/#symbols.
   * As of July 2019 this returns almost 9,000 symbols, so maybe don't call it in a loop.
   *
   * @return a list of all of the stock symbols supported by IEX.
   */
  @GetMapping("/ref-data/symbols")
  List<IexSymbol> getAllSymbols();

  /**
   * Get the last traded price for each stock symbol passed in. See https://iextrading.com/developer/docs/#last.
   *
   * @param symbols stock symbols to get last traded price for.
   * @return a list of the last traded price for each of the symbols passed in.
   */
  @GetMapping("/tops/last")
  List<IexLastTradedPrice> getLastTradedPriceForSymbols(@RequestParam("symbols") String[] symbols);

  /**
   * Get the historical prices for each stock symbol passed in.  See https://iexcloud.io/docs/api/#historical-prices.
   * @param symbols stock symbols to get historical price for.
   * @param range range of time to get stocks historical price
   * @return the historical price for each of the symbols passed in.
   */
  @RequestMapping(path = "stable/stock/{symbols}/chart/{range}?token=sk_c9b710dae6614eca843a9fd94c1de32e", method = RequestMethod.GET)
  List<IexHistoricalPrice> getHistoricalPriceForSymbols(@RequestParam("symbols") String[] symbols, @RequestParam("range") String[] range);

}
