import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
public class ServerImpl extends UnicastRemoteObject implements ServerInterface 
{
	List<Student> list=new ArrayList<>();
	int count=0;
	public ServerImpl() throws RemoteException 
	{
	}
	
	public void sendData(String data[][],String clgname) throws RemoteException
	{	
		System.out.println("\n\nList from : "+clgname);
		for(int i=0;i<data.length;i++)
		{
			Student s=new Student(data[i][0],data[i][1],data[i][2],clgname);
			list.add(s);
			System.out.println(s.display());
		}
		count ++;
	}
	public boolean isResultReady() throws RemoteException
	{
		if(count>=10)
			return true;
		return false;
	}
	public String getTop5() throws RemoteException
	{
		String data="Still Processing the result..";
		if(isResultReady())
		{
			System.out.println("\n\nTop 5 Students");
			data="";
			sort();
			for(int i=0;i<5;i++)
			{
				Student s=list.get(i);
				System.out.println(s.display());
				data=data+s.id+"\t"+s.name+"\t"+s.marks+"\t"+s.clg+"\n";
				
			}
		}
		return data;
	}
	
	void sort()
	{
		Comparator cmp=new MarksComparator();
		Collections.sort(list,cmp);
	}
	
	class MarksComparator implements Comparator<Student> {  
	    @Override  
	    public int compare(Student obj1, Student obj2) {  
	        return (obj1.marks.compareTo(obj2.marks)>0) ? -1 : (obj1.marks.compareTo(obj2.marks)<0) ? 1 : 0;  
	    }
    }
	
}