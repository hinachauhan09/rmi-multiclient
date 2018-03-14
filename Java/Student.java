public class Student
{
	String id,name,marks,clg;
	Student(String id,String name,String marks,String clg)
	{
		this.id=id;
		this.name=name;
		this.marks=marks;
		this.clg=clg;
	}
	String display()
	{
		return id+" "+name+" "+marks+" "+clg;
	}
}
