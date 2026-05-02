package udpWork;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.Objects;

public class User implements Serializable
{
    private static final long serialVersionUID = 1L;
    private InetAddress address;
    private int port;

    public User() {}

    public User(InetAddress address, int port)
    {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() { return address; }
    public void setAddress(InetAddress address) { this.address = address; }
    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    @Override
    public String toString()
    {
        return "User{address=" + address.getHostAddress() + ", port=" + port + "}";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return port == user.port && address.equals(user.address);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(address, port);
    }
}