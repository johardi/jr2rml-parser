package io.github.johardi.r2rmlparser;

import java.io.IOException;
import java.io.Reader;

import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.turtle.TurtleParser;

import io.github.johardi.r2rmlparser.exception.R2RmlParserException;
import io.github.johardi.r2rmlparser.mapping.Document;

public class JR2RmlParser
{
   private TurtleParser mTurtleParser = new TurtleParser();

   public Document parse(Reader documentReader, String baseIri) throws R2RmlParserException
   {
      try {
         R2RmlHandler handler = new R2RmlHandler();
         mTurtleParser.setRDFHandler(handler);
         mTurtleParser.parse(documentReader, baseIri);
         return new Document(handler.getTriplesMaps(), handler.getPrefixMapper());
      }
      catch (RDFHandlerException e) {
         throw new R2RmlParserException(e);
      }
      catch (RDFParseException e) {
         throw new R2RmlParserException(e);
      }
      catch (IOException e) {
         throw new R2RmlParserException(e);
      }
   }
}
