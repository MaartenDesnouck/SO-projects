package serialization;
import java.io.*;
import java.util.Objects;

public class Person implements Serializable{ 
	private String firstName,lastName;
	private PhoneNumber number;
	public Person(String firstName, String lastName){
		this.firstName=firstName;
		this.lastName=lastName;
	}
	public Person(String firstName,String lastName,PhoneNumber number){
		this(firstName,lastName);
		this.number=number;
	}
	public String getFirstName(){
		return firstName;
	}
	public String getLastName(){
		return lastName;
	}
	public PhoneNumber getPhoneNumber(){
		return number;
	}
	public void setPhoneNumber(PhoneNumber number){
		this.number=number;
	}
        @Override
	public String toString() {
		return "<"+firstName+" "+lastName+" @ "+number+">";
	}
        @Override
	public boolean equals(Object o){
		if(o instanceof Person) {
			Person p=(Person)o;
			return((p.firstName).equals(firstName)&&(p.lastName).equals(lastName));
		} else return false;
	}
}