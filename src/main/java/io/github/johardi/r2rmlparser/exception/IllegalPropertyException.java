package io.github.johardi.r2rmlparser.exception;

public class IllegalPropertyException extends JR2RmlParserException
{
   private static final long serialVersionUID = 1681949L;

   public IllegalPropertyException(String propertyName, String location)
   {
      super(String.format("Illegal use %s at %s", propertyName, location));
   }
}
