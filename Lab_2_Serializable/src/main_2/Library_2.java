package main_2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Library_2 implements Serializable
{
    // transient - invisible for serialization
    private transient List<Book> books = new ArrayList<>();
    private transient List<Reader> readers = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();

    public void addBook(Book b) { books.add(b); }

    public void addReader(Reader r) { readers.add(r); }

    public void takeBook(Book b, Reader r) { loans.add(new Loan(b, r)); }

    private void writeObject(ObjectOutputStream out) throws IOException
    {
        // save any regular (non-transient) fields first
        out.defaultWriteObject();

        // manually saving books data
        out.writeInt(books.size());
        for (Book b : books)
        {
            out.writeObject(b.getTitle());
            out.writeObject(b.getAuthor().getName());
        }

        // manually saving readers data
        out.writeInt(readers.size());
        for (Reader r : readers)
        {
            out.writeObject(r.getName());
            out.writeInt(r.getId());
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        // load regular fields
        in.defaultReadObject();

        // recreating books
        this.books = new ArrayList<>();
        int bookCount = in.readInt();
        for (int i = 0; i < bookCount; i++)
        {
            String title = (String) in.readObject();
            String authorName = (String) in.readObject();
            this.addBook(new Book(title, new Author(authorName)));
        }

        // recreating readers
        this.readers = new ArrayList<>();
        int readerCount = in.readInt();
        for (int i = 0; i < readerCount; i++)
        {
            String name = (String) in.readObject();
            int id = in.readInt();
            this.addReader(new Reader(name, id));
        }
    }

    @Override
    public String toString()
    {
        return "\t\tLibrary_2 Current State\n" +
                "Books in stock: " + books.size() + "\n" +
                "Total readers: " + readers.size() + "\n" +
                "Books currently borrowed: " + loans.size() + "\n" +
                "Recent transactions: " + loans;
    }
}
