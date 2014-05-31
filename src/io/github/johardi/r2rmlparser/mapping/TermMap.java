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
package io.github.johardi.r2rmlparser.mapping;

public abstract class TermMap
{
   /**
    * A constant-valued term map
    */
   public final static int CONSTANT_VALUE = 1;

   /**
    * A column-valued term map
    */
   public final static int COLUMN_VALUE = 2;

   /**
    * A template-valued term map
    */
   public final static int TEMPLATE_VALUE = 3;

   private int mType;

   private String mValue;
   private String mDatatype;
   private String mLanguage;

   public void setType(int type)
   {
      mType = type;
   }

   public int getType()
   {
      return mType;
   }

   public void setDatatype(String datatype)
   {
      mDatatype = datatype;
   }

   public String getDatatype()
   {
      return mDatatype;
   }

   public void setLanguage(String value)
   {
      mLanguage = value;
   }

   public String getLanguage()
   {
      return mLanguage;
   }

   public void setValue(String value)
   {
      mValue = value;
   }

   public String getValue()
   {
      return mValue;
   }
}
