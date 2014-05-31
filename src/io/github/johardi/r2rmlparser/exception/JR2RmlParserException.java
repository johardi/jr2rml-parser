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
