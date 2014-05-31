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

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Document
{
   private List<TriplesMap> mTriplesMaps;
   private Map<String, String> mPrefixMapper;

   public Document(List<TriplesMap> triplesMaps, Map<String, String> prefixMapper)
   {
      mTriplesMaps = triplesMaps;
      mPrefixMapper = prefixMapper;
   }

   /**
    * Returns a list of triples maps found in the input R2RML document.
    */
   public List<TriplesMap> getTriplesMaps()
   {
      return Collections.unmodifiableList(mTriplesMaps);
   }

   /**
    * Returns a prefix mapper that maps prefix to its IRI.
    */
   public Map<String, String> getPrefixMapper()
   {
      return Collections.unmodifiableMap(mPrefixMapper);
   }

   public void accept(IDocumentVisitor visitor)
   {
      visitor.visit(this);
   }
}
