package materiais;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class frameMatCadas {
	
	private JFrame frame;
	
	
	public frameMatCadas(){
		
		frame = new JFrame("Cadastro de Materiais");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(420,270);
		frame.setLocationRelativeTo(null);
		
		
		
		
		
		frame.setVisible(true);
	}
	
}