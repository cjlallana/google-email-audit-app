import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import com.google.gdata.client.appsforyourdomain.audit.AuditService;
import com.google.gdata.client.appsforyourdomain.audit.MailBoxDumpRequest;
import com.google.gdata.data.appsforyourdomain.generic.GenericEntry;
import com.google.gdata.util.ServiceException;


public class EmailAPI {

	private String STR_PUBLIC_KEY;
	private String ADMIN_EMAIL;
	private String ADMIN_PASS;
	private String DOMAIN;
	private String APPLICATION_NAME;

	/*  */
	private AuditService service;
	private List<GenericEntry> mailbox_dump_requests;
	
	
	/**
	 * Parameterized Constructor, in order to make this a more generic class by
	 * having all the constants in the Main class.
	 * 
	 * @param spk Public Key
	 * @param ae Admin email
	 * @param ap Admin password
	 * @param d Google Apps domain
	 * @param an Application name
	 */
	public EmailAPI(String spk, String ae, String ap, String d, String an) {
		STR_PUBLIC_KEY = spk;
		ADMIN_EMAIL = ae;
		ADMIN_PASS = ap;
		DOMAIN = d;
		APPLICATION_NAME = an;
	}
	
	/**
	 * Initialize the Emaul Audit Service
	 * 
	 * @return True if it went OK.
	 */
	public boolean initializeService()
	{
		try {
			service = new AuditService(	ADMIN_EMAIL, ADMIN_PASS, 
										DOMAIN, APPLICATION_NAME);
		
			//GenericEntry getUploadPublicKey = 
			service.uploadPublicKey(STR_PUBLIC_KEY);
            //System.out.println("UploadedKey: " + getUploadPublicKey.getAllProperties());
            
		} catch (IOException | ServiceException e) {
			e.printStackTrace();
			System.out.println("Error initializing the Audit Service");
			return false;
		}

		return true;
	}
	
	
	public void requestMboxDump(String user_email)
	{
		System.out.println("REQUESTING MAILBOX DUMP for " + user_email);
		MailBoxDumpRequest request = new MailBoxDumpRequest();
		request.setAdminEmailAddress(ADMIN_EMAIL);
		request.setUserEmailAddress(user_email);

		request.setIncludeDeleted(true);
		//request.setSearchQuery("in:chat");
		request.setPackageContent("FULL_MESSAGE");
		
		GenericEntry mailboxDumpEntry;
		
		try {
			mailboxDumpEntry = service.createMailboxDumpRequest(request);
			System.out.println(mailboxDumpEntry.getId());
			
		} catch (IOException | ServiceException e) {
			e.printStackTrace();
			System.out.println();
		}
	}
	
	
	public boolean retrieveAllMboxDumpRequests()
	{
		System.out.println("RETRIEVING ALL MAILBOX DUMP REQUESTS");
		try {
			Calendar temp = Calendar.getInstance();
			temp.add(Calendar.DATE, -7);
			
			mailbox_dump_requests = service.retrieveAllMailboxDumpRequests(temp.getTime());

			return true;

		} catch (IOException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	public void filterMboxDumpRequests()
	{
		for (GenericEntry dump_request : mailbox_dump_requests){
			System.out.println(dump_request.getAllProperties().toString());
			
			MailBoxDumpRequest request = new MailBoxDumpRequest(dump_request);
			if (request.getNumberOfFiles() > 0){
				if (request.getStatus().equals("COMPLETED")){
					System.out.println(request.getUserEmailAddress());
					System.out.println(" - Download --> " + dump_request.getProperty("fileUrl0"));
				}
			}
			else {
				deleteMboxDumpRequest(request);
			}
		}
	}

	
	public boolean deleteMboxDumpRequest(MailBoxDumpRequest request)
	{
		String user_name = request.getUserEmailAddress();
		user_name = user_name.substring(0, user_name.indexOf("@"));

		try {
			service.deleteMailboxDumpRequest(user_name, request.getRequestId());
			System.out.println("The request for " + request.getUserEmailAddress() + " is ready to be deleted.");
			return true;
		} catch (IOException | ServiceException e) {
			e.printStackTrace();
			System.out.println("Error deleting the request for: " + request.getUserEmailAddress());
			return false;
		}
	}
}
