package io.github.johardi.r2rmlparser.exception;

public class JR2RmlParserRuntimeException extends RuntimeException
{
   private static final long serialVersionUID = 1681949L;

   public JR2RmlParserRuntimeException()
   {
      super();
   }

   public JR2RmlParserRuntimeException(String message)
   {
      super(message);
   }

   public JR2RmlParserRuntimeException(String message, Throwable cause)
   {
      super(message, cause);
   }

   public JR2RmlParserRuntimeException(Throwable cause)
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
