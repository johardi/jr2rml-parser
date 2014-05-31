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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import io.github.johardi.r2rmlparser.document.Document;
import io.github.johardi.r2rmlparser.exception.JR2RmlParserException;

public class JR2RmlVisitorPatternTest
{
   private JR2RmlParser mParser;

   @Before
   public void setUp() throws Exception
   {
      mParser = new JR2RmlParser();
   }

   @Test
   public void testVisitorPattern() throws JR2RmlParserException
   {
      String filePath = "res/example.ttl";
      
      Document document = mParser.parse(getReader(filePath), "http://example.com/ns");
      R2RmlDocumentHandler documentHandler = new R2RmlDocumentHandler();
      document.accept(documentHandler);
      
      List<String> templateStrings = documentHandler.getTemplateStrings();
      for (String template : templateStrings) {
         System.out.println(template);
      }
   }

   private Reader getReader(String filePath)
   {
      try {
         return new BufferedReader(new FileReader(filePath));
      }
      catch (FileNotFoundException e) {
         throw new RuntimeException("File not found: " + e.getMessage());
      }
   }
}
