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

public class ObjectMap extends TermMap implements IObjectMap
{
   @Override
   /**
    * If the term map does not have a rr:termType property, then its term type is:
    * <ol>
    * <li>rr:Literal, if it is an object map and at least one of the following conditions is true:
    *    <ul>
    *    <li>It is a column-based term map.</li>
    *    <li>It has a rr:language property (and thus a specified language tag).</li>
    *    <li>It has a rr:datatype property (and thus a specified datatype).</li>
    *    </ul>
    * </li>
    * <li>rr:IRI, otherwise.</li>
    * </ol>
    */
   protected void decideDefaultTermType()
   {
      if (getType() == COLUMN_VALUE) {
         setTermType(TermType.LITERAL);
      }
      else if (hasLanguageTag()) {
         setTermType(TermType.LITERAL);
      }
      else if (hasDatatype()) {
         setTermType(TermType.LITERAL);
      }
      else {
         setTermType(TermType.IRI);
      }
   }

   @Override
   public void accept(IMappingVisitor visitor)
   {
      visitor.visit(this);
   }
}
