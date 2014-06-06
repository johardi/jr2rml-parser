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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.helpers.RDFHandlerBase;

import io.github.johardi.r2rmlparser.document.LogicalTable;
import io.github.johardi.r2rmlparser.document.ObjectMap;
import io.github.johardi.r2rmlparser.document.PredicateMap;
import io.github.johardi.r2rmlparser.document.PredicateObjectMap;
import io.github.johardi.r2rmlparser.document.R2RmlView;
import io.github.johardi.r2rmlparser.document.SqlBaseTableOrView;
import io.github.johardi.r2rmlparser.document.SubjectMap;
import io.github.johardi.r2rmlparser.document.TermMap;
import io.github.johardi.r2rmlparser.document.TriplesMap;
import io.github.johardi.r2rmlparser.exception.InvalidPropertyAttributeException;
import io.github.johardi.r2rmlparser.exception.UnsupportedPropertyException;
import io.github.johardi.r2rmlparser.util.MultiMap;

public class R2RmlHandler extends RDFHandlerBase implements IR2RmlConstants
{
   private Set<String> mTriplesMapIds = new HashSet<String>();

   private List<TriplesMap> mTriplesMaps = new ArrayList<TriplesMap>();
   private Map<String, String> mPrefixMapper = new HashMap<String, String>();

   private MultiMap<String, PropertyGraph> mPropertyGraphs;

   public List<TriplesMap> getTriplesMaps()
   {
      return mTriplesMaps;
   }

   public Map<String, String> getPrefixMapper()
   {
      return mPrefixMapper;
   }

   @Override
   public void handleNamespace(String prefix, String uri)
   {
      mPrefixMapper.put(prefix, uri);
   }

   @Override
   public void startRDF() throws RDFHandlerException
   {
      mPropertyGraphs = new MultiMap<String, PropertyGraph>(true);
   }

   @Override
   public void endRDF() throws RDFHandlerException
   {
      for (String triplesMapId : mTriplesMapIds) {
         TriplesMap triplesMap = createTriplesMap();
         triplesMap.setId(triplesMapId);
         Collection<PropertyGraph> properties = mPropertyGraphs.get(triplesMapId);
         for (PropertyGraph property : properties) {
            switch (property.kind()) {
               case K_LOGICAL_TABLE:
                  logicalTable(triplesMap, property.graph());
                  break;
               case K_SUBJECT_MAP:
                  subjectMap(triplesMap, property.graph());
                  break;
               case K_PREDICATE_OBJECT_MAP:
                  predicateObjectMap(triplesMap, property.graph());
                  break;
            }
         }
         mTriplesMaps.add(triplesMap);
      }
   }

   private void logicalTable(TriplesMap triplesMap, Statement graph)
   {
      LogicalTable logicalTable = new LogicalTable();
      String bNodeId = getObject(graph);
      Collection<PropertyGraph> properties = mPropertyGraphs.get(bNodeId);
      for (PropertyGraph property : properties) {
         switch (property.kind()) {
            case K_TABLE_NAME:
               SqlBaseTableOrView table = new SqlBaseTableOrView();
               table.setBaseTable(getObject(property.graph()));
               logicalTable.setTableView(table);
               break;
            case K_SQL_QUERY:
               R2RmlView sqlQuery = new R2RmlView();
               String sql = getObject(property.graph());
               sqlQuery.setSqlString(sql.replaceAll("\\s+|\\n+", " ").trim()); // replace any ws and newlines to space
               logicalTable.setTableView(sqlQuery);
               break;
            default:
               throw invalidPropertyAttributeException(property, "rr:logicalTable", triplesMap.getId());
         }
      }
      triplesMap.setLogicalTable(logicalTable);
   }

   private void subjectMap(TriplesMap triplesMap, Statement graph)
   {
      SubjectMap subjectMap = new SubjectMap();
      String bNodeId = getObject(graph);
      Collection<PropertyGraph> properties = mPropertyGraphs.get(bNodeId);
      for (PropertyGraph property : properties) {
         switch (property.kind()) {
            case K_CLASS:
               subjectMap.setClassIri(getObject(property.graph()));
               break;
            case K_TEMPLATE:
               subjectMap.setType(TermMap.TEMPLATE_VALUE);
               subjectMap.setValue(getObject(property.graph()));
               break;
            default:
               throw invalidPropertyAttributeException(property, "rr:subjectMap", triplesMap.getId());
         }
      }
      triplesMap.setSubjectMap(subjectMap);
   }

