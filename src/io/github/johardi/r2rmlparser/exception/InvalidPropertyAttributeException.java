package io.github.johardi.r2rmlparser.exception;

import io.github.johardi.r2rmlparser.util.StringUtils;

public class InvalidPropertyAttributeException extends JR2RmlParserRuntimeException
{
   private static final long serialVersionUID = 1681949L;

   private String mPropertyLabel;
   private String mExceptionDetail;

   public InvalidPropertyAttributeException(String propertyLabel)
   {
      mPropertyLabel = propertyLabel;
   }

   public void setExceptionDetail(String detail)
   {
      mExceptionDetail = detail;
   }

   @Override
   public String getMessage()
   {
      StringBuilder sb = new StringBuilder();
      sb.append("Invalid property attribute for");
      sb.append("\"").append(mPropertyLabel).append("\"");
      if (!StringUtils.isEmpty(mExceptionDetail)) {
         sb.append(" ");
         sb.append(mExceptionDetail);
      }
      return sb.toString();
   }
}
