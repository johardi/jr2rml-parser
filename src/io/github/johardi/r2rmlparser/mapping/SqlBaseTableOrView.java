package io.github.johardi.r2rmlparser.mapping;

public class SqlBaseTableOrView implements ITableView
{
   private String mTableOrViewName;

   public void setBaseTable(String tableOrViewName)
   {
      mTableOrViewName = tableOrViewName;
   }

   public String getBaseTable()
   {
      return mTableOrViewName;
   }

   @Override
   public String getSqlQuery()
   {
      return String.format("SELECT * FROM \"%s\"", mTableOrViewName);
   }
}
