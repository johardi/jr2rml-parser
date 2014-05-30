package io.github.johardi.r2rmlparser.mapping;

public interface IMappingVisitor
{
   void visit(LogicalTable arg);

   void visit(SubjectMap arg);

   void visit(PredicateObjectMap arg);

   void visit(PredicateMap arg);

   void visit(ObjectMap arg);

   void visit(RefObjectMap arg);
}
