package sarai_avital;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;
import java.time.Month;
public class SocketHandler extends Thread {
	Socket incoming;
	Driver Sql;
	static int count=1;
	int counter=0;
	SocketHandler(Socket _in) {
		this.incoming = _in;

	}
	@SuppressWarnings({ "deprecation", "static-access", "unused" })
	public void run() {
		Object clientObject;
		ObjectInputStream inFromClient = null;
		DataOutputStream outToClient = null;
		try {
			inFromClient = new ObjectInputStream(incoming.getInputStream());
			outToClient = new DataOutputStream(incoming.getOutputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String one;
		Sql.ConectingToSQL();
		double sum = 0;
		double result = 0;
		try {
			Info Detail = null;
			Housecommite House_Commite=null;
			Tenant tenant=null;
			Supplier supplier=null;
			while (true) {
				clientObject = inFromClient.readObject();
				if (clientObject instanceof Info) {
					Detail = (Info) clientObject;	
					  if ((Detail.GetCategory().matches("0")))
							  {
						       outToClient.writeBytes("bye bye!"+ "\n");
							  }
	               if ((Detail.GetCategory().matches("1") && Detail.GetSelect().matches("1")) && Detail.GetChoice().matches("non")) {
						if (Detail.GetPassword().matches(Sql.SelectHouseCommite(Detail.GetUsername()))) 
							outToClient.writeBytes("Wellcom!"+ "\n"); // if its was the right username&password(housecommite)
						else {
						outToClient.writeBytes("Wrong username/password! please try again--->" + "\n");
						}						
						}					
					if ((Detail.GetCategory().matches("2") && Detail.GetSelect().matches("1")) && Detail.GetChoice().matches("non")) {		
						if (Detail.GetPassword().matches(Sql.SelectTenant(Detail.GetUsername()))) 
							outToClient.writeBytes("Wellcom!" + "\n");// if its was the right username&password(tenant)
						else {
						outToClient.writeBytes("Wrong username/password! please try again--->" + "\n");
						}
					}
					 if ((Detail.GetCategory().matches("1") && Detail.GetSelect().matches("2"))&& Detail.GetChoice().matches("non")) {
							Sql.UpdateHouseCommitePassword(Detail.GetUsername(), Detail.GetPassword());
							outToClient.writeBytes("Password changed sucssefuly :)" + "\n");//enter a new password(housecommite)
					 }
					 if ((Detail.GetCategory().matches("2") && Detail.GetSelect().matches("2"))&& Detail.GetChoice().matches("non")) {
						Sql.UpdateTenantPassword(Detail.GetUsername(), Detail.GetPassword());
						outToClient.writeBytes("Password has changed sucssefuly :)" + "\n");//enter a new password(tenant)	
					}
				if (Detail.GetCategory().matches("2") && Detail.GetSelect().matches("1") && Detail.GetChoice().matches("1")) {
					outToClient.writeBytes(Sql.SelectMonthPay(Detail.GetUsername()) + "\n");//Show your payments(tenant)
					}
				}
				//HouseComitte Sql
				if (clientObject instanceof Housecommite) {
					House_Commite = (Housecommite) clientObject;			
				   switch(House_Commite.GetChoice())
				   {// find what the user chose
				case "1":{ //Show a specific tenant payments
					String ans=Sql.SelectTenantPay(House_Commite.GetApp());
			     	outToClient.writeBytes(ans+ "\n");
					break;
					     }
				case "2":{ //Show all of the payments in your building
					String ans=Sql.SelectAllPay();
					outToClient.writeBytes(ans+ "\n");
					break;
					     }
				case "4":{ //Update a specific tenant payment
					int month = Integer.parseInt(House_Commite.GetMonth());
					String month2=(Month.of(month).name());	
					int app = Integer.parseInt(House_Commite.GetApp());
					Sql.UpdatePay(month2,app,month);
					outToClient.writeBytes("The payment updated sucssefuly :)"+ "\n");
					break;
					     }
				case "5":{ //Delete tenant payment
					int month = Integer.parseInt(House_Commite.GetMonth());
					String month2=(Month.of(month).name());	
					int app = Integer.parseInt(House_Commite.GetApp());
					Sql.UpdatePayD(month2,app);
					outToClient.writeBytes("The payment deleted sucssefuly :)"+ "\n");
					break;
					     }
				case "6":{ // Monthly income of your building
					int month = Integer.parseInt(House_Commite.GetMonth());
					String month2=(Month.of(month).name());
					String ans=Sql.SelectAllSum(month2);
					outToClient.writeBytes(ans+ "\n");
					break;
					     }
				case "7":{ //Show Suppliers
					String answer=(Sql.SelectSupplier(House_Commite.GetSpecial()));
					outToClient.writeBytes(answer+ "\n");
					break;
					     }
				default:{break;}
				}
				}	
				if (clientObject instanceof Tenant) {
					tenant = (Tenant) clientObject;
					{
					if(count==1)
					{
					Sql.DropTable();
					Sql.CreateTable();
					}
					Sql.InsertTenant(count++, tenant.GetFirstName(),tenant.GetLastName(),tenant.GetUsername(),tenant.GetPassword(),tenant.GetMail(),tenant.GetPay());
					}
					}
				if (clientObject instanceof Supplier) { //Enter a new supplier
					supplier = (Supplier) clientObject;
					if(supplier.GetFirstName().matches((Sql.CheckIfExist(supplier.GetPhone()))))
					{
					outToClient.writeBytes("The supplier you entered already exist!"+ "\n");
					}
					else
					{
					counter=Sql.SelectMax();
					counter++;
					Sql.InsertSupplier(counter,supplier.GetFirstName(),supplier.GetLastName(),supplier.GetPhone(),supplier.GetSpecial());
					outToClient.writeBytes("The supplier entered sucssesfuly :)"+ "\n");
					}	
				}
			}
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
