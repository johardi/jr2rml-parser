package io.github.johardi.r2rmlparser.document;

public interface IDocument
{
   public void accept(IDocumentVisitor visitor);
}
