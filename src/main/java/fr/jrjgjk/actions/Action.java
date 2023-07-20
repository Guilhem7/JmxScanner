package fr.jrjgjk.actions;

public interface Action{
	public void init(); // Method call to avoid init component inside a constructor
	public void run();
}