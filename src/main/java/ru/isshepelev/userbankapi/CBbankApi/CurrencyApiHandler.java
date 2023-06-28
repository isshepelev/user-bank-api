package ru.isshepelev.userbankapi.CBbankApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
}
