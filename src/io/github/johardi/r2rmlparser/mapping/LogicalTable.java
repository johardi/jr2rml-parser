package io.github.johardi.r2rmlparser.mapping;

public class LogicalTable implements IMappingBody
{
   private ITableView mTableView;

   public void setTableView(ITableView tableView)
   {
      mTableView = tableView;
   }

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
