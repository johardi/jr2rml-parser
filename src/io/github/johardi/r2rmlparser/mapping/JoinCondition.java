package io.github.johardi.r2rmlparser.mapping;

public class JoinCondition
{
   private String mChildColumn;
   private String mParentColumn;

   public void setChildColumn(String name)
   {
      mChildColumn = name;
   }

   public String getChildColumn()
   {
      return mChildColumn;
   }

   public void setParentColumn(String name)
   {
      mParentColumn = name;
   }

   public String getParentColumn()
   {
      return mParentColumn;
   }
}
