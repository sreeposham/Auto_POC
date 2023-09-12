/*
 * Copyright (c) 2022 Anh Tester
 * Automation Framework Selenium
 */

package anhtester.com.helpers;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public final class JsonHelpers {

    public JsonHelpers() {
        super();
    }


public static void writeToJsonFile(String filepath, Map<String, Map<String, String>> dataMap) {
//    ObjectWriter writer = new ObjectMapper().writer(new DefaultPrettyPrinter());
    ObjectMapper mapper = new ObjectMapper();
    try {
        mapper.writeValue(Paths.get(filepath).toFile(), dataMap);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public static Map<String, Map<String, String>> readFromJsonFile (String filepath)throws Exception{
        String jsonStr="";
        Map<String,Map<String, String>>readDataMap=new HashMap<>();
        ObjectMapper readMapper = new ObjectMapper();
        jsonStr=new String(Files.readAllBytes(Paths.get(filepath)));
            readDataMap=readMapper.readValue(jsonStr, Map.class);
            System.out.println(readDataMap);
        return readDataMap;
}
}