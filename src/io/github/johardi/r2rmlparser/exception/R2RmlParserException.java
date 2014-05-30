package io.github.johardi.r2rmlparser.exception;

public class R2RmlParserException extends Exception
{
   private static final long serialVersionUID = 1681949L;

   public R2RmlParserException()
   {
      super();
   }

   public R2RmlParserException(String message)
   {
      super(message);
   }

   public R2RmlParserException(String message, Throwable cause)
   {
      super(message, cause);
   }

   public R2RmlParserException(Throwable cause)
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
