package ru.isshepelev.userbankapi.CBbankApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class CurrencyApiHandler {
    ResponseApi response = new ResponseApi();

    public String getDate() throws IOException {
        JsonNode dateNode = response.openConnection().get("Date");
        String date = dateNode.asText().toString();
        return date;
    }

    public List<String> currencyList() throws IOException {
        List<String> valute = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.openConnection().traverse());
        JsonNode valuteNode = rootNode.get("Valute");

        for (JsonNode currencyNode : valuteNode) {
            String currencyCode = currencyNode.get("CharCode").asText();
            valute.add(currencyCode);
        }
        return valute;
    }

    public BigDecimal transferCurrency(String charCode1, String charCode2) throws IOException {
        Map<String, Double> currency = new HashMap<>();
        Map<String, Integer> currencyNominal = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        BigDecimal valueCharCode1 = BigDecimal.ZERO;
        BigDecimal valueCharCode2 = BigDecimal.ZERO;
        JsonNode rootNode = objectMapper.readTree(response.openConnection().traverse());
        JsonNode valuteNode = rootNode.get("Valute");

        for (JsonNode node : valuteNode) {
            String currencyCode = node.get("CharCode").asText();
            Integer nominalCode = node.get("Nominal").asInt();
            Double valueCode = node.get("Value").asDouble();
            currency.put(currencyCode, valueCode);
            currencyNominal.put(currencyCode, nominalCode);
        }


        for (String key : currency.keySet()) {
            if (charCode1.equals(key)) {
                int nominal = currencyNominal.get(key);
                valueCharCode1 = BigDecimal.valueOf(currency.get(key)).divide(BigDecimal.valueOf(nominal));
            }
            if (charCode2.equals(key)) {
                int nominal = currencyNominal.get(key);
                valueCharCode2 = BigDecimal.valueOf(currency.get(key)).divide(BigDecimal.valueOf(nominal));
            }
        }


        if (valueCharCode1.compareTo(BigDecimal.ZERO) != 0 && valueCharCode2.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal result = valueCharCode1.divide(valueCharCode2, RoundingMode.HALF_DOWN);
            return result;
        }
        return null;
    }
}
