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

   public String getId()
   {
      return mTriplesMapId;
   }

   public void setLogicalTable(LogicalTable logicalTable)
   {
      mLogicalTable = logicalTable;
   }

   public LogicalTable getLogicalTable()
   {
      return mLogicalTable;
   }

   public void setSubjectMap(SubjectMap map)
   {
      mSubjectMap = map;
   }

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