   private void predicateObjectMap(TriplesMap triplesMap, Statement graph)
   {
      PredicateObjectMap predicateObjectMap = new PredicateObjectMap();
      String bNodeId = getObject(graph);
      Collection<PropertyGraph> properties = mPropertyGraphs.get(bNodeId);
      for (PropertyGraph property : properties) {
         switch (property.kind()) {
            case K_PREDICATE_MAP:
               predicateMap(predicateObjectMap, property.graph(), triplesMap);
               break;
            case K_PREDICATE:
               predicate(predicateObjectMap, getObject(property.graph()));
               break;
            case K_OBJECT_MAP:
               objectMap(predicateObjectMap, property.graph(), triplesMap);
               break;
            case K_OBJECT:
               object(predicateObjectMap, getObject(property.graph()));
               break;
            default:
               throw invalidPropertyAttributeException(property, "rr:predicateObjectMap", triplesMap.getId());
         }
      }
      triplesMap.addPredicateObjectMap(predicateObjectMap);
   }

   private void predicateMap(PredicateObjectMap predicateObjectMap, Statement graph, TriplesMap triplesMap)
   {
      PredicateMap predicateMap = new PredicateMap();
      String bNodeId = getObject(graph);
      Collection<PropertyGraph> properties = mPropertyGraphs.get(bNodeId);
      for (PropertyGraph property : properties) {
         switch (property.kind()) {
            case K_CONSTANT:
               predicateMap.setType(TermMap.CONSTANT_VALUE);
               predicateMap.setValue(getObject(property.graph()));
               break;
            default:
               throw invalidPropertyAttributeException(property, "rr:predicateMap", triplesMap.getId());
         }
      }
      predicateObjectMap.setPredicateMap(predicateMap);
   }

   private void predicate(PredicateObjectMap predicateObjectMap, String predicateName)
   {
      PredicateMap predicateMap = new PredicateMap();
      predicateMap.setType(TermMap.CONSTANT_VALUE);
      predicateMap.setValue(predicateName);
      predicateObjectMap.setPredicateMap(predicateMap);
   }

   private void objectMap(PredicateObjectMap predicateObjectMap, Statement graph, TriplesMap triplesMap)
   {
      ObjectMap objectMap = new ObjectMap();
      String bNodeId = getObject(graph);
      Collection<PropertyGraph> properties = mPropertyGraphs.get(bNodeId);
      for (PropertyGraph property : properties) {
         switch (property.kind()) {
            case K_COLUMN:
               objectMap.setType(TermMap.COLUMN_VALUE);
               objectMap.setValue(getObject(property.graph()));
               break;
            case K_TEMPLATE:
               objectMap.setType(TermMap.TEMPLATE_VALUE);
               objectMap.setValue(getObject(property.graph()));
               break;
            case K_CONSTANT:
               objectMap.setType(TermMap.CONSTANT_VALUE);
               objectMap.setValue(getObject(property.graph()));
               break;
            case K_DATATYPE:
               objectMap.setDatatype(getObject(property.graph()));
               break;
            case K_LANGUAGE:
               objectMap.setLanguage(getObject(property.graph()));
               break;
            default:
               throw invalidPropertyAttributeException(property, "rr:objectMap", triplesMap.getId());
         }
      }
      predicateObjectMap.setObjectMap(objectMap);
   }

   private void object(PredicateObjectMap predicateObjectMap, String constantValue)
   {
      ObjectMap objectMap = new ObjectMap();
      objectMap.setType(TermMap.CONSTANT_VALUE);
      objectMap.setValue(constantValue);
      predicateObjectMap.setObjectMap(objectMap);
   }

