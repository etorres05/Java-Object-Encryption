package p4;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A helper class for your gradebook
 * Some of these methods may be useful for your program
 * You can remove methods you do not need
 * If you do not wish to use a Gradebook object, don't
 */
public class Gradebook implements Serializable{
	
	//default serial version
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Assignment> assignments ;
	private ArrayList<Student> students;
	private String gradebookName;

	/* Create a new gradebook */
  public Gradebook(String name) {
	  assignments = new ArrayList<Assignment>();
	  students = new ArrayList<Student>();
	  gradebookName = name;
  }

  /* Adds a student to the gradebook */
  public void addStudent(Student s) {
	  students.add(s);
	  
	  //add student to assignments lists
	  for(Assignment a : assignments) {
		  //set all student grades to zero until specified
		  a.addGrade(s, 0);
	  }
  }

  /* Adds an assignment to the gradebook */
  public void addAssignment(Assignment a) {
	  /*add assignment if it doesn't already exist*/
	  if(!assignmentExists(a.getAsName())) {
		  
		  assignments.add(a);
		  
		  for(Student s : students) {
			  //set all student grades to zero until specified
			  s.addGrade(a, 0);
		  }
	  }
	  else {
		  System.out.println("Invalid");
		  System.exit(255);
	  }
  }
  
	public boolean assignmentExists(String asName) {
		// TODO Auto-generated method stub
		boolean exists = false;
		
		for(Assignment assign : assignments) {
			if (assign.getAsName().equals(asName)) {
				  exists = true;
			  }
		  }
		
		return exists;
	}

	public double getTotalWeight() {
		// TODO Auto-generated method stub
		double totalWeight = 0;
		
		for(Assignment assig : assignments)
			totalWeight += assig.getWeight();
			
		return totalWeight;
	}

	public void delAssignment(String name) {
		// Remove assignment from assignments list
		Assignment assign = getAssignment(name);
		assignments.remove(assign);
		
		// Remove assignment from student assignment list
		for(Student s: students)
		{
			//delete assignment from all student profiles
			s.delAssignment(assign);
		}
	}

	public boolean studentExists(String fName, String lName) {
		// TODO Auto-generated method stub
		boolean exists = false;
		
		for(Student s : students) {
			  if (s.getFName().equals(fName) && s.getLName().equals(lName)) {
				  exists = true;
			  }
		  }
		
		return exists;
	}

	public void delStudent(String fName, String lName) {
		// remove student from student list
		Student s = getStudent(fName, lName);
		students.remove(s);
		
		//remove student from assignments
		for(Assignment as : assignments)
		{
			as.removeStudent(s);
		}
	}

	public void gradeAssignment(String fName, String lName, String asName, int grade) {
		// TODO Auto-generated method stub
		Student student = getStudent(fName, lName);
		Assignment assign = getAssignment(asName);
		
		student.addGrade(assign, grade);
		assign.addGrade(student, grade);
	}

	public Assignment getAssignment(String asName) {
		// TODO Auto-generated method stub
		Assignment a = null;
		for(Assignment assign : assignments) {
			  if (assign.getAsName().equals(asName)) {
				  a = assign;
			  }
		  }
		return a;
	}
	
	public ArrayList<Assignment> getAssignmentList()
	{
		return assignments;
	}
	public ArrayList<Student> getStudentList()
	{
		return students;
	}

	public Student getStudent(String fName, String lName) {
		// TODO Auto-generated method stub
		Student found = null;
		for(Student s : students) {
			  if (s.getFName().equals(fName) && s.getLName().equals(lName)) {
				  found = s;
			  }
		  }
		return found;
	}

	public String getGbName() {
		// TODO Auto-generated method stub
		return gradebookName;
	}
	
}

