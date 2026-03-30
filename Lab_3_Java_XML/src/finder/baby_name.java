package finder;

// class to store baby data
public class baby_name {
    private String name;
    private String gender;
    private int count;
    private int rank;

    public baby_name(String name, String gender, int count, int rank)
    {
        this.name = name;
        this.gender = gender;
        this.count = count;
        this.rank = rank;
    }

    public String getName() { return name; }
    public String getGender() { return gender; }
    public int getCount() { return count; }
    public int getRank() { return rank; }
}
