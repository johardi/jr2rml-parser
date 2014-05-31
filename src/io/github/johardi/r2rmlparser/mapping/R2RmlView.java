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
package io.github.johardi.r2rmlparser.mapping;

public class R2RmlView implements ITableView
{
   private String mSqlString;

   public void setSqlString(String sqlString)
   {
      mSqlString = sqlString;
   }

   /**
    * Returns the value specified by <code>rr:sqlQuery</code> property.
    */
   @Override
   public String getSqlQuery()
   {
      return mSqlString;
   }
}
