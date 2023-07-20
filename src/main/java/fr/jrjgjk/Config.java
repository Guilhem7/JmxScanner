package fr.jrjgjk;

public class Config{
	public static final String packageName = "fr.jrjgjk.Scan";
	public static String mLetName = "DefaultDomain:type=MLet";
	public static final String objectName = "Jrjgjk:name=payload,id=1";
	public static final String jarPath = "./resources/";
	public static final String jarName = "jrjgjk.jar";
	public static final int timeout = 2000;

	public static final String mLetMethodName = "getMBeansFromURL";
	public static final String mBeanLetName = "javax.management.loading.MLet";

	public static final String defaultBoundName = "jmxrmi";
	public static final String defaultTargetPort = "1099";
	public static final String defaultServerPort = "80";
	public static final String defaultLocalServerEndpoint = "jrjgjkBean";

	public static void setMletName(String name)
	{
		mLetName = name;
	}

}