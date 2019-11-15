 
import java.io.*;
import java.net.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;
import java.lang.Runnable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Integer;
import java.rmi.RemoteException;

/*
 * @author Vaishnavi Manjunath
 */

@SuppressWarnings("unused")

public class GroupOneServer
{
    public static ArrayList<GroupOneFileInfo> globalArray = new ArrayList<GroupOneFileInfo>();
 
    @SuppressWarnings("resource")
    public GroupOneServer() throws NumberFormatException, IOException
    {
    	
		
    	ServerSocket serverSocket=null;
    	Socket socket = null;
    	try{
    			serverSocket = new ServerSocket(7799);
    			System.out.println("Server started!! ");
    			System.out.println(" ");
    			System.out.println("Waiting for the Peer to be connected ..");
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
    	while(true)
    	{
    		try{
    				socket = serverSocket.accept();
    				//serverSocket.close();
    		}
    		catch(IOException e)
    		{
    			System.out.println("I/O error: " +e);
    		}
    		new ServerTestClass(socket,globalArray).start();
    	}
    }
}

class ServerTestClass extends Thread
{
	protected Socket socket;
	ArrayList<GroupOneFileInfo> globalArray;
	public ServerTestClass(Socket clientSocket,ArrayList<GroupOneFileInfo> globalArray)
	{
		this.socket=clientSocket;
		this.globalArray=globalArray;
	}

	ArrayList<GroupOneFileInfo> filesList=new ArrayList<GroupOneFileInfo>();
   	ObjectOutputStream oos;
	ObjectInputStream ois;
	String str;
	int index;

    @SuppressWarnings("unchecked")
	public void run()
    {
    	try
    	{  
    		InputStream is=socket.getInputStream();
    		oos = new ObjectOutputStream(socket.getOutputStream());
    		ois = new ObjectInputStream(is);     
    		filesList=(ArrayList<GroupOneFileInfo>)ois.readObject();
    		System.out.println("All the available files from the given directory have been recieved to the Server!");      
    		for(int i=0;i<filesList.size() ;i++)
    		{
    			globalArray.add(filesList.get(i));
    		}
    		System.out.println("Total number of files available in the Server that are received from all the connected peers: " +globalArray.size());
    	}
    	
    	catch(IndexOutOfBoundsException e){
    		System.out.println("Index out of bounds exception");
    	}
    	catch(IOException e){
    		System.out.println("I/O exception");
    	}
    	catch(ClassNotFoundException e){
    		System.out.println("Class not found exception");
    	}
    	
    	try {
    			str = (String) ois.readObject();
    	}
    	catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerTestClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    	
        ArrayList<GroupOneFileInfo> sendingPeers = new ArrayList<GroupOneFileInfo>();
        System.out.println("Searching for the file name...!!!"); 
           
        for(int j=0;j<globalArray.size();j++)
        {
           GroupOneFileInfo fileInfo=globalArray.get(j);
           Boolean tf=fileInfo.fileName.equals(str);
           if(tf)
           {
        	   index = j;
        	   sendingPeers.add(fileInfo);
        	   break;
           }
        }
        
        try {
        	oos.writeObject(sendingPeers);
        } 
        catch (IOException ex) {
         Logger.getLogger(ServerTestClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   

}
    

 

