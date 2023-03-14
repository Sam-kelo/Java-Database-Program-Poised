 package poisedProjectDatabase;
 import java.io.File;
import java.io.PrintWriter;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Date;
import java.io.FileNotFoundException;
import java.text.ParseException;

public class PoisedProjectDatabase {
	
	public static void main(String[] args) throws ParseException, FileNotFoundException {
	
		String Completed = "Yes";	
		String answer;
		int choice = 0;

		try {
			// Connecting to the ebookstore database.
			// New user logins name "otheruser" and password "helloworld"
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/poisepms?useSSL=false&allowPublicKeyRetrieval=true",
					"otheruser", 
					"helloworld");

			// Creating a direct line to the database for running queries.
			Statement statement = connection.createStatement();
			ResultSet results;
			int rowsAffected;
			// Main menu
			do {
				System.out.println("Menu: ");
				System.out.println("1. View current projects: ");
				System.out.println("2. Add a project: ");
				System.out.println("3. Add third party details");
				System.out.println("4. Update a project: ");
				System.out.println("5. Update structural engineer contact details: ");
				System.out.println("6. View incomplete, overdue and complete projects: ");
				System.out.println("7. Generate an invoice and finalise a project: ");
				System.out.println("8. Search projects");
				System.out.println("9. Delete a project");

				Scanner sc = new Scanner(System.in);
				System.out.println("Enter choice here: ");
				choice = sc.nextInt();
				// Switch case for user selection.
				switch(choice) {
				// Viewing all current projects.
				case 1:

					results = statement.executeQuery("SELECT Project_Number, ERF_Number, Project_name, Project_Address, Project_Type, Due_Date, "
							+ "Completed, Date_of_Completion FROM projects");
					System.out.println("Projects\n");
					while (results.next()) {
						System.out.println(results.getInt("Project_Number")+", "+results.getInt("ERF_Number")+", "+results.getString("Project_Name")+", "+results.getString("Project_Address")+", "+results.getString("Project_Type")+
								", "+results.getString("Due_Date")+", "+results.getString("Completed")+", "+results.getString("Date_of_Completion"));		
					}

					break;
				case 2:
					
					try {
						// Taking in new project details.
						System.out.println("Add project number: ");
						Scanner project_num  = new Scanner(System.in);
						int Project_Number = project_num.nextInt();
						try { 
							System.out.println("Add ERF number: ");
							Scanner erf_num = new Scanner(System.in);
							int ERF_Number = erf_num.nextInt();

							System.out.println("Add project name: ");
							Scanner projectName  = new Scanner(System.in);
							String Project_Name = projectName.nextLine();

							System.out.println("Add project address: ");
							Scanner projectAddress  = new Scanner(System.in);
							String Project_Address = projectAddress.nextLine();

							System.out.println("Add project type: ");
							Scanner projectType  = new Scanner(System.in);
							String Project_Type = projectAddress.nextLine();
							try {
								System.out.println("Add due date(yyyy-MM-dd): ");
								Scanner duedate  = new Scanner(System.in);
								String Due_Date = duedate.nextLine();
								SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
								java.util.Date firstDate = df.parse(Due_Date);
								// Creating a new row.	
								rowsAffected = statement.executeUpdate("INSERT INTO projects VALUES('"+Project_Number+"', '"+ERF_Number+"', '"+Project_Name+"', '"+
										Project_Address+"', '"+Project_Type+"', '"+Due_Date+"', '"+"No"+"', "+ null+");");

								System.out.println("\nProject added.");

							}catch(ParseException e) {
								System.out.println("Please enter a valid date.");
							}
						}catch(InputMismatchException e) {

							System.out.println("Incorrect input. Please enter a valid ERF number.");
						}	
					}catch(InputMismatchException e) {

						System.out.println("Incorrect input. Please enter a valid project number.");
					}	

					break;

				case 3:

					try {
						// Add customer details.
						System.out.println("Enter the customers project number : ");
						Scanner CustomerProjNum = new Scanner(System.in);
						int CustomerProjectNumber = CustomerProjNum.nextInt();		

						System.out.println("Enter customer name: ");
						Scanner projCustomer = new Scanner(System.in);
						String CustomerName = projCustomer.nextLine();

						System.out.println("Enter customer telephone number.(10 digits maximum starting with 0.eg-0112685824): ");
						Scanner projCustomerTelephone = new Scanner(System.in);
						String CustomerTelephoneNumber = projCustomerTelephone.nextLine();		

						System.out.println("Enter customer email address: ");
						Scanner projCustomerEmail = new Scanner(System.in);
						String CustomerEmailAddress = projCustomerEmail.nextLine();	

						rowsAffected = statement.executeUpdate("INSERT INTO customer VALUES('"+CustomerProjectNumber+"', '"+CustomerName+"', '"+
								CustomerTelephoneNumber+"', '"+CustomerEmailAddress+"');");

						System.out.println("Customer added.");	
						try {
							// Add project manager details.
							System.out.println("Enter the project managers project number: ");
							Scanner PMmanagerProjNum = new Scanner(System.in);
							int ProjectManagerProjectNumber= PMmanagerProjNum.nextInt();	

							System.out.println("Enter the project managers name: ");
							Scanner projProjectManagerName= new Scanner(System.in);
							String ProjectManagerName = projProjectManagerName.nextLine();	

							System.out.println("Enter the project managers telephone number.(10 digits maximum starting with 0.eg-0112685824): ");
							Scanner projProjectManagerTelephone = new Scanner(System.in);
							String ProjectManagerTelephoneNumber = projProjectManagerTelephone.nextLine();	

							System.out.println("Enter the project managers email address: ");
							Scanner projProjectManagerEmail = new Scanner(System.in);
							String ProjectManagerEmailAddress =  projProjectManagerEmail.nextLine();

							rowsAffected = statement.executeUpdate("INSERT INTO project_manager VALUES('"+ProjectManagerProjectNumber+"', '"+ProjectManagerName+"', '"+
									ProjectManagerTelephoneNumber+"', '"+ProjectManagerEmailAddress+"');");
							System.out.println("Project manager added.");
							try {
								// Add architect details.
								System.out.println("Enter the architects project number: ");
								Scanner projArchitectNum = new Scanner(System.in);
								int ArchitectProjectNumber = projArchitectNum .nextInt();	

								System.out.println("Enter the architects name: ");
								Scanner projArchitect = new Scanner(System.in);
								String architectName = projArchitect.nextLine();		

								System.out.println("Enter the architects telephone number.(10 digits maximum starting with 0.eg-0112685824): ");
								Scanner projArchitectTelephone = new Scanner(System.in);
								String architectTelephoneNumber = projArchitectTelephone.nextLine();

								System.out.println("Enter the architects email address: ");
								Scanner projArchitectEmail = new Scanner(System.in);
								String architectEmailAddress = projArchitectEmail.nextLine();

								rowsAffected = statement.executeUpdate("INSERT INTO architect VALUES('"+ArchitectProjectNumber+"', '"+architectName+"', '"+
										architectTelephoneNumber+"', '"+architectEmailAddress+"');");
								System.out.println("Architect added.");
								try {
									// Add structural engineer details.
									System.out.println("Enter the structural engineers project number: ");
									Scanner projStructuralEngineerNum = new Scanner(System.in);
									int StructuralEngineerNumber =  projStructuralEngineerNum.nextInt();	

									System.out.println("Enter the structural engineers name: ");
									Scanner projStructuralEngineer = new Scanner(System.in);
									String StructuralEngineerName = projArchitect.nextLine();

									System.out.println("Enter the structural engineers telephone number.(10 digits maximum starting with 0.eg-0112685824): ");
									Scanner projSEtelephoneNum = new Scanner(System.in);
									String SEtelephoneNumber = projStructuralEngineerNum .nextLine();

									System.out.println("Enter the structural engineers email address: ");
									Scanner SEemail = new Scanner(System.in);
									String SEemailAddress = projArchitectEmail.nextLine();	

									rowsAffected = statement.executeUpdate("INSERT INTO structural_engineer VALUES('"+StructuralEngineerNumber+"', '"+StructuralEngineerName+"', '"+
											SEtelephoneNumber+"', '"+SEemailAddress+"');");
									System.out.println("Structural engineer added.");

								}catch(InputMismatchException e) {
									System.out.println("Please enter a valid project number");
								}
							}catch(InputMismatchException e) {
								System.out.println("Please enter a valid project number");
							}	
						}catch(InputMismatchException e) {
							System.out.println("Please enter a valid project number");
						}
					}catch(InputMismatchException e) {
						System.out.println("Please enter a valid project number");
					}

					break;

				case 4:

					String user_Choice = "";
					int selector = 0;
					int choose = 0;
					try {
						try {
							do {
								System.out.println("Enter a project number: ");
								Scanner edit = new Scanner(System.in);
								selector = edit.nextInt();
								// User option to choose what to update.
								System.out.println("Would you like to update the project name-1, project type-2, project address-3, project due date-4:");
								Scanner scanner = new Scanner(System.in);
								choose = scanner.nextInt();

								if(choose == 1) {
									System.out.println("Change project name:");
									Scanner proj_name = new Scanner(System.in);
									String newName = proj_name.nextLine();
									rowsAffected = statement.executeUpdate("UPDATE projects SET Project_Name='"+newName+"' "+" WHERE Project_Number= "+selector);
									System.out.println("Query handled, " + rowsAffected + " rows updated.");
								}

								else if(choose == 2) {

									System.out.println("Change project type:");
									Scanner proj_type = new Scanner(System.in);
									String newType = proj_type.nextLine();
									rowsAffected = statement.executeUpdate("UPDATE projects SET Project_Type='"+newType+"' "+" WHERE Project_Number= "+selector);
									System.out.println("Query handled, " + rowsAffected + " rows updated.");
								}

								else if(choose == 3) {

									System.out.println("Change project address:");
									Scanner proj_address = new Scanner(System.in);
									String newAddress = proj_address.nextLine();
									rowsAffected = statement.executeUpdate("UPDATE projects SET Project_Address='"+newAddress+"' "+" WHERE Project_Number= "+selector);
									System.out.println("Query handled, " + rowsAffected + " rows updated.");
								}

								else if(choose == 4) {

									System.out.println("Change due date(yyyy-MM-dd):" );
									Scanner date = new Scanner(System.in);
									String newDate = date.nextLine();
									SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
									java.util.Date updateDate = sdf.parse(newDate);

									rowsAffected = statement.executeUpdate("UPDATE projects SET Due_Date='"+newDate+"' "+" WHERE Project_Number= "+selector);
									System.out.println("Query handled, " + rowsAffected + " rows updated.");
								}

								else{
									System.out.println("Please enter a valid choice");
								}

								System.out.println("Would you like to continue to update? Click \"y\" for yes and \"n\" for no: ");
								Scanner s = new Scanner(System.in);
								user_Choice = s.nextLine();

								if(user_Choice.equals ("y")) {
									continue;
								}

								else if (user_Choice.equals("n")) {
									System.out.println("Update Complete");
								}

								else {
									System.out.println("Please enter a valid option.");
								}

							}while((user_Choice.equals("y")));

						}catch(ParseException e) {
							System.out.println("Please enter a valid date.");
						}

					}catch(InputMismatchException e) {
						System.out.println("Please enter a valid project number");
					}

					break;

				case 5:
					// Attaining structural engineer details and adding them to the structural engineer table.
					try {
						System.out.println("Enter a project number:");
						Scanner input = new Scanner(System.in);
						int selector2 = input.nextInt();

						System.out.println("Enter the Structural Engineer's telephone number.(10 digits maximum starting with 0.eg-0112685824): ");
						Scanner SETel = new Scanner(System.in);
						String newSETelephoneNumber = SETel.nextLine();
						rowsAffected = statement.executeUpdate("UPDATE structural_engineer SET Structural_Engineer_Telephone_Number='"+newSETelephoneNumber+"' "+" WHERE Structural_Engineer_Project_Number= "+selector2);

						System.out.println("Enter Structural Engineer's email address : ");
						Scanner SE_Email = new Scanner(System.in);
						String newSEEmailAddress = SE_Email.nextLine();
						rowsAffected = statement.executeUpdate("UPDATE structural_engineer SET Structural_Engineer_Email_Address='"+newSEEmailAddress+"' "+" WHERE Structural_Engineer_Project_Number= "+selector2);

						System.out.println("Contact details have been edited.");
					}catch(InputMismatchException e) {
						System.out.println("Please enter a valid telephone number");
					}

					break;

				case 6:
					// Scanning for incomplete projects.
					System.out.println("Incomplete projects\n");

					results = statement.executeQuery("SELECT Project_Number, ERF_Number, Project_name, Project_Address, Project_Type, Due_Date, "
							+ "Completed, Date_of_Completion FROM projects WHERE Completed = 'No'");
					while (results.next()) {
						System.out.println(results.getInt("Project_Number")+", "+results.getInt("ERF_Number")+", "+results.getString("Project_Name")+", "+results.getString("Project_Address")+", "+results.getString("Project_Type")+
								", "+results.getString("Due_Date")+", "+results.getString("Completed")+", "+results.getString("Date_of_Completion"));		
					}	
					// Scanning for overdue projects.
					System.out.println("\nOverdue Projects\n");

					results = statement.executeQuery("SELECT Project_Number, ERF_Number, Project_name, Project_Address, Project_Type, Due_Date, "
							+ "Completed, Date_of_Completion FROM projects WHERE Due_Date <= NOW()");
					while (results.next()) {
						System.out.println(results.getInt("Project_Number")+", "+results.getInt("ERF_Number")+", "+results.getString("Project_Name")+", "+results.getString("Project_Address")+", "+results.getString("Project_Type")+
								", "+results.getString("Due_Date")+", "+results.getString("Completed")+", "+results.getString("Date_of_Completion"));
					}
					// Scanning for complete projects.
					System.out.println("\nCompleted projects\n");

					results = statement.executeQuery("SELECT Project_Number, ERF_Number, Project_name, Project_Address, Project_Type, Due_Date, "
							+ "Completed, Date_of_Completion FROM projects WHERE Completed = 'Yes'");
					while (results.next()) {
						System.out.println(results.getInt("Project_Number")+", "+results.getInt("ERF_Number")+", "+results.getString("Project_Name")+", "+results.getString("Project_Address")+", "+results.getString("Project_Type")+
								", "+results.getString("Due_Date")+", "+results.getString("Completed")+", "+results.getString("Date_of_Completion"));
					}

					break;

				case 7:	
					// Creating variable for current time. 
					LocalDate CompletionDate = LocalDate.now();

					// Inputting details for invoice.
					System.out.println("Generate an invoice:\n");

					System.out.println("Enter the name of the project: ");
					Scanner projectname = new Scanner(System.in);
					String ProjectName = projectname.nextLine();
					
					System.out.println("Enter the name of the customer: ");
					Scanner customername = new Scanner(System.in);
					String InvCustomerName = customername.nextLine();
					try {
						System.out.println("Enter the customer's telephone number.(10 digits maximum starting with 0.eg-0112685824): ");
						Scanner customertel = new Scanner(System.in);
						int CustomerTel= customertel.nextInt();
		
						System.out.println("Enter the building type: ");
						Scanner buildtype = new Scanner(System.in);
						String BuildingType = buildtype.nextLine();
						try {	
							System.out.println("Enter the total fee to be paid:");
							Scanner totalfee = new Scanner(System.in);
							double TotalFee = totalfee.nextDouble();
				
							System.out.println("Enter the total fee paid:");
							Scanner totalfeePaid = new Scanner(System.in);
							double FeePaid = totalfeePaid.nextDouble();

							// Determining the amount outstanding.
							double AmountOutstanding = TotalFee - FeePaid;

							// If the amount outstanding is 0 then there will be no invoice.
							if(AmountOutstanding == 0) {
								System.out.println("No invoice required");
							}
							// If there is an outstanding amount then an invoice will be generated.
							else {
								System.out.println("Invoice generated. ");
								System.out.println();
								// Inputting values into invoice table.
								rowsAffected = statement.executeUpdate("INSERT INTO invoices VALUES('"+ProjectName+"', '"+InvCustomerName+"', '"+CustomerTel+"', '"+BuildingType+"', '"+TotalFee+"', '"+FeePaid+"', '"+
										AmountOutstanding+"', '"+CompletionDate+"');");
							}

							System.out.println("Enter the project number of the project to be finalised in the poisepms database: ");
							Scanner iinput = new Scanner(System.in);
							int selector3 = iinput.nextInt();
						
							// Finalising project by setting completion to "yes" and adding date of completion.
							rowsAffected = statement.executeUpdate("UPDATE projects SET Completed='"+Completed+"' "+" WHERE Project_Number= "+selector3);
							rowsAffected = statement.executeUpdate("UPDATE projects SET Date_of_Completion='"+CompletionDate+"' "+" WHERE Project_Number= "+selector3);

							System.out.println("Project Finalised");
							
						}catch(InputMismatchException e) {
							System.out.println("Incorrect input. Please enter a valid amount.");				
						}
					}catch(InputMismatchException e) {
						System.out.println("Please enter a valid number");
					}

					break;

				case 8:

					try {
						System.out.println("Enter a project number");
						Scanner ProjectSearch = new Scanner(System.in);
						int Search = ProjectSearch.nextInt();
						String search = ("SELECT* FROM projects WHERE Project_Number ="+Search);
						
						ResultSet result = statement.executeQuery(search);
						System.out.println("Project Details:\n");

						// Displaying the book's details.
						while (result.next()) {
							int projnum = result.getInt("Project_Number");
							int erfnum = result . getInt ( "ERF_Number" );
							String projname = result . getString ( "Project_Name" );
							String projtype = result . getString ( "Project_Type" );
							String projaddress = result . getString ("Project_Address");
							Date duedate = result .getDate("Due_Date");
							String completion = result . getString("Completed");
							Date Date_of_Completion = result . getDate("Date_of_Completion");
							System . out . println ( projnum + ", " + erfnum + ", " + projname+ ", "+
									projtype + ", " + projaddress + ", "+ duedate+", "+completion+ ", "+ Date_of_Completion);
						}

					}catch(InputMismatchException e) {
						System.out.println("Incorrect input. Please enter a valid project number.");
					}
					
					break;
				case 9:
					// Deleting a project.
					try {		
						int index = 0;

						System.out.println("Enter the project number of the project to be deleted: ");
						Scanner select  = new Scanner(System.in);
						index = select.nextInt();
						rowsAffected = statement.executeUpdate("DELETE FROM projects WHERE Project_Number = "+index);
						rowsAffected = statement.executeUpdate("DELETE FROM architect WHERE Architect_Project_Number = "+index);
						rowsAffected = statement.executeUpdate("DELETE FROM customer WHERE Customer_Project_Number = "+index);
						rowsAffected = statement.executeUpdate("DELETE FROM project_manager WHERE Project_Manager_Project_Number = "+index);
						rowsAffected = statement.executeUpdate("DELETE FROM structural_engineer WHERE Structural_Engineer_Project_Number = "+index);	
						System.out.println("Project details deleted.");
						break;
					// Exception handling to handle incorrect input.
					}catch(InputMismatchException e) {
						System.out.println("Incorrect input. Please enter a valid id.");
					}
						
				default:

					System.out.println("Please enter a valid choice.");	
					
				}
				// User can opt to return to the menu or exit.
				System.out.println();
				System.out.println("Would you like to return to the menu? Click \"y\" for yes or \"n\".");	
				answer = sc.next();

				if(answer.equals ("y")) {
					continue;

				}

				else if (answer.equals("n")) {
					System.out.println("Thank you for using Poised Mananger.");
				}

				else {
					System.out.println("Please enter a valid option.");
				}
				// Giving the user an option to continue.
			}while(answer.equals("y"));
			
		}catch(SQLException e) {

			e.printStackTrace();
		}
	}	
}

