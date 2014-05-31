package io.github.johardi.r2rmlparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import io.github.johardi.r2rmlparser.exception.JR2RmlParserException;
import io.github.johardi.r2rmlparser.mapping.Document;

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
