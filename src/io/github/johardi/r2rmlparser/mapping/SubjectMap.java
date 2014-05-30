package io.github.johardi.r2rmlparser.mapping;

public class SubjectMap extends TermMap implements IMappingBody
{
   private String mClassIri;

   public void setClassIri(String iri)
   {
      mClassIri = iri;
   }

   public String getClassIri()
   {
      return mClassIri;
   }

   @Override
   public void accept(IMappingVisitor visitor)
   {
      visitor.visit(this);
   }
}
