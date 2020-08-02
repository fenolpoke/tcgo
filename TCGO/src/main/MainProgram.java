package main;

import panel.LoginPanel;
import template.TemplateFrame;


public class MainProgram {

	public static void main(String[] args) {
		//login frame first
		new LoginPanel(new TemplateFrame());
		
	}

}
