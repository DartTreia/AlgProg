public class Verticle {
    private String vertex;
    private Verticle[] adjVerticles;
    
    public Verticle(String vertex, Verticle[] adjVerticles) {
        this.vertex = vertex;
        this.adjVerticles = adjVerticles;
    }
    
    public String getVertex() {
        return vertex;
    }
    public Verticle[] getAdjVerticles() {
        return adjVerticles;
    }
    public void setAdjVerticles(Verticle[] adjVerticles) {
        this.adjVerticles = adjVerticles;
    }
    public void setVertex(String vertex) {
        this.vertex = vertex;
    }
}
