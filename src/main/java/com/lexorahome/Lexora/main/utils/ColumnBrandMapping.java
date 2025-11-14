package com.lexorahome.Lexora.main.utils;


import java.util.HashMap;
import java.util.Map;

public class ColumnBrandMapping {

    public static Map<String,String> createMappingForLexoraHomeDepotUs(){
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("IDX_IS_CORE","6");
        columnMapping.put("IDX_COLLECTION","8");
        columnMapping.put("IDX_CATEGORY","4");
        columnMapping.put("IDX_CURRENT_SKU","9");
        columnMapping.put("IDX_UPC","10");
        columnMapping.put("IDX_TITLE","12");

        // only for non-core
        columnMapping.put("IDX_CORE_SKU","0");
        columnMapping.put("IDX_CORE_COLLECTION","1");

        // for unindexed values
        columnMapping.put("VAL_BRAND","Lexora");
        columnMapping.put("VAL_RETAIL","Home Depot USA");
        return columnMapping;
    }

    public static Map<String,String> createMappingForLexoraHomeDepotCa(){
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("IDX_IS_CORE","13");
        columnMapping.put("IDX_COLLECTION","15");
        columnMapping.put("IDX_CATEGORY","4");
        columnMapping.put("IDX_CURRENT_SKU","16");
        columnMapping.put("IDX_UPC","17");
        columnMapping.put("IDX_TITLE","18");

        // only for non-core
        columnMapping.put("IDX_CORE_SKU","0");
        columnMapping.put("IDX_CORE_COLLECTION","1");

        // for unindexed values
        columnMapping.put("VAL_BRAND","Lexora");
        columnMapping.put("VAL_RETAIL","Home Depot Canada");
        return columnMapping;
    }
    public static Map<String,String> createMappingForLexoraLowes(){
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("IDX_IS_CORE","20");
        columnMapping.put("IDX_COLLECTION","22");
        columnMapping.put("IDX_CATEGORY","4");
        columnMapping.put("IDX_CURRENT_SKU","23");
        columnMapping.put("IDX_UPC","24");
        columnMapping.put("IDX_TITLE","26");

        // only for non-core
        columnMapping.put("IDX_CORE_SKU","0");
        columnMapping.put("IDX_CORE_COLLECTION","1");

        // for unindexed values
        columnMapping.put("VAL_BRAND","Lexora");
        columnMapping.put("VAL_RETAIL","Lowes");
        return columnMapping;
    }

    public static Map<String,String> createMappingForLexoraHouzz(){
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("IDX_IS_CORE","27");
        columnMapping.put("IDX_COLLECTION","29");
        columnMapping.put("IDX_CATEGORY","4");
        columnMapping.put("IDX_CURRENT_SKU","30");
        columnMapping.put("IDX_UPC","31");
        columnMapping.put("IDX_TITLE","23");

        // only for non-core
        columnMapping.put("IDX_CORE_SKU","0");
        columnMapping.put("IDX_CORE_COLLECTION","1");

        // for unindexed values
        columnMapping.put("VAL_BRAND","Lexora");
        columnMapping.put("VAL_RETAIL","Houzz");
        return columnMapping;
    }

    public static Map<String,String> createMappingForLexoraOverstock(){
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("IDX_IS_CORE","34");
        columnMapping.put("IDX_COLLECTION","36");
        columnMapping.put("IDX_CATEGORY","4");
        columnMapping.put("IDX_CURRENT_SKU","37");
        columnMapping.put("IDX_UPC","38");
        columnMapping.put("IDX_TITLE","40");

        // only for non-core
        columnMapping.put("IDX_CORE_SKU","0");
        columnMapping.put("IDX_CORE_COLLECTION","1");

        // for unindexed values
        columnMapping.put("VAL_BRAND","Lexora");
        columnMapping.put("VAL_RETAIL","Overstock");
        return columnMapping;
    }

    public static Map<String,String> createMappingForLexoraWayfair(){
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("IDX_IS_CORE","41");
        columnMapping.put("IDX_COLLECTION","43");
        columnMapping.put("IDX_CATEGORY","4");
        columnMapping.put("IDX_CURRENT_SKU","44");
        columnMapping.put("IDX_UPC","45");
        columnMapping.put("IDX_TITLE","47");

        // only for non-core
        columnMapping.put("IDX_CORE_SKU","0");
        columnMapping.put("IDX_CORE_COLLECTION","1");

        // for unindexed values
        columnMapping.put("VAL_BRAND","Lexora");
        columnMapping.put("VAL_RETAIL","Wayfair");
        return columnMapping;
    }

    public static Map<String,String> createMappingForLexoraAmazon(){
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("IDX_IS_CORE","48");
        columnMapping.put("IDX_COLLECTION","50");
        columnMapping.put("IDX_CATEGORY","4");
        columnMapping.put("IDX_CURRENT_SKU","51");
        columnMapping.put("IDX_UPC","52");
        columnMapping.put("IDX_TITLE","54");

        // only for non-core
        columnMapping.put("IDX_CORE_SKU","0");
        columnMapping.put("IDX_CORE_COLLECTION","1");

        // for unindexed values
        columnMapping.put("VAL_BRAND","Lexora");
        columnMapping.put("VAL_RETAIL","Amazon");
        return columnMapping;
    }

    public static Map<String,String> createMappingForLexoraCanadianTire(){
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("IDX_IS_CORE","68");
        columnMapping.put("IDX_COLLECTION","70");
        columnMapping.put("IDX_CATEGORY","4");
        columnMapping.put("IDX_CURRENT_SKU","71");
        columnMapping.put("IDX_UPC","72");
        columnMapping.put("IDX_TITLE","74");

        // only for non-core
        columnMapping.put("IDX_CORE_SKU","0");
        columnMapping.put("IDX_CORE_COLLECTION","1");

        // for unindexed values
        columnMapping.put("VAL_BRAND","Lexora");
        columnMapping.put("VAL_RETAIL","Canadian Tire");
        return columnMapping;
    }

    public static Map<String,String> createMappingForLexoraTheBay(){
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("IDX_IS_CORE","75");
        columnMapping.put("IDX_COLLECTION","77");
        columnMapping.put("IDX_CATEGORY","4");
        columnMapping.put("IDX_CURRENT_SKU","78");
        columnMapping.put("IDX_UPC","79");
        columnMapping.put("IDX_TITLE","81");

        // only for non-core
        columnMapping.put("IDX_CORE_SKU","0");
        columnMapping.put("IDX_CORE_COLLECTION","1");

        // for unindexed values
        columnMapping.put("VAL_BRAND","Lexora");
        columnMapping.put("VAL_RETAIL","The Bay");
        return columnMapping;
    }


    public static Map<String,String> createMappingForBellModernAmazon(){
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("IDX_IS_CORE","61");
        columnMapping.put("IDX_COLLECTION","63");
        columnMapping.put("IDX_CATEGORY","4");
        columnMapping.put("IDX_CURRENT_SKU","64");
        columnMapping.put("IDX_UPC","65");
        columnMapping.put("IDX_TITLE","67");

        // only for non-core
        columnMapping.put("IDX_CORE_SKU","0");
        columnMapping.put("IDX_CORE_COLLECTION","1");

        // for unindexed values
        columnMapping.put("VAL_BRAND","BM");
        columnMapping.put("VAL_RETAIL","Amazon");
        return columnMapping;
    }


}
