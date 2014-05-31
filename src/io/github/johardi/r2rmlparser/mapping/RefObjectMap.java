package io.github.johardi.r2rmlparser.mapping;

public class RefObjectMap implements IObjectMap
{
   private String mParentTriplesMap;
   private JoinCondition mJoinCondition;

   public void setParentTriplesMap(String refTriplesMap)
   {
      mParentTriplesMap = refTriplesMap;
   }

   public String getParentTriplesMap()
   {
      return mParentTriplesMap;
   }

   public void setJoinCondition(JoinCondition join)
   {
      mJoinCondition = join;
   }

   public JoinCondition getJoinCondition()
   {
      return mJoinCondition;
   }

   @Override
   public void accept(IMappingVisitor visitor)
   {
      visitor.visit(this);
   }
}
