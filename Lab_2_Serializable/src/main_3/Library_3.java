package main_3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library_3 implements Externalizable
{
    private List<Book> books = new ArrayList<>();
    private List<Reader> readers = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();

    public Library_3() {}

    public void addBook(Book b) { books.add(b); }
    public void addReader(Reader r) { readers.add(r); }
    public void takeBook(Book b, Reader r) { loans.add(new Loan(b, r)); }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        // saving our collections manually
        out.writeObject(books);
        out.writeObject(readers);
        out.writeObject(loans);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        // loading collections back
        this.books = (List<Book>) in.readObject();
        this.readers = (List<Reader>) in.readObject();
        this.loans = (List<Loan>) in.readObject();
    }

    @Override
    public String toString()
    {
        return "\t\tLibrary Current State\n" +
                "Books in stock: " + books.size() + "\n" +
                "Total readers: " + readers.size() + "\n" +
                "Books currently borrowed: " + loans.size() + "\n" +
                "Recent transactions: " + loans;
    }
}
