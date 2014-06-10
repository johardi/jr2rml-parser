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

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import io.github.johardi.r2rmlparser.document.ObjectMap;
import io.github.johardi.r2rmlparser.document.PredicateObjectMap;
import io.github.johardi.r2rmlparser.document.R2RmlView;
import io.github.johardi.r2rmlparser.document.TriplesMap;
import io.github.johardi.r2rmlparser.exception.JR2RmlParserException;

public class JR2RmlParserTest
{
   private JR2RmlParser mParser;

   @Before
   public void setUp() throws Exception
   {
      mParser = new JR2RmlParser();
   }

   @Test
   public void testParser() throws JR2RmlParserException
   {
      String filePath = "res/example.ttl";
      
      R2RmlDocument document = mParser.parse(getReader(filePath), "http://example.com/ns");
      List<TriplesMap> mappingList = document.getTriplesMaps();
      
      assertEquals(4, mappingList.size());
      
      /*
       * Testing <#TriplesMap1>
       */
      TriplesMap triplesMap1 = mappingList.get(0);
      checkResult(triplesMap1.getLogicalTable().getTableView().getSqlQuery(), "SELECT * FROM EMP");
      checkResult(triplesMap1.getSubjectMap().getClassIri(), "http://example.com/ns#Employee");
      checkResult(triplesMap1.getSubjectMap().getType(), 3);
      checkResult(triplesMap1.getSubjectMap().getValue(), "http://data.example.com/employee/{EMPNO}");
      checkResult(triplesMap1.getSubjectMap().getTermType(), R2RmlVocabulary.IRI);
      
      checkResult(triplesMap1.getPredicateObjectMaps().size(), 1);
      checkResult(triplesMap1.getPredicateObjectMaps().get(0).getPredicateMap().getType(), 1);
      checkResult(triplesMap1.getPredicateObjectMaps().get(0).getPredicateMap().getValue(), "http://example.com/ns#name");
      checkResult(triplesMap1.getPredicateObjectMaps().get(0).getPredicateMap().getTermType(), R2RmlVocabulary.IRI);
      checkResult(((ObjectMap) triplesMap1.getPredicateObjectMaps().get(0).getObjectMap()).getType(), 2);
      checkResult(((ObjectMap) triplesMap1.getPredicateObjectMaps().get(0).getObjectMap()).getValue(), "ENAME");
      checkResult(((ObjectMap) triplesMap1.getPredicateObjectMaps().get(0).getObjectMap()).getTermType(), R2RmlVocabulary.LITERAL);
      
      /*
       * Testing <#TriplesMap2>
       */
      TriplesMap triplesMap2 = mappingList.get(1);
      checkResult(triplesMap2.getLogicalTable().getTableView().getSqlQuery(), "SELECT * FROM EMP");
      checkResult(triplesMap2.getSubjectMap().getClassIri(), "http://example.com/ns#Employee");
      checkResult(triplesMap2.getSubjectMap().getType(), 3);
      checkResult(triplesMap2.getSubjectMap().getValue(), "http://data.example.com/employee/{EMPNO}");
      checkResult(triplesMap2.getSubjectMap().getTermType(), R2RmlVocabulary.BLANK_NODE);
      
      checkResult(triplesMap2.getPredicateObjectMaps().size(), 1);
      checkResult(triplesMap2.getPredicateObjectMaps().get(0).getPredicateMap().getType(), 1);
      checkResult(triplesMap2.getPredicateObjectMaps().get(0).getPredicateMap().getValue(), "http://example.com/ns#fullName");
      checkResult(triplesMap2.getPredicateObjectMaps().get(0).getPredicateMap().getTermType(), R2RmlVocabulary.IRI);
      checkResult(((ObjectMap) triplesMap2.getPredicateObjectMaps().get(0).getObjectMap()).getType(), 3);
      checkResult(((ObjectMap) triplesMap2.getPredicateObjectMaps().get(0).getObjectMap()).getValue(), "{ENAME} {LNAME}");
      checkResult(((ObjectMap) triplesMap2.getPredicateObjectMaps().get(0).getObjectMap()).getTermType(), R2RmlVocabulary.LITERAL);
      
      /*
       * Testing <#TriplesMap3>
       */
      TriplesMap triplesMap3 = mappingList.get(2);
      checkResult(triplesMap3.getLogicalTable().getTableView().getSqlQuery(),
            "select ('Department' || DEPTNO) AS DEPTID, DEPTNO, DNAME, LOC from SCOTT.DEPT");
      checkResult(((R2RmlView) triplesMap3.getLogicalTable().getTableView()).getSqlVersion(), R2RmlVocabulary.MYSQL);
      checkResult(triplesMap3.getSubjectMap().getClassIri(), null);
      checkResult(triplesMap3.getSubjectMap().getType(), 3);
      checkResult(triplesMap3.getSubjectMap().getValue(), "http://data.example.com/employee/{EMPNO}");

      checkResult(triplesMap3.getPredicateObjectMaps().size(), 1);
      checkResult(triplesMap3.getPredicateObjectMaps().get(0).getPredicateMap().getType(), 1);
      checkResult(triplesMap3.getPredicateObjectMaps().get(0).getPredicateMap().getValue(), "http://example.com/ns#role");
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(0).getObjectMap()).getType(), 3);
      checkResult(((ObjectMap) triplesMap3.getPredicateObjectMaps().get(0).getObjectMap()).getValue(), "http://data.example.com/roles/{ROLE}");
      
      /*
       * Testing <#TriplesMap4>
       */
      TriplesMap triplesMap4 = mappingList.get(3);
      checkResult(triplesMap4.getLogicalTable().getTableView().getSqlQuery(), 
            "select DEPTNO, DNAME, LOC, (select count(*) from EMP where EMP.DEPTNO=DEPT.DEPTNO) as STAFF, DESC from DEPT");
      checkResult(((R2RmlView) triplesMap4.getLogicalTable().getTableView()).getSqlVersion(), null);
      checkResult(triplesMap4.getSubjectMap().getClassIri(), "http://example.com/ns#Department");
      checkResult(triplesMap4.getSubjectMap().getType(), 3);
      checkResult(triplesMap4.getSubjectMap().getValue(), "http://data.example.com/department/{DEPTNO}");
      
      checkResult(triplesMap4.getPredicateObjectMaps().size(), 4);
      
      for (int i = 0; i < triplesMap4.getPredicateObjectMaps().size(); i++) {
         PredicateObjectMap pom = triplesMap4.getPredicateObjectMaps().get(i);
         String predicateName = pom.getPredicateMap().getValue();
         if (predicateName.equals("http://example.com/ns#staff")) {
            checkResult(((ObjectMap) pom.getObjectMap()).getType(), 2);
            checkResult(((ObjectMap) pom.getObjectMap()).getValue(), "STAFF");
            checkResult(((ObjectMap) pom.getObjectMap()).getDatatype(), "http://www.w3.org/2001/XMLSchema#int");
            checkResult(((ObjectMap) pom.getObjectMap()).getLanguage(), null);
         }
         else if (predicateName.equals("http://example.com/ns#name")) {
            checkResult(((ObjectMap) pom.getObjectMap()).getType(), 2);
            checkResult(((ObjectMap) pom.getObjectMap()).getValue(), "DNAME");
            checkResult(((ObjectMap) pom.getObjectMap()).getDatatype(), null);
            checkResult(((ObjectMap) pom.getObjectMap()).getLanguage(), null);
         }
         else if (predicateName.equals("http://example.com/ns#location")) {
            checkResult(((ObjectMap) pom.getObjectMap()).getType(), 2);
            checkResult(((ObjectMap) pom.getObjectMap()).getValue(), "LOC");
            checkResult(((ObjectMap) pom.getObjectMap()).getDatatype(), null);
            checkResult(((ObjectMap) pom.getObjectMap()).getLanguage(), null);
         }
         else if (predicateName.equals("http://example.com/ns#description")) {
            checkResult(((ObjectMap) pom.getObjectMap()).getType(), 2);
            checkResult(((ObjectMap) pom.getObjectMap()).getValue(), "DESC");
            checkResult(((ObjectMap) pom.getObjectMap()).getDatatype(), null);
            checkResult(((ObjectMap) pom.getObjectMap()).getLanguage(), "en-us");
         }
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

   private void checkResult(Object actual, Object expected)
   {
      assertEquals(expected, actual);
   }
}
