
public class Main {
	
	// Constants
	private static final String STR_PUBLIC_KEY = 
			"LS0tLS1CRUdJTiBQR1AgUFVCTElDIEtFWSBCTE9DSy0tLS0tDQpWZXJzaW9uOiBHbn"
			+ "VQRyB2Mi4wLjIyIChNaW5nVzMyKQ0KDQptUUVOQkZMV2J6a0JDQUM0cG5zZmlVK2"
			+ "pKNXVzd3VvTFNFWmI0U01GMVNjd2U3THlmYXlQN1p0V2lvTFNMTXJGDQpsTlMvZ2"
			+ "5BV2RDUlpVWGdYY3JIVmVrc1lkMXA4L3BoUFNkTEl0V1FPY0dFcnFtbXJSeGgvbn"
			+ "RkU1VscDZ6SkZ5DQplUmFzMkRxZHlVSHVvdDI4ZnJFVTVKK0JoMkJMSTRJWExKV3"
			+ "pUZks5MCs0cDZMOWhNd3VocUVsSzRaVk9RWVEyDQozVW1IRzJYbW5aZk1nWHo1VW"
			+ "xjOEt0NUpZYVJaR1AwZEJRcy9SOWJVcFM4Z1JlamJLdjJIc3VVRk9iVVZrYnNyDQ"
			+ "phK0doYWcyK2xzeTh4Q1JFVG1Vc0d1TWY1aXBhTGovV1BUS3lZc1RCdytNOHJmeV"
			+ "NUM3BmU2RVMmxFRFhkZkUwDQpraGdYZlgwYXliVjVKTytaeU1LK2hubSsyT0I5d1"
			+ "l4ekQybERBQkVCQUFHMExXRmtiV2x1SUNoRmJXRnBiQ0JCDQpkV1JwZENCQlVFa2"
			+ "dTMlY1S1NBOFlXUnRhVzVBWlhWc1pXNHVZMjl0UG9rQk9RUVRBUUlBSXdVQ1V0Wn"
			+ "ZPUUliDQpEUWNMQ1FnSEF3SUJCaFVJQWdrS0N3UVdBZ01CQWg0QkFoZUFBQW9KRU"
			+ "F0V3lGTWo4NzlWQ2trSC9pZ0pMTW5zDQpTeFZnNmYzV3ZGMGhSODFrTXBESC9UVl"
			+ "ZHUUpxU2tYc3FmenBDMEZ0SHRhOGg3SnZMSG5NNEMyK2lHMldNbHFzDQpnd2VGd2"
			+ "NuVC9iM0YrQUx0QmdzYmNtTEs4dDBBaThtSGxrWEhydXlZbnNSVlNzeUJPUWZxMH"
			+ "oxMTlOZ2tkalRjDQpXYStxWXRHUm5HUVo4VGRTbExyM0o5UFVGTDVMNmRlK05VVW"
			+ "ZTYzI3NmJESDgxWDFEYmtPcmJrVzFnOFljcGlRDQp3Zkl5UFRBdmdCa1phelFxZk"
			+ "pJRU1EZUcxU3ZxYWFoUFl2TlBoS210Z2hjeUpMSHhIYjJLLy92Z05hc2wyUnJuDQ"
			+ "pOcUVLZHhsWDBEZVBXMkl2czJMZ2ZJZG03WC8vbzl3QWlZNDhQMFBwT0x1bFUrSz"
			+ "AzMTFnSHJ4dFdkZTVMRCtODQo1YmFaN3JKQXFWVmNrMG89DQo9WDVLZQ0KLS0tLS"
			+ "1FTkQgUEdQIFBVQkxJQyBLRVkgQkxPQ0stLS0tLQ==";
	
	private final static String ADMIN_EMAIL = "admin@domain.com";
	private final static String ADMIN_PASS = "pAsSwOrD";
	private final static String DOMAIN = "domain.com";
	private final static String APPLICATION_NAME = "application-name";
	
	public static void main(String[] args) 
	{
		/* If running the application through proxy, use these lines. */
		//System.getProperties().put("http.proxyHost", "10.80.X.X"); 
		//System.getProperties().put("http.proxyPort", "8080");
		//System.getProperties().put("https.proxyHost", "10.80.X.X"); 
		//System.getProperties().put("https.proxyPort", "8080");
		
		EmailAPI ea = new EmailAPI(	STR_PUBLIC_KEY, ADMIN_EMAIL, ADMIN_PASS, 
									DOMAIN, APPLICATION_NAME);
		
		// Initialize the Email Audit Service
		if (ea.initializeService()){
			String user_email = "user.email@eulen.com";
			
			ea.requestMboxDump(user_email);
			
			if (ea.retrieveAllMboxDumpRequests())
				ea.filterMboxDumpRequests();
		}
		
		//System.out.println("END");
	}
}
