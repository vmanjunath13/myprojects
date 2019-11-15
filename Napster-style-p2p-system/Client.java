
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Vaishnavi Manjunath
 */

public class GroupOneClient
{
	@SuppressWarnings({ "unchecked", "rawtypes", "resource", "unused" })
	public GroupOneClient()
	{  
		double starttime;
		double endtime;
		double responsetime = 0;
		Socket socket;
		ArrayList al;  
		ArrayList<GroupOneFileInfo> arrList = new ArrayList<GroupOneFileInfo>();  
		Scanner scanner = new Scanner(System.in);  
		ObjectInputStream ois ;
		ObjectOutputStream oos ;
		String string;
		Object o,b;
		String directoryPath=null;
		int peerServerPort=0;
   
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
  			
			System.out.println("Welcome to the Peer ::");
			System.out.println(" ");
			System.out.println("Enter the directory that contain the files -->");
			directoryPath=br.readLine();
			
			System.out.println("Enter the port number on which the peer should act as server ::");
			peerServerPort=Integer.parseInt(br.readLine());
  
			GroupOneServerDownload objGroupOneServerDownload = new GroupOneServerDownload(peerServerPort,directoryPath);
			objGroupOneServerDownload.start();
			
			Socket clientThread = new Socket("localhost",7799);
			
			ObjectOutputStream objOutStream = new ObjectOutputStream(clientThread.getOutputStream());
			ObjectInputStream objInStream = new ObjectInputStream(clientThread.getInputStream());
			
			al = new ArrayList();  
			
			socket = new Socket("localhost",7799);
			System.out.println("Connection has been established with the Server");
			
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream()); 
			
			System.out.println("Enter the peerid for this directory ::");
			int readpid=Integer.parseInt(br.readLine());
  
			File folder = new File(directoryPath); 
			File[] listofFiles = folder.listFiles();
			GroupOneFileInfo currentFile;
			File file;
			
			for (int i = 0; i < listofFiles.length; i++) {
				currentFile= new GroupOneFileInfo();
				file = listofFiles[i];
				currentFile.fileName=file.getName();  
				currentFile.peerid=readpid;
				currentFile.portNumber=peerServerPort;
				arrList.add(currentFile);
			}
			
			oos.writeObject(arrList);
			//System.out.println("The complete ArrayList :::"+arrList);
			
			System.out.println("Welcome to search and download:\nOptions available: \n1. Search/download file\n2. Test the Average Response Time for a single client performing multiple sequential search Requests");
			int choice = Integer.parseInt(br.readLine());
			System.out.println("Enter the desired file name that you want to search from the list of the files available in the Server ::");
			String fileNameToDownload = br.readLine();
			oos.writeObject(fileNameToDownload);
			
			System.out.println("Waiting for the reply from Server...!!");
			
			ArrayList<GroupOneFileInfo> peers= new ArrayList<GroupOneFileInfo>();
			peers = (ArrayList<GroupOneFileInfo>)ois.readObject();
			
			switch (choice) {
			case 1:
				// Option to Search and Download the file
				for(int i=0;i<peers.size();i++)
				{  
					starttime = System.nanoTime();
					int result = peers.get(i).peerid;
					int port = peers.get(i).portNumber;
					System.out.println("The file is stored at peer id " +result+ " on port "+port);
					endtime = System.nanoTime() - starttime;
					responsetime = +endtime;
				}
				System.out.println("Time taken to search the requested file: " + responsetime + " nanoseconds.");
				
				System.out.println("Do you want to download the file you searched? Please reply Yes or No:");
				String replyChoice = br.readLine();
				String replyChoiceCase = replyChoice.toLowerCase();
				if (replyChoiceCase.equals("yes")) {
					System.out.println("Enter the respective port number of the above peer id :");
					int clientAsServerPortNumber = Integer.parseInt(br.readLine());
				
					System.out.println("Enter the desired peer id from which you want to download the file from :");
					int clientAsServerPeerid = Integer.parseInt(br.readLine());
					
					starttime = System.nanoTime();
					clientAsServer(clientAsServerPeerid,clientAsServerPortNumber,fileNameToDownload,directoryPath);
					endtime = System.nanoTime() - starttime;
					responsetime = +endtime;
					System.out.println("Time taken for downloading file: " + responsetime + " nanoseconds.");
					break;
				} 
				else 
				{
					System.out.println("Requested file - "+fileNameToDownload+ ", has been found in above Peer");
					System.out.println(" ");
				}
				break;
			case 2:
				// Option to test Average Response Time
				System.out.println("Number of Sequential requests: ");
				int number = Integer.parseInt(br.readLine());
				starttime = System.nanoTime();
				for (int i = 0; i < number; i++) {
					for(int j=0;j<peers.size();j++)
					{  
						int result = peers.get(j).peerid;
						int port = peers.get(j).portNumber;
					}	
				}
				endtime = System.nanoTime() - starttime;
				responsetime = +endtime;
				System.out.println("Average Response time for " + number + " sequential search requests: "
						+ responsetime / number + " nanoseconds.");
				break;
			default:
				break;
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println("Error in establishing the Connection between the Peer and the Server!! ");
			System.out.println("Please cross-check the host address and the port number..");
		}
	}
	
	
	public static void clientAsServer(int clientAsServerPeerid, int clientAsServerPortNumber, String fileNamedwld, String directoryPath) throws ClassNotFoundException
	{   
		try {
				@SuppressWarnings("resource")
				Socket clientAsServersocket = new Socket("localhost",clientAsServerPortNumber);
				
				ObjectOutputStream clientAsServerOOS = new ObjectOutputStream(clientAsServersocket.getOutputStream());
				ObjectInputStream clientAsServerOIS = new ObjectInputStream(clientAsServersocket.getInputStream());
				
				clientAsServerOOS.writeObject(fileNamedwld);
				int readBytes=(int) clientAsServerOIS.readObject();
				
				//System.out.println("Number of bytes that have been transferred are ::"+readBytes);
				
				byte[] b=new byte[readBytes];
				clientAsServerOIS.readFully(b);
				OutputStream  fileOPstream = new FileOutputStream(directoryPath+"//"+fileNamedwld);
				
				@SuppressWarnings("resource")
				
				BufferedOutputStream BOS = new BufferedOutputStream(fileOPstream);
				BOS.write(b, 0,(int) readBytes);
				
				System.out.println("Requested file - "+fileNamedwld+ ", has been downloaded to your desired directory "+directoryPath);
				System.out.println(" ");
				System.out.println("Display file "+fileNamedwld);
				
				BOS.flush();
		} 
		catch (IOException ex) {
        Logger.getLogger(GroupOneClient.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

