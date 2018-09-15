package sarai_avital;

import java.io.*;
import java.net.*;
import java.time.Month;
import java.util.Scanner;

class TCPClient_with_serialized {
	@SuppressWarnings({ "resource", "unused" })
	public static void main(String argv[]) throws Exception {
		@SuppressWarnings("unused")
		String Result = null;
		Object Supplier=null;
		Supplier supplier=null;
		Object HouseCommite = null;
		Object Tenant=null;
		Tenant tenant=null;
		Housecommite House_Commite=null;
		Object detail = null;
		Info Detail=null;
		String Select="non",app_num="non",month="non",speciality="non",choice="non";
		int Tenant_num=-1;
		int id=0;
		String Category = null, username=null, password=null;
		Socket clientSocket = new Socket("localhost", 10000);
		Scanner inFromUser = new Scanner(System.in);
		ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		while (true) {
			choice="non";
			Select="non";
			System.out.println("~*~*~*~*~Please Select your Category~*~*~*~*~");//choose Category
			System.out.println("->1 House Committee");
			System.out.println("->2 Tenant");
			System.out.println("->0 To exit");
			Category= inFromUser.nextLine();
			switch (Category) {
			case "0":{
				String choice2="non";
				Detail = new Info(username,password,Category,choice2,Select);
				outToServer.writeObject(Detail);
				String modifiedSentence = inFromServer.readLine();//exit program
				System.out.println("FROM SERVER: " + modifiedSentence);
				if(modifiedSentence.matches("bye bye!"))break;}
			case "1":{//its a housecommite
				System.out.println("~*~*~*~*~ Hello dear HouseCommite ! ~*~*~*~*~");
				System.out.println("->1 Login");
				System.out.println("->2 Change password");
				String choice2 = inFromUser.nextLine();
				if (choice2.matches("1")) {//enter with a username&password
					System.out.println("Please enter Username--->");
					username = inFromUser.nextLine();
					System.out.println("Please enter Password--->");
					password = inFromUser.nextLine();
					Detail = new Info(username,password,Category,choice2,Select);
					outToServer.writeObject(Detail);
					String modifiedSentence = inFromServer.readLine();
					System.out.println("FROM SERVER: " + modifiedSentence);
					if (modifiedSentence.matches("Wellcom!")) { // if its was the right username&password
						while(!choice2.matches("0")){//while we still want to do things as a housecommite
						app_num="non";
						month="non";
						speciality="non";
						Tenant_num=-1;
						System.out.println("~*~*~*~*~Please select what would you like to do~*~*~*~*~");
						System.out.println("->1 Show a specific tenant payments");
						System.out.println("->2 Show all of the payments in your building");
						System.out.println("->3 Set up new building");
						System.out.println("->4 Update a specific tenant payment");
						System.out.println("->5 Delete tenant payment");
						System.out.println("->6 Monthly income of your building");
						System.out.println("->7 Show Suppliers");
						System.out.println("->8 Enter a new supplier");
						System.out.println("->0  To exit");
						choice2= inFromUser.nextLine();
						switch (choice2) // find what the user chose
						{
						case"1":{// Show a specific tenant payments
							System.out.println("Please enter the appartment number--->");
							app_num=inFromUser.nextLine();
							House_Commite = new Housecommite(app_num,Tenant_num,month,speciality,choice2);
							outToServer.writeObject(House_Commite);
							modifiedSentence = inFromServer.readLine();
							System.out.println("FROM SERVER: " + modifiedSentence);
							break;
							    }
						case"2":{// Show all of the payments in your building
							House_Commite = new Housecommite(app_num,Tenant_num,month,speciality,choice2);
							outToServer.writeObject(House_Commite);
							modifiedSentence = inFromServer.readLine();
							System.out.println("FROM SERVER: " + modifiedSentence);
							break;
						    	}
						case"3":{// Set up new building
							System.out.println("How many people will live here?");
							Tenant_num=inFromUser.nextInt();
							for(int i=1;i<Tenant_num+1;i++)
							{
								System.out.println("enter tenant Firstname ?");	
								String firstname=inFromUser.next();
								System.out.println("enter tenant Lastname");
								String Lastname=inFromUser.next();
								System.out.println("enter tenant Username");
								String Username=inFromUser.next();
								System.out.println("enter tenant Password");
								String Password=inFromUser.next();
								System.out.println("enter tenant monthly payment");
								int payment=inFromUser.nextInt();
								System.out.println("enter tenant Email?");
								String Email=inFromUser.next();
								tenant = new Tenant(i,firstname,Lastname,Username,Password,payment,Email);
								outToServer.writeObject(tenant);	
							}
							break;
							    }
						case"4":{// Update a specific tenant payment
							System.out.println("Please enter the appartment number--->");
							app_num=inFromUser.nextLine();
							System.out.println("Please enter the number of the month you would like to update--->");
							month=inFromUser.nextLine();
							House_Commite = new Housecommite(app_num,Tenant_num,month,speciality,choice2);
							outToServer.writeObject(House_Commite);
							modifiedSentence = inFromServer.readLine();
							System.out.println("FROM SERVER: " + modifiedSentence);
							break;
							    }
						case"5":{//  Delete tenant payment
							System.out.println("Please enter the appartment number--->");
							app_num=inFromUser.nextLine();
							System.out.println("Please enter the number of the month you would like to update--->");
							month=inFromUser.nextLine();
							House_Commite = new Housecommite(app_num,Tenant_num,month,speciality,choice2);
							outToServer.writeObject(House_Commite);
							modifiedSentence = inFromServer.readLine();
							System.out.println("FROM SERVER: " + modifiedSentence);
							break;
							    }
						case"6":{// Monthly income of your building
							System.out.println("Please enter the number of the month--->");
							month=inFromUser.nextLine();
							House_Commite = new Housecommite(app_num,Tenant_num,month,speciality,choice2);
							outToServer.writeObject(House_Commite);
							modifiedSentence = inFromServer.readLine();
							System.out.println("FROM SERVER: " + modifiedSentence);
							break;
							    }
						case"7":{// Show Suppliers
						    System.out.println("Please enter a speciality of supplier:electrician/plumber/renovation--->");
						    speciality=inFromUser.nextLine();
						    House_Commite = new Housecommite(app_num,Tenant_num,month,speciality,choice2);
						    outToServer.writeObject(House_Commite);
					    	modifiedSentence = inFromServer.readLine();
						    System.out.println("FROM SERVER: " + modifiedSentence);
						    break;
						       }
						case"8":{// Enter a new supplier
							System.out.println("Please enter the firstname--->");
							String SupName=inFromUser.next();
							System.out.println("Please enter the Lastname--->");
							String SupLast=inFromUser.next();
							System.out.println("Please enter the specializes:electrician/plumber/renovation--->");	
							String spec=inFromUser.next();
							System.out.println("Please enter the phone number--->");	
							String phone=inFromUser.next();
							supplier = new Supplier(id,SupName,SupLast,spec,phone);
							outToServer.writeObject(supplier);
							modifiedSentence = inFromServer.readLine();
							System.out.println("FROM SERVER: " + modifiedSentence);
							break;
							    }
						default:{break;}
					}
						}
					}
					if(choice2.matches("0")) break;
						else if (choice2.matches("2")) {//create new password
							System.out.println("Please enter Username--->");
						username = inFromUser.nextLine();
							System.out.println("Please enter your New Password--->");
						password = inFromUser.nextLine();
						Detail = new Info(username,password,Category,choice2,Select);
						outToServer.writeObject(Detail);
						String modifiedSentence1 = inFromServer.readLine();
						System.out.println("FROM SERVER: " + modifiedSentence1);
						break;
						}
						}
					}
			case "2": //its a tenant
			{
				String modifiedSentence;
				System.out.println("~*~*~*~*~Hello dear Tenant ! ~*~*~*~*~");
				System.out.println("->1 Login");
				System.out.println("->2 Change your password");
				String choice2 = inFromUser.nextLine();
				if (choice2.matches("1")) {//enter with a username&password
					System.out.println("Please enter Username--->");
					username = inFromUser.nextLine();
					System.out.println("Please enter Password--->");
					password = inFromUser.nextLine();
					Detail = new Info(username,password,Category,choice2,Select);
					outToServer.writeObject(Detail);
					modifiedSentence = inFromServer.readLine();
					System.out.println("FROM SERVER: " + modifiedSentence);
					if (modifiedSentence.matches("Wellcom!")) {// if its was the right username&password
						System.out.println("~*~*~*~*~Please select what would you like to do~*~*~*~*~");
						System.out.println("->1 Show your payments");
						 Select= inFromUser.nextLine();
						 if (Select.matches("1")) {
							Detail = new Info(username,password,Category,choice2,Select);
							outToServer.writeObject(Detail);
							modifiedSentence = inFromServer.readLine();
							System.out.println("FROM SERVER: " + modifiedSentence);
						}
					}
				}
				else if (choice2.matches("2")) {//create new password
					System.out.println("Please enter Username--->");
					username = inFromUser.nextLine();
					System.out.println("Please enter your New Password--->");
					password = inFromUser.nextLine();
					Detail = new Info(username,password,Category,choice2,Select);
					outToServer.writeObject(Detail);
					modifiedSentence = inFromServer.readLine();
					System.out.println("FROM SERVER: " + modifiedSentence);
					break;
				}
			}
			
			     default:{break;}
			}
				if(Category.matches("0"))
				break;
			}
		}
	}
