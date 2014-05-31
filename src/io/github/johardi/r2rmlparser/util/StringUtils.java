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
package io.github.johardi.r2rmlparser.util;

import java.util.regex.Pattern;

public final class StringUtils
{
   /**
    * System specific line separator character.
    */
   public static final String NEWLINE = System.getProperty("line.separator"); //$NON-NLS-1$

   private static final Pattern containsWhitespacePattern = Pattern.compile(".*\\s.*"); //$NON-NLS-1$
   private static final Pattern isAllWhitespacePattern = Pattern.compile("^\\s*$"); //$NON-NLS-1$

   public static boolean isEmpty(final String text)
   {
      return text == null || text.isEmpty() || isAllWhitespacePattern.matcher(text).matches();
   }

   public static boolean containsWhitespace(final String text)
   {
      if (text == null) {
         return false;
      }
      else {
         return containsWhitespacePattern.matcher(text).matches();
      }
   }

   public static String toLowerCase(final String text)
   {
      return text.toLowerCase();
   }

   public static String toUpperCase(final String text)
   {
      return text.toUpperCase();
   }

   private StringUtils()
   {
      // NO-OP: Prevent instantiation
   }
}
