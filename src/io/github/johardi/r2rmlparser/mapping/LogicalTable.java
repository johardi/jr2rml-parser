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

public class LogicalTable implements IMappingBody
{
   private ITableView mTableView;

   public void setTableView(ITableView tableView)
   {
      mTableView = tableView;
   }

   /**
    * Returns the table view specified by this logical table. The table view can
    * be a {@link SqlBaseTableOrView} which specified by
    * <code>rr:tableName</code> property or a {@link R2RmlView} which specified
    * by <code>rr:sqlQuery</code> property.
    */
   public ITableView getTableView()
   {
      return mTableView;
   }

   @Override
   public void accept(IMappingVisitor visitor)
   {
      visitor.visit(this);
   }
}
