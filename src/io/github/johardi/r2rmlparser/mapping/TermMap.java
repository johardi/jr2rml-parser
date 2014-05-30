package io.github.johardi.r2rmlparser.mapping;

public abstract class TermMap
{
   /**
    * A constant-valued term map
    */
   public final static int CONSTANT_VALUE = 1;

   /**
    * A column-valued term map
    */
   public final static int COLUMN_VALUE = 2;

   /**
    * A template-valued term map
    */
   public final static int TEMPLATE_VALUE = 3;

   private int mType;

   private String mValue;
   private String mDatatype;
   private String mLanguage;

   public void setType(int type)
   {
      mType = type;
   }

   public int getType()
   {
      return mType;
   }

   public void setDatatype(String datatype)
   {
      mDatatype = datatype;
   }

   public String getDatatype()
   {
      return mDatatype;
   }

   public void setLanguage(String value)
   {
      mLanguage = value;
   }

   public String getLanguage()
   {
      return mLanguage;
   }

   public void setValue(String value)
   {
      mValue = value;
   }

   public String getValue()
   {
      return mValue;
   }
}
