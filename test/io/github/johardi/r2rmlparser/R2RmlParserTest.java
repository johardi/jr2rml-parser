package io.github.johardi.r2rmlparser;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.turtle.TurtleParser;

import io.github.johardi.r2rmlparser.mapping.ObjectMap;
import io.github.johardi.r2rmlparser.mapping.TriplesMap;

public class R2RmlParserTest
{
   private TurtleParser mParser = new TurtleParser();
   private R2RmlHandler mHandler = null;

   @Before
   public void setUp() throws Exception
   {
      mHandler = new R2RmlHandler();
      mParser.setRDFHandler(mHandler);
   }

   @Test
   public void testParser() throws RDFParseException, RDFHandlerException, IOException
   {
      String filePath = "res/example.ttl";
      
      mParser.parse(getReader(filePath), "http://example.com/ns");
      List<TriplesMap> mappingList = mHandler.getTriplesMaps();
      
      assertEquals(3, mappingList.size());
      
      /*
       * Testing <#TriplesMap1>
       */
      TriplesMap triplesMap1 = mappingList.get(0);
      checkResult(triplesMap1.getLogicalTable().getTableView().getSqlQuery(), "SELECT * FROM \"EMP\"");
      checkResult(triplesMap1.getSubjectMap().getClassIri(), "http://example.com/ns#Employee");
      checkResult(triplesMap1.getSubjectMap().getType(), 3);
      checkResult(triplesMap1.getSubjectMap().getValue(), "http://data.example.com/employee/{EMPNO}");
      
      checkResult(triplesMap1.getPredicateObjectMaps().size(), 1);
      checkResult(triplesMap1.getPredicateObjectMaps().get(0).getPredicateMap().getType(), 1);
      checkResult(triplesMap1.getPredicateObjectMaps().get(0).getPredicateMap().getValue(), "http://example.com/ns#name");
      checkResult(((ObjectMap) triplesMap1.getPredicateObjectMaps().get(0).getObjectMap()).getType(), 2);
      checkResult(((ObjectMap) triplesMap1.getPredicateObjectMaps().get(0).getObjectMap()).getValue(), "ENAME");
      
      /*
       * Testing <#TriplesMap2>
       */
      TriplesMap triplesMap2 = mappingList.get(1);
      checkResult(triplesMap2.getLogicalTable().getTableView().getSqlQuery(), 
            "select ('Department' || DEPTNO) AS DEPTID, DEPTNO, DNAME, LOC from SCOTT.DEPT");
      checkResult(triplesMap2.getSubjectMap().getClassIri(), null);
      checkResult(triplesMap2.getSubjectMap().getType(), 3);
      checkResult(triplesMap2.getSubjectMap().getValue(), "http://data.example.com/employee/{EMPNO}");
      
      checkResult(triplesMap2.getPredicateObjectMaps().size(), 1);
      checkResult(triplesMap2.getPredicateObjectMaps().get(0).getPredicateMap().getType(), 1);
      checkResult(triplesMap2.getPredicateObjectMaps().get(0).getPredicateMap().getValue(), "http://example.com/ns#role");
      checkResult(((ObjectMap) triplesMap2.getPredicateObjectMaps().get(0).getObjectMap()).getType(), 3);
      checkResult(((ObjectMap) triplesMap2.getPredicateObjectMaps().get(0).getObjectMap()).getValue(), "http://data.example.com/roles/{ROLE}");
      
      /*
       * Testing <#TriplesMap3>
       */
      TriplesMap triplesMap3 = mappingList.get(2);
      checkResult(triplesMap3.getLogicalTable().getTableView().getSqlQuery(), 
            "select DEPTNO, DNAME, LOC, (select count(*) from EMP where EMP.DEPTNO=DEPT.DEPTNO) as STAFF, DESC from DEPT");
      checkResult(triplesMap3.getSubjectMap().getClassIri(), "http://example.com/ns#Department");
      checkResult(triplesMap3.getSubjectMap().getType(), 3);
      checkResult(triplesMap3.getSubjectMap().getValue(), "http://data.example.com/department/{DEPTNO}");
      
      checkResult(triplesMap3.getPredicateObjectMaps().size(), 4);
      checkResult(triplesMap3.getPredicateObjectMaps().get(0).getPredicateMap().getType(), 1);
      checkResult(triplesMap3.getPredicateObjectMaps().get(0).getPredicateMap().getValue(), "http://example.com/ns#staff");
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(0).getObjectMap()).getType(), 2);
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(0).getObjectMap()).getValue(), "STAFF");
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(0).getObjectMap()).getDatatype(), "http://www.w3.org/2001/XMLSchema#int");
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(0).getObjectMap()).getLanguage(), null);
      
      checkResult(triplesMap3.getPredicateObjectMaps().get(1).getPredicateMap().getType(), 1);
      checkResult(triplesMap3.getPredicateObjectMaps().get(1).getPredicateMap().getValue(), "http://example.com/ns#description");
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(1).getObjectMap()).getType(), 2);
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(1).getObjectMap()).getValue(), "DESC");
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(1).getObjectMap()).getDatatype(), null);
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(1).getObjectMap()).getLanguage(), "en-us");
      
      checkResult(triplesMap3.getPredicateObjectMaps().get(2).getPredicateMap().getType(), 1);
      checkResult(triplesMap3.getPredicateObjectMaps().get(2).getPredicateMap().getValue(), "http://example.com/ns#name");
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(2).getObjectMap()).getType(), 2);
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(2).getObjectMap()).getValue(), "DNAME");
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(2).getObjectMap()).getDatatype(), null);
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(2).getObjectMap()).getLanguage(), null);
      
      checkResult(triplesMap3.getPredicateObjectMaps().get(3).getPredicateMap().getType(), 1);
      checkResult(triplesMap3.getPredicateObjectMaps().get(3).getPredicateMap().getValue(), "http://example.com/ns#location");
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(3).getObjectMap()).getType(), 2);
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(3).getObjectMap()).getValue(), "LOC");
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(3).getObjectMap()).getDatatype(), null);
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(3).getObjectMap()).getLanguage(), null);
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

   private void checkResult(String actual, String expected)
   {
      assertEquals(expected, actual);
   }

   private void checkResult(int actual, int expected)
   {
      assertEquals(expected, actual);
   }
}
