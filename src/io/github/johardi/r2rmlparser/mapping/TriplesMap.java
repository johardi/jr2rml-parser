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

import java.util.ArrayList;
import java.util.List;

public class TriplesMap implements IGraph
{
   private String mTriplesMapId;

   private LogicalTable mLogicalTable;
   private SubjectMap mSubjectMap;
   private List<PredicateObjectMap> mPredicateObjectMaps;

   public void setId(String id)
   {
      mTriplesMapId = id;
   }

   /**
    * Returns the triples map ID.
    */
   public String getId()
   {
      return mTriplesMapId;
   }

   public void setLogicalTable(LogicalTable logicalTable)
   {
      mLogicalTable = logicalTable;
   }

   /**
    * Returns {@link LogicalTable} object specified by <code>rr:logicalTable</code> property.
    */
   public LogicalTable getLogicalTable()
   {
      return mLogicalTable;
   }

   public void setSubjectMap(SubjectMap map)
   {
      mSubjectMap = map;
   }

   /**
    * Returns {@link SubjectMap} object specified by <code>rr:subjectMap</code> property.
    */
   public SubjectMap getSubjectMap()
   {
      return mSubjectMap;
   }

   public void addPredicateObjectMap(PredicateObjectMap map)
   {
      if (mPredicateObjectMaps == null) {
         mPredicateObjectMaps = new ArrayList<PredicateObjectMap>();
      }
      mPredicateObjectMaps.add(map);
   }

   /**
    * Returns a list of {@link PredicateObjectMap} objects specified by
    * <code>rr:predicateObjectMap</code> property.
    */
   public List<PredicateObjectMap> getPredicateObjectMaps()
   {
      return mPredicateObjectMaps;
   }

   @Override
   public void accept(IGraphVisitor visitor)
   {
      visitor.visit(this);
   }
}
