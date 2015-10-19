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

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
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
      String filePath = "/example.ttl";

      R2RmlDocument document = mParser.parse(getReader(filePath), "http://example.com/ns#");
      List<TriplesMap> mappingList = document.getTriplesMaps();

      assertEquals(4, mappingList.size());

      for (TriplesMap tmap : document.getTriplesMaps()) {
         String id = tmap.getId();
         if (id.equals("http://example.com/ns#TriplesMap1")) {
            /*
             * Testing <#TriplesMap1>
             */
            checkResult(tmap.getLogicalTable().getTableView().getSqlQuery(), "SELECT * FROM EMP");
            checkResult(tmap.getSubjectMap().getClassIri(), "http://example.com/ns#Employee");
            checkResult(tmap.getSubjectMap().getType(), 3);
            checkResult(tmap.getSubjectMap().getValue(), "http://data.example.com/employee/{EMPNO}");
            checkResult(tmap.getSubjectMap().getTermType(), R2RmlVocabulary.IRI);
   
            checkResult(tmap.getPredicateObjectMaps().size(), 1);
            checkResult(tmap.getPredicateObjectMaps().get(0).getPredicateMap().getType(), 1);
            checkResult(tmap.getPredicateObjectMaps().get(0).getPredicateMap().getValue(), "http://example.com/ns#name");
            checkResult(tmap.getPredicateObjectMaps().get(0).getPredicateMap().getTermType(), R2RmlVocabulary.IRI);
            checkResult(((ObjectMap) tmap.getPredicateObjectMaps().get(0).getObjectMap()).getType(), 2);
            checkResult(((ObjectMap) tmap.getPredicateObjectMaps().get(0).getObjectMap()).getValue(), "ENAME");
            checkResult(((ObjectMap) tmap.getPredicateObjectMaps().get(0).getObjectMap()).getTermType(), R2RmlVocabulary.LITERAL);
         }
         else if (id.equals("http://example.com/ns#TriplesMap2")) {
            /*
             * Testing <#TriplesMap2>
             */
            checkResult(tmap.getLogicalTable().getTableView().getSqlQuery(), "SELECT * FROM EMP");
            checkResult(tmap.getSubjectMap().getClassIri(), "http://example.com/ns#Employee");
            checkResult(tmap.getSubjectMap().getType(), 3);
            checkResult(tmap.getSubjectMap().getValue(), "http://data.example.com/employee/{EMPNO}");
            checkResult(tmap.getSubjectMap().getTermType(), R2RmlVocabulary.BLANK_NODE);
   
            checkResult(tmap.getPredicateObjectMaps().size(), 1);
            checkResult(tmap.getPredicateObjectMaps().get(0).getPredicateMap().getType(), 1);
            checkResult(tmap.getPredicateObjectMaps().get(0).getPredicateMap().getValue(), "http://example.com/ns#fullName");
            checkResult(tmap.getPredicateObjectMaps().get(0).getPredicateMap().getTermType(), R2RmlVocabulary.IRI);
            checkResult(((ObjectMap) tmap.getPredicateObjectMaps().get(0).getObjectMap()).getType(), 3);
            checkResult(((ObjectMap) tmap.getPredicateObjectMaps().get(0).getObjectMap()).getValue(), "{ENAME} {LNAME}");
            checkResult(((ObjectMap) tmap.getPredicateObjectMaps().get(0).getObjectMap()).getTermType(), R2RmlVocabulary.LITERAL);
         }
         else if (id.equals("http://example.com/ns#TriplesMap3")) {
            /*
             * Testing <#TriplesMap3>
             */
            checkResult(tmap.getLogicalTable().getTableView().getSqlQuery(),
                  "select ('Department' || DEPTNO) AS DEPTID, DEPTNO, DNAME, LOC from SCOTT.DEPT");
            checkResult(((R2RmlView) tmap.getLogicalTable().getTableView()).getSqlVersion(), R2RmlVocabulary.MYSQL);
            checkResult(tmap.getSubjectMap().getClassIri(), null);
            checkResult(tmap.getSubjectMap().getType(), 3);
            checkResult(tmap.getSubjectMap().getValue(), "http://data.example.com/employee/{EMPNO}");
   
            checkResult(tmap.getPredicateObjectMaps().size(), 1);
            checkResult(tmap.getPredicateObjectMaps().get(0).getPredicateMap().getType(), 1);
            checkResult(tmap.getPredicateObjectMaps().get(0).getPredicateMap().getValue(), "http://example.com/ns#role");
            checkResult(((ObjectMap) tmap.getPredicateObjectMaps().get(0).getObjectMap()).getType(), 3);
            checkResult(((ObjectMap) tmap.getPredicateObjectMaps().get(0).getObjectMap()).getValue(), "http://data.example.com/roles/{ROLE}");
         }
         else if (id.equals("http://example.com/ns#TriplesMap4")) {
            /*
             * Testing <#TriplesMap4>
             */
            checkResult(tmap.getLogicalTable().getTableView().getSqlQuery(),
                  "select DEPTNO, DNAME, LOC, (select count(*) from EMP where EMP.DEPTNO=DEPT.DEPTNO) as STAFF, DESC from DEPT");
            checkResult(((R2RmlView) tmap.getLogicalTable().getTableView()).getSqlVersion(), null);
            checkResult(tmap.getSubjectMap().getClassIri(), "http://example.com/ns#Department");
            checkResult(tmap.getSubjectMap().getType(), 3);
            checkResult(tmap.getSubjectMap().getValue(), "http://data.example.com/department/{DEPTNO}");
   
            checkResult(tmap.getPredicateObjectMaps().size(), 4);
   
            for (int i = 0; i < tmap.getPredicateObjectMaps().size(); i++) {
               PredicateObjectMap pom = tmap.getPredicateObjectMaps().get(i);
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
         else {
            assertFalse("Unknown mapping was found: " + id, true);;
         }
      }
   }

   private Reader getReader(String filePath)
   {
      try {
         URL url = this.getClass().getResource(filePath);
         return new BufferedReader(new FileReader(url.getFile()));
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
