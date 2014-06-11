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
      return String.format("The %s property is not supported yet by JR2RML Parser", mPropertyName);
   }
}
