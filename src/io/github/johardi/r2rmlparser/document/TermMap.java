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
package io.github.johardi.r2rmlparser.document;

import io.github.johardi.r2rmlparser.util.StringUtils;

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
   private String mTermType;

   private String mValue;
   private String mDatatype;
   private String mLanguage;

   public void setType(int type)
   {
      mType = type;
      if (mTermType == null) {
         decideDefaultTermType();
      }
   }

   protected abstract void decideDefaultTermType();

   /**
    * Returns one of the following types below. The type specifies the kind of
    * value returns by <code>getValue()</code> method.
    * <ul>
    * <li>{@link CONSTANT_VALUE} - A constant-valued term map, specified by <code>rr:constant</code> property</li>
    * <li>{@link COLUMN_VALUE} - A column-valued term map, specified by <code>rr:ccolumn</code> property</li>
    * <li>{@link TEMPLATE_VALUE} - A template-valued term map, specified by <code>rr:template</code> property</li>
    * </ul>
    */
   public int getType()
   {
      return mType;
   }

   public void setTermType(String type)
   {
      mTermType = type;
   }

   public String getTermType()
   {
      return mTermType;
   }

   public void setDatatype(String datatype)
   {
      mDatatype = datatype;
   }

   /**
    * Returns the value specified by <code>rr:datatype</code> property.
    */
   public String getDatatype()
   {
      return mDatatype;
   }

   /**
    * Returns <code>true</code> if the map has specified explicitly the data
    * type, or <code>false</code> otherwise.
    */
   public boolean hasDatatype()
   {
      return (StringUtils.isEmpty(mDatatype)) ? false : true;
   }

   public void setLanguage(String value)
   {
      mLanguage = value;
   }

   /**
    * Returns the value specified by <code>rr:language</code> property.
    */
   public String getLanguage()
   {
      return mLanguage;
   }

   /**
    * Returns <code>true</code> if the map has language tag specified, or
    * <code>false</code> otherwise.
    */
   public boolean hasLanguageTag()
   {
      return (StringUtils.isEmpty(mLanguage)) ? false : true;
   }

   public void setValue(String value)
   {
      mValue = value;
   }

   /**
    * Returns the term map value. Use <code>getType()</code> method to know the
    * kind of value returned by this method.
    */
   public String getValue()
   {
      return mValue;
   }
}
