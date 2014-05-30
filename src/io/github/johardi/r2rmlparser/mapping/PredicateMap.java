package io.github.johardi.r2rmlparser.mapping;

public class PredicateMap extends TermMap implements IMappingBody
{
   @Override
   public void accept(IMappingVisitor visitor)
   {
      visitor.visit(this);
   }
}
