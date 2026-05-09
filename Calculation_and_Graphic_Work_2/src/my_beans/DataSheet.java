package my_beans;

import java.util.ArrayList;

public class DataSheet
{
    private ArrayList<Data> dataArr;

    public DataSheet()
    {
        dataArr = new ArrayList<>();
    }

    public void addDataItem(Data d)
    {
        dataArr.add(d);
    }

    public void removeDataItem(int index)
    {
        if (index >= 0 && index < dataArr.size())
        {
            dataArr.remove(index);
        }
    }

    public Data getDataItem(int index)
    {
        return dataArr.get(index);
    }

    public int size()
    {
        return dataArr.size();
    }
}