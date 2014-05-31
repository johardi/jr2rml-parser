package io.github.johardi.r2rmlparser.mapping;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Document
{
   private List<TriplesMap> mTriplesMaps;
   private Map<String, String> mPrefixMapper;

   public Document(List<TriplesMap> triplesMaps, Map<String, String> prefixMapper)
   {
      mTriplesMaps = triplesMaps;
      mPrefixMapper = prefixMapper;
   }

   public List<TriplesMap> getTriplesMaps()
   {
      return Collections.unmodifiableList(mTriplesMaps);
   }

   public Map<String, String> getPrefixMapper()
   {
      return Collections.unmodifiableMap(mPrefixMapper);
   }

   public void accept(IDocumentVisitor visitor)
   {
      visitor.visit(this);
   }
}
