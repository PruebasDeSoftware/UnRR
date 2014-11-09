package main;

public class App {

	private String packageName;
	private String mainActivity;

	public App(String packageName, String mainActivity) {
		this.packageName = packageName;
		this.mainActivity = mainActivity;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getMainActivity() {
		return mainActivity;
	}

}
