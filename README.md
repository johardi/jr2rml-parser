JR2RmlParser parses a [R2RML mapping document](http://www.w3.org/TR/r2rml/) and translate it into a hierarchical Java object. The produced object can be navigated using [Visitor Pattern](http://en.wikipedia.org/wiki/Visitor_pattern). The parser uses Sesame RDF I/O module to walk through the R2RML Turtle syntax.

Feature
-------
* Support most R2RML mapping properties. The full list will be implemented incrementally.
* Using visitor pattern to allow easy integration with your existing code.
* Open source under Apache License 2.0

Example: Visitor Pattern
------------------------
This example shows you how to utilize the visitor pattern in the API. The handler below will collect string templates specified by `rr:template` attribute in a R2RML document.
```java
public class R2RmlDocumentHandler implements IDocumentVisitor, IGraphVisitor, IMappingVisitor
{
   private List<String> mTemplateStrings = new ArrayList<String>();

   public List<String> getTemplateStrings()
   {
      return mTemplateStrings;
   }

   @Override
   public void visit(Document document)
   {
      for (TriplesMap triplesMap : document.getTriplesMaps()) {
         triplesMap.accept(this);
      }
   }

   @Override
   public void visit(TriplesMap triplesMap)
   {
      triplesMap.getSubjectMap().accept(this);
      for (PredicateObjectMap pom : triplesMap.getPredicateObjectMaps()) {
         pom.accept(this);
      }
   }

   @Override
   public void visit(LogicalTable arg)
   {
      // NO-OP
   }

   @Override
   public void visit(SubjectMap arg)
   {
      switch (arg.getType()) {
         case TermMap.TEMPLATE_VALUE:
            mTemplateStrings.add(arg.getValue()); break;
         case TermMap.COLUMN_VALUE:
         case TermMap.CONSTANT_VALUE:
            // NO-OP
      }
   }

   @Override
   public void visit(PredicateObjectMap arg)
   {
      arg.getObjectMap().accept(this);
   }

   @Override
   public void visit(PredicateMap arg)
   {
      // NO-OP
   }

   @Override
   public void visit(ObjectMap arg)
   {
      switch (arg.getType()) {
         case TermMap.TEMPLATE_VALUE:
            mTemplateStrings.add(arg.getValue()); break;
         case TermMap.COLUMN_VALUE:
         case TermMap.CONSTANT_VALUE:
            // NO-OP
      }
   }

   @Override
   public void visit(RefObjectMap arg)
   {
      // NO-OP
   }
}
```

Next the client code below will use the handler to print the collected template strings to stdout.
```java
JR2RmlParser parser = new JR2RmlParse();   
Document document = parser.parse(getReader("example.ttl"), "http://example.com/ns");
R2RmlDocumentHandler documentHandler = new R2RmlDocumentHandler();
document.accept(documentHandler);
   
List<String> templateStrings = documentHandler.getTemplateStrings();
for (String template : templateStrings) {
   System.out.println(template);
}
```

License
-------
This software is licensed under the Apache 2 license, quoted below.

```
Copyright (c) 2014 Josef Hardi <josef.hardi@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.
```




