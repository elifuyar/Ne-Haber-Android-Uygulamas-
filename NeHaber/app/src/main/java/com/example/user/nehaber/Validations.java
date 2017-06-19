package com.example.user.nehaber;

public class Validations {
	public static boolean isEmpty(String s)
	{
		if (s == null)
			return true;
		if (s.isEmpty())
			return true;
		if (s.trim().isEmpty())
			return true;
		return false;
	}
}
