package main_1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Library_1 implements Serializable
{
    private List<Book> books = new ArrayList<>();
    private List<Reader> readers = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();

    public void addBook(Book book) { books.add(book); }
    public void addReader(Reader reader) { readers.add(reader); }
    public void takeBook(Book book, Reader reader) {
        loans.add(new Loan(book, reader));
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
