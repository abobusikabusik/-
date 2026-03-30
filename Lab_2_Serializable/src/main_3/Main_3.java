package main_3;

import java.io.*;

public class Main_3
{
    public static void main(String[] args)
    {
        // creating the Library_3 system for the third task
        Library_3 my_Library_3 = new Library_3();

        // creating data
        Author king = new Author("Stephen King");
        Book book1 = new Book("The Shining", king);
        Book book2 = new Book("It", king);

        Reader sofiia = new Reader("Sofia", 101);

        // adding data to the Library_3
        my_Library_3.addBook(book1);
        my_Library_3.addBook(book2);
        my_Library_3.addReader(sofiia);

        // giving a book to a reader
        my_Library_3.takeBook(book1, sofiia);

        System.out.println("Before serialization:");
        System.out.println(my_Library_3);

        String filename = "library_3.dat";

        // saving process using Externalizable
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            out.writeObject(my_Library_3);
            System.out.println("\n[System saved to: " + filename + "]");
        }
        catch (IOException e) 
        { 
            e.printStackTrace(); 
        }

        // restoring process
        Library_3 restored_Library_3 = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename)))
        {
            restored_Library_3 = (Library_3) in.readObject();
            System.out.println("[System restored from file]\n");
        }
        catch (IOException | ClassNotFoundException e) 
        { 
            e.printStackTrace(); 
        }

        System.out.println("After deserialization:");
        if (restored_Library_3 != null)
        {
            System.out.println(restored_Library_3);
        }
    }
}

// all classes implement Externalizable and have a public constructor
class Author implements Externalizable
{
    private String name;

    // required public constructor for Externalizable
    public Author() {}

    public Author(String name) { this.name = name; }

    public String getName() { return name; }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        // manually writing fields
        out.writeObject(name);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        // manually reading fields in the same order
        this.name = (String) in.readObject();
    }

    @Override
    public String toString() { return "Author: " + name; }
}

class Book implements Externalizable
{
    private String title;
    private Author author;

    public Book() {}

    public Book(String title, Author author)
    {
        this.title = title;
        this.author = author;
    }

    public String getTitle() { return title; }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeObject(title);
        out.writeObject(author);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        this.title = (String) in.readObject();
        this.author = (Author) in.readObject();
    }

    @Override
    public String toString() { return "Book: '" + title + "' (" + author + ")"; }
}

class Reader implements Externalizable
{
    private String name;
    private int card_number;

    public Reader() {}

    public Reader(String name, int card_number)
    {
        this.name = name;
        this.card_number = card_number;
    }

    public String getName() { return name; }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeObject(name);
        out.writeInt(card_number);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        this.name = (String) in.readObject();
        this.card_number = in.readInt();
    }

    @Override
    public String toString() { return "Reader: " + name + " [№" + card_number + "]"; }
}

class Loan implements Externalizable
{
    private String book_title;
    private String reader_name;

    public Loan() {}

    public Loan(Book book, Reader reader)
    {
        this.book_title = book.getTitle();
        this.reader_name = reader.getName();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeObject(book_title);
        out.writeObject(reader_name);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        this.book_title = (String) in.readObject();
        this.reader_name = (String) in.readObject();
    }

    @Override
    public String toString() { return reader_name + " borrowed " + book_title; }
}