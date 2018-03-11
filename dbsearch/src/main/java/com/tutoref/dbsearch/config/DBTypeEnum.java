package com.tutoref.dbsearch.config;

/**
 * The Enum DBTypeEnum.
 */
public enum DBTypeEnum {
	
     MYSQL("mysql"),
     ORACLE("oracle"),
     POSTGRESQL("postgresql");
     
     /** The symbol. */
     private final String symbol;
     
     /**
      * Instantiates a new DB type enum.
      *
      * @param symbol the symbol
      */
     DBTypeEnum(String symbol){
      this.symbol=symbol;
     }
     
     /**
      * Symbol.
      *
      * @return the string
      */
     public String symbol(){
      return this.symbol;
     }
}
