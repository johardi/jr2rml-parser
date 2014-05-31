package io.github.johardi.r2rmlparser.exception;

public class JR2RmlParserException extends Exception
{
   private static final long serialVersionUID = 1681949L;

   public JR2RmlParserException()
   {
      super();
   }

   public JR2RmlParserException(String message)
   {
      super(message);
   }

   public JR2RmlParserException(String message, Throwable cause)
   {
      super(message, cause);
   }

   public JR2RmlParserException(Throwable cause)
   {
      super(cause);
   }

   @Override
   public String getMessage()
   {
      StringBuilder sb = new StringBuilder();
      sb.append(super.getMessage());
      if (getCause() != null) {
         sb.append("\n"); //$NON-NLS-1$
         sb.append("Reason: "); //$NON-NLS-1$
         sb.append(getCause().getMessage());
     }
      return sb.toString();
   }
}
