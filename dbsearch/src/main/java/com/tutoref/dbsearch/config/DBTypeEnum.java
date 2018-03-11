package com.tutoref.dbsearch.config;

// TODO: Auto-generated Javadoc
/**
 * The Enum DBTypeEnum.
 */
public enum DBTypeEnum {
	
     /** The mysql. */
     MYSQL("mysql"),
     
     /** The oracle. */
     ORACLE("oracle"),
     
     /** The postgresql. */
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
