/*
 * Copyright 2014 Josef Hardi <josef.hardi@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.johardi.r2rmlparser;

import java.io.IOException;
import java.io.Reader;

import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.turtle.TurtleParser;

import io.github.johardi.r2rmlparser.exception.JR2RmlParserException;
import io.github.johardi.r2rmlparser.mapping.Document;

public class JR2RmlParser
{
   private TurtleParser mTurtleParser = new TurtleParser();

   public Document parse(Reader documentReader, String baseIri) throws JR2RmlParserException
   {
      try {
         R2RmlHandler handler = new R2RmlHandler();
         mTurtleParser.setRDFHandler(handler);
         mTurtleParser.parse(documentReader, baseIri);
         return new Document(handler.getTriplesMaps(), handler.getPrefixMapper());
      }
      catch (RDFHandlerException e) {
         throw new JR2RmlParserException(e);
      }
      catch (RDFParseException e) {
         throw new JR2RmlParserException(e);
      }
      catch (IOException e) {
         throw new JR2RmlParserException(e);
      }
   }
}
