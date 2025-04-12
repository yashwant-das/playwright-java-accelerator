package io.github.mypixelquest.pja.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages test data loading and access from various sources (YAML, JSON, CSV)
 */
@Slf4j
public class TestDataManager {
    private static final String DATA_DIR = "src/test/resources/data";
    private static final Map<String, Object> dataCache = new HashMap<>();
    
    private final ObjectMapper yamlMapper;
    private final ObjectMapper jsonMapper;
    private final CsvMapper csvMapper;
    private final String environment;
    
    /**
     * Creates a new TestDataManager instance
     * 
     * @param environment The environment to load data for (qa, prod, etc.)
     */
    public TestDataManager(String environment) {
        this.environment = environment;
        this.yamlMapper = new ObjectMapper(new YAMLFactory());
        this.jsonMapper = new ObjectMapper();
        this.csvMapper = new CsvMapper();
    }
    
    /**
     * Loads data from a YAML file
     * 
     * @param fileName The name of the YAML file
     * @return The loaded data as a Map
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> loadYamlData(String fileName) {
        String cacheKey = "yaml:" + fileName;
        if (dataCache.containsKey(cacheKey)) {
            return (Map<String, Object>) dataCache.get(cacheKey);
        }
        
        try {
            Path filePath = Paths.get(DATA_DIR, environment, fileName);
            if (!Files.exists(filePath)) {
                filePath = Paths.get(DATA_DIR, fileName);
            }
            
            Map<String, Object> data = yamlMapper.readValue(filePath.toFile(), Map.class);
            dataCache.put(cacheKey, data);
            return data;
        } catch (IOException e) {
            log.error("Failed to load YAML data from file: {}", fileName, e);
            throw new RuntimeException("Failed to load YAML data", e);
        }
    }
    
    /**
     * Loads data from a JSON file
     * 
     * @param fileName The name of the JSON file
     * @return The loaded data as a Map
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> loadJsonData(String fileName) {
        String cacheKey = "json:" + fileName;
        if (dataCache.containsKey(cacheKey)) {
            return (Map<String, Object>) dataCache.get(cacheKey);
        }
        
        try {
            Path filePath = Paths.get(DATA_DIR, environment, fileName);
            if (!Files.exists(filePath)) {
                filePath = Paths.get(DATA_DIR, fileName);
            }
            
            Map<String, Object> data = jsonMapper.readValue(filePath.toFile(), Map.class);
            dataCache.put(cacheKey, data);
            return data;
        } catch (IOException e) {
            log.error("Failed to load JSON data from file: {}", fileName, e);
            throw new RuntimeException("Failed to load JSON data", e);
        }
    }
    
    /**
     * Loads data from a CSV file
     * 
     * @param fileName The name of the CSV file
     * @return The loaded data as a Map with a "data" key containing a list of rows
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> loadCsvData(String fileName) {
        String cacheKey = "csv:" + fileName;
        if (dataCache.containsKey(cacheKey)) {
            return (Map<String, Object>) dataCache.get(cacheKey);
        }
        
        try {
            Path filePath = Paths.get(DATA_DIR, environment, fileName);
            if (!Files.exists(filePath)) {
                filePath = Paths.get(DATA_DIR, fileName);
            }
            
            // Create schema with headers auto-detected
            CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
            
            // Read CSV data into a list of maps (each map is a row)
            MappingIterator<Map<String, String>> mappingIterator = 
                csvMapper.readerFor(Map.class)
                         .with(csvSchema)
                         .readValues(filePath.toFile());
            
            List<Map<String, String>> rows = mappingIterator.readAll();
            
            // Create a map with a "data" key containing all the rows
            Map<String, Object> data = new HashMap<>();
            data.put("data", rows);
            
            dataCache.put(cacheKey, data);
            return data;
        } catch (IOException e) {
            log.error("Failed to load CSV data from file: {}", fileName, e);
            throw new RuntimeException("Failed to load CSV data", e);
        }
    }
    
    /**
     * Gets a specific value from the loaded data
     * 
     * @param data The data map
     * @param path The path to the value (e.g., "users.admin.username")
     * @return The value at the specified path
     */
    @SuppressWarnings("unchecked")
    public Object getValue(Map<String, Object> data, String path) {
        String[] keys = path.split("\\.");
        Object current = data;
        
        for (String key : keys) {
            if (current instanceof Map) {
                current = ((Map<String, Object>) current).get(key);
            } else {
                throw new RuntimeException("Invalid path: " + path);
            }
        }
        
        return current;
    }
    
    /**
     * Clears the data cache
     */
    public void clearCache() {
        dataCache.clear();
    }
}