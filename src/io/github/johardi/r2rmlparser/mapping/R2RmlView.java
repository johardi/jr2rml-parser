package io.github.johardi.r2rmlparser.mapping;

public class R2RmlView implements ITableView
{
   private String mSqlString;

   public void setSqlString(String sqlString)
   {
      mSqlString = sqlString;
   }

   @Override
   public String getSqlQuery()
   {
      return mSqlString;
   }
}
