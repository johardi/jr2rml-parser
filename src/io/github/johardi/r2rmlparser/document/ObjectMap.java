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

import io.github.johardi.r2rmlparser.R2RmlVocabulary;

public class ObjectMap extends TermMap implements IObjectMap
{
   @Override
   protected void decideDefaultTermType()
   {
      if (getType() == COLUMN_VALUE) {
         mTermType = R2RmlVocabulary.LITERAL;
      }
      else {
         mTermType = R2RmlVocabulary.IRI;
      }
   }

   @Override
   public void setDatatype(String datatype)
   {
      super.setDatatype(datatype);
      if (!bUserDefinedTermType) {
         mTermType = R2RmlVocabulary.LITERAL;
      }
   }

   @Override
   public void setLanguage(String language)
   {
      super.setLanguage(language);
      if (!bUserDefinedTermType) {
         mTermType = R2RmlVocabulary.LITERAL;
      }
   }

   @Override
   public void accept(IMappingVisitor visitor)
   {
      visitor.visit(this);
   }
}
