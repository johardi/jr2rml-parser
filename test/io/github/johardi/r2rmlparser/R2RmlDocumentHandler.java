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
package io.github.johardi.r2rmlparser;

import java.util.ArrayList;
import java.util.List;

import io.github.johardi.r2rmlparser.mapping.Document;
import io.github.johardi.r2rmlparser.mapping.IDocumentVisitor;
import io.github.johardi.r2rmlparser.mapping.IGraphVisitor;
import io.github.johardi.r2rmlparser.mapping.IMappingVisitor;
import io.github.johardi.r2rmlparser.mapping.LogicalTable;
import io.github.johardi.r2rmlparser.mapping.ObjectMap;
import io.github.johardi.r2rmlparser.mapping.PredicateMap;
import io.github.johardi.r2rmlparser.mapping.PredicateObjectMap;
import io.github.johardi.r2rmlparser.mapping.RefObjectMap;
import io.github.johardi.r2rmlparser.mapping.SubjectMap;
import io.github.johardi.r2rmlparser.mapping.TermMap;
import io.github.johardi.r2rmlparser.mapping.TriplesMap;

public class R2RmlDocumentHandler implements IDocumentVisitor, IGraphVisitor, IMappingVisitor
{
   private List<String> mTemplateStrings = new ArrayList<String>();

   public List<String> getTemplateStrings()
   {
      return mTemplateStrings;
   }

   @Override
   public void visit(Document document)
   {
      for (TriplesMap triplesMap : document.getTriplesMaps()) {
         triplesMap.accept(this);
      }
   }

   @Override
   public void visit(TriplesMap triplesMap)
   {
      triplesMap.getSubjectMap().accept(this);
      for (PredicateObjectMap pom : triplesMap.getPredicateObjectMaps()) {
         pom.accept(this);
      }
   }

   @Override
   public void visit(LogicalTable arg)
   {
      // NO-OP
   }

   @Override
   public void visit(SubjectMap arg)
   {
      switch (arg.getType()) {
         case TermMap.TEMPLATE_VALUE:
            mTemplateStrings.add(arg.getValue()); break;
         case TermMap.COLUMN_VALUE:
         case TermMap.CONSTANT_VALUE:
            // NO-OP
      }
   }

   @Override
   public void visit(PredicateObjectMap arg)
   {
      arg.getObjectMap().accept(this);
   }

   @Override
   public void visit(PredicateMap arg)
   {
      // NO-OP
   }

   @Override
   public void visit(ObjectMap arg)
   {
      switch (arg.getType()) {
         case TermMap.TEMPLATE_VALUE:
            mTemplateStrings.add(arg.getValue()); break;
         case TermMap.COLUMN_VALUE:
         case TermMap.CONSTANT_VALUE:
            // NO-OP
      }
   }

   @Override
   public void visit(RefObjectMap arg)
   {
      // NO-OP
   }
}
