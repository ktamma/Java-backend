package servlet;

public class OrderId {
    private Long id = 0L;
    public Long increase(){
        id++;
        return id;
    }
}
