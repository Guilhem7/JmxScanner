package fr.jrjgjk;

public final class LocalServer {

    private String host;
    private String port;
    private String endpoint;

    public LocalServer(String host, String port, String endpoint) {
        this.host = host;
        this.port = port;
        this.endpoint = endpoint;
    }

    public void initServer(String host, String port, String endpoint){
    	this.host = host;
    	this.port = port;
    	this.endpoint = endpoint;
    }

    public String getHost(){
    	return this.host;
    }

    public String getPort(){
    	return this.port;
    }

    public String getEndpoint(){
    	return this.endpoint;
    }

    public String getUrl(){
    	return String.format("http://%s:%s/%s", this.host, this.port, this.endpoint);
    }

}