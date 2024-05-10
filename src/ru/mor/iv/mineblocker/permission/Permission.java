package ru.mor.iv.mineblocker.permission;

public interface Permission {
	public boolean isEnabled();

	public void setEnabled(boolean enabled);

	public String getStringPermission();

	public void setStringPermission(String stringPermission);

	public String getConfigSection();

	public String getRuMessage();
	
	public String getEnMessage();
	
	public String getMessage();
	
	public void setMessage(String message);
	
	public boolean isForWhatEnabled();
	
	public void setForWhatEnabled(boolean enabled);

}
