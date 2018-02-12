package com.tutoref.dbsearch.config;

public enum DBTypeEnum {
	
     MYSQL("mysql"),
     ORACLE("oracle"),
     POSTGRESQL("postgresql");
     
     private final String symbol;
     
     DBTypeEnum(String symbol){
      this.symbol=symbol;
     }
     
     public String symbol(){
      return this.symbol;
     }
}
