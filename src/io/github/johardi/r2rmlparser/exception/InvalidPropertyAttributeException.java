/*
 * Copyright 2014 Josef Hardi <josef.hardi@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
