package io.github.johardi.r2rmlparser.mapping;

public class ObjectMap extends TermMap implements IObjectMap
{
   @Override
   public void accept(IMappingVisitor visitor)
   {
      visitor.visit(this);
   }
}
