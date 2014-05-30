package io.github.johardi.r2rmlparser.mapping;

public class ObjectMap extends TermMap implements IMappingBody, IObjectMap
{
   @Override
   public void accept(IMappingVisitor visitor)
   {
      visitor.visit(this);
   }
}
