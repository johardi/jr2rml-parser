package io.github.johardi.r2rmlparser.mapping;

public class PredicateObjectMap implements IMappingBody
{
   private PredicateMap mPredicateMap;
   private IObjectMap mObjectMap;

   public void setPredicateMap(PredicateMap predicateMap)
   {
      mPredicateMap = predicateMap;
   }

   public PredicateMap getPredicateMap()
   {
      return mPredicateMap;
   }

   public void setObjectMap(IObjectMap objectMap)
   {
      mObjectMap = objectMap;
   }

   public IObjectMap getObjectMap()
   {
      return mObjectMap;
   }

   @Override
   public void accept(IMappingVisitor visitor)
   {
      visitor.visit(this);
   }
}
