package io.github.johardi.r2rmlparser.exception;

public class UnsupportedPropertyException extends JR2RmlParserException
{
   private static final long serialVersionUID = 1681949L;

   private String mPropertyName;

   public UnsupportedPropertyException(String propertyName)
   {
      mPropertyName = propertyName;
   }

   public String getUnsupportedProperty()
   {
      return mPropertyName;
   }

   @Override
   public String getMessage()
   {
      return String.format("R2RML %s property is not supported yet", mPropertyName);
   }
}
