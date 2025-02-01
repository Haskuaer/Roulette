package ef.client.services;

import ef.client.util.ClientSocket;
import ef.client.util.SceneManager;

public class Service {

    private ClientSocket clientSocket;
    private SceneManager sceneManager;

    public Service(ClientSocket clientSocket, SceneManager sceneManager)
    {
        this.clientSocket = clientSocket;
        this.sceneManager = sceneManager;
    }

    public Service() {

    }

    public ClientSocket getClientSocket(){ return clientSocket; }
    public void setClientSocket(ClientSocket clientSocket){ this.clientSocket = clientSocket; }

    public SceneManager getSceneManager(){ return sceneManager; }
    public void setSceneManager(SceneManager sceneManager){ this.sceneManager = sceneManager; }
}
