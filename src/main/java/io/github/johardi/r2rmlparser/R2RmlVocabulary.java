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

import io.github.johardi.r2rmlparser.util.Namespaces;

/**
 * This class contains R2RML vocabulary.
 */
public final class R2RmlVocabulary
{
   /*
    *  R2RML property list. Source: {@link http://www.w3.org/TR/r2rml/#property-index}
    */
   public static final String CHILD = Namespaces.RR + "child";
   public static final String CLASS = Namespaces.RR + "class";
   public static final String COLUMN = Namespaces.RR + "column";
   public static final String DATATYPE = Namespaces.RR + "datatype";
   public static final String CONSTANT = Namespaces.RR + "constant";
   public static final String GRAPH = Namespaces.RR + "graph";
   public static final String GRAPH_MAP = Namespaces.RR + "graphMap";
   public static final String INVERSE_EXPRESSION = Namespaces.RR + "inverseExpression";
   public static final String JOIN_CONDITION = Namespaces.RR + "joinCondition";
   public static final String LANGUAGE = Namespaces.RR + "language";
   public static final String LOGICAL_TABLE = Namespaces.RR + "logicalTable";
   public static final String OBJECT = Namespaces.RR + "object";
   public static final String OBJECT_MAP = Namespaces.RR + "objectMap";
   public static final String PARENT = Namespaces.RR + "parent";
   public static final String PARENT_TRIPLES_MAP = Namespaces.RR + "parentTriplesMap";
   public static final String PREDICATE = Namespaces.RR + "predicate";
   public static final String PREDICATE_MAP = Namespaces.RR + "predicateMap";
   public static final String PREDICATE_OBJECT_MAP = Namespaces.RR + "predicateObjectMap";
   public static final String SQL_QUERY = Namespaces.RR + "sqlQuery";
   public static final String SQL_VERSION = Namespaces.RR + "sqlVersion";
   public static final String SUBJECT = Namespaces.RR + "subject";
   public static final String SUBJECT_MAP = Namespaces.RR + "subjectMap";
   public static final String TABLE_NAME = Namespaces.RR + "tableName";
   public static final String TEMPLATE = Namespaces.RR + "template";
   public static final String TERM_TYPE = Namespaces.RR + "termType";

   /*
    *  R2RML term types.
    */
   public static final String IRI = Namespaces.RR + "IRI";
   public static final String BLANK_NODE = Namespaces.RR + "BlankNode";
   public static final String LITERAL = Namespaces.RR + "Literal";

   /*
    * R2RML SQL Version IRIs. Source:
    * http://www.w3.org/2001/sw/wiki/RDB2RDF/SQL_Version_IRIs
    */
   public static final String SQL2008 = Namespaces.RR + "SQL2008";
   public static final String ORACLE = Namespaces.RR + "Oracle";
   public static final String MYSQL = Namespaces.RR + "MySQL";
   public static final String MSSQLSERVER = Namespaces.RR + "MSSQLServer";
   public static final String HSQLDB = Namespaces.RR + "HSQLDB";
   public static final String POSTGRESQL = Namespaces.RR + "PostgreSQL";
   public static final String DB2 = Namespaces.RR + "DB2";
   public static final String INFORMIX = Namespaces.RR + "Informix";
   public static final String INGRES = Namespaces.RR + "Ingres";
   public static final String PROGRESS = Namespaces.RR + "Progress";
   public static final String SYBASE_ASE = Namespaces.RR + "SybaseASE";
   public static final String SYBASE_SQLANYWHERE = Namespaces.RR + "SybaseSQLAnywhere";
   public static final String VIRTUOSO = Namespaces.RR + "Virtuoso";
   public static final String FIREBIRD = Namespaces.RR + "Firebird";
}