   @Override
   public void handleStatement(Statement graph) throws RDFHandlerException
   {
      findTriplesMapId(graph);
      
      String subjectId = getSubject(graph);
      String predicateName = getPredicate(graph);
      
      if (predicateName.equals(R2RmlVocabulary.LOGICAL_TABLE)) {
         mPropertyGraphs.put(subjectId, new PropertyGraph(K_LOGICAL_TABLE, "rr:logicalTable", graph));
      }
      else if (predicateName.equals(R2RmlVocabulary.SUBJECT_MAP)) {
         mPropertyGraphs.put(subjectId, new PropertyGraph(K_SUBJECT_MAP, "rr:subjectMap", graph));
      }
      else if (predicateName.equals(R2RmlVocabulary.PREDICATE_OBJECT_MAP)) {
         mPropertyGraphs.put(subjectId, new PropertyGraph(K_PREDICATE_OBJECT_MAP, "rr:predicateObjectMap", graph));
      }
      else if (predicateName.equals(R2RmlVocabulary.PREDICATE_MAP)) {
         mPropertyGraphs.put(subjectId, new PropertyGraph(K_PREDICATE_MAP, "rr:predicateMap", graph));
      }
      else if (predicateName.equals(R2RmlVocabulary.OBJECT_MAP)) {
         mPropertyGraphs.put(subjectId, new PropertyGraph(K_OBJECT_MAP, "rr:ObjectMap", graph));
      }
      else if (predicateName.equals(R2RmlVocabulary.PREDICATE)) {
         mPropertyGraphs.put(subjectId, new PropertyGraph(K_PREDICATE, "rr:predicate", graph));
      }
      else if (predicateName.equals(R2RmlVocabulary.OBJECT)) {
         mPropertyGraphs.put(subjectId, new PropertyGraph(K_OBJECT, "rr:Object", graph));
      }
      else if (predicateName.equals(R2RmlVocabulary.TABLE_NAME)) {
         mPropertyGraphs.put(subjectId, new PropertyGraph(K_TABLE_NAME, "rr:tableName", graph));
      }
      else if (predicateName.equals(R2RmlVocabulary.SQL_QUERY)) {
         mPropertyGraphs.put(subjectId, new PropertyGraph(K_SQL_QUERY, "rr:sqlQuery", graph));
      }
      else if (predicateName.equals(R2RmlVocabulary.CLASS)) {
         mPropertyGraphs.put(subjectId, new PropertyGraph(K_CLASS, "rr:class", graph));
      }
      else if (predicateName.equals(R2RmlVocabulary.COLUMN)) {
         mPropertyGraphs.put(subjectId, new PropertyGraph(K_COLUMN, "rr:column", graph));
      }
      else if (predicateName.equals(R2RmlVocabulary.TEMPLATE)) {
         mPropertyGraphs.put(subjectId, new PropertyGraph(K_TEMPLATE, "rr:template", graph));
      }
      else if (predicateName.equals(R2RmlVocabulary.CONSTANT)) {
         mPropertyGraphs.put(subjectId, new PropertyGraph(K_CONSTANT, "rr:constant", graph));
      }
      else if (predicateName.equals(R2RmlVocabulary.DATATYPE)) {
         mPropertyGraphs.put(subjectId, new PropertyGraph(K_DATATYPE, "rr:datatype", graph));
      }
      else if (predicateName.equals(R2RmlVocabulary.LANGUAGE)) {
         mPropertyGraphs.put(subjectId, new PropertyGraph(K_LANGUAGE, "rr:language", graph));
      }
      else if (predicateName.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")) {
         // NO-OP: ignore rdf:type
      }
      else {
         throw new RDFHandlerException(new UnsupportedPropertyException(predicateName));
      }
   }

   private void findTriplesMapId(Statement graph)
   {
      if (graph.getSubject() instanceof URI) {
         mTriplesMapIds.add(getSubject(graph)); // set will discard duplicates
      }
   }

   private TriplesMap createTriplesMap()
   {
      return new TriplesMap();
   }

   private String getSubject(Statement graph)
   {
      return graph.getSubject().stringValue();
   }

   private String getPredicate(Statement graph)
   {
      return graph.getPredicate().stringValue();
   }

   private String getObject(Statement graph)
   {
      return graph.getObject().stringValue();
   }

   private RuntimeException invalidPropertyAttributeException(PropertyGraph property, String parent, String triplesMapId)
   {
      InvalidPropertyAttributeException exception = new InvalidPropertyAttributeException(property.label());
      exception.setExceptionDetail(String.format("(at %s in <%s>)", parent, shortenIfNecessary(triplesMapId)));
      return exception;
   }

   private String shortenIfNecessary(String triplesMapId)
   {
      if (triplesMapId.contains("#")) {
         triplesMapId = triplesMapId.substring(triplesMapId.lastIndexOf("#") + 1);
      }
      else if (triplesMapId.contains("/")) {
         triplesMapId = triplesMapId.substring(triplesMapId.lastIndexOf("/") + 1);
      }
      return triplesMapId;
   }

   /**
    * A helper class to store the graph statement with the identified property kind.
    */
   private class PropertyGraph
   {
      private int mKind;
      private String mLabel;
      private Statement mGraph;
      
      public PropertyGraph(int kind, String label, Statement graph)
      {
         mKind = kind;
         mLabel = label;
         mGraph = graph;
      }
      
      public int kind()
      {
         return mKind;
      }
      
      public String label()
      {
         return mLabel;
      }
      
      public Statement graph()
      {
         return mGraph;
      }
   }
}
