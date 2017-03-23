package Pictures;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;

public class ButtonHandler {
	private JButton myButton;
	private JTextField myTextField;
	private ArrayList handlers = new ArrayList();
	
	
	public boolean canHandle(JButton){
		
	}
	
	public boolean validRoll(){
		
	}
	
	public void reset(){
		
	}
	
	public void updateScore(){
		
	}
	private void loadHandlers() {
		handlers.add(new OnesButtonHandler(onesButton, ones));
		handlers.add(new TwosButtonHandler(twosButton, twos));
		
		handlers.add(new ChanceButtonHandler(chanceButton, chance));
		
	}
	
	class AllButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JButton clickedButton = (JButton) e.getSource();
			for(ButtonHandler handler : handlers){
				if(handler.canHandle(clickedButton)){
					handler.updateScore();
					break;
				}
			}
		}
	}
	
	abstract class ButtonHandler {
		protected JButton myButton;
		protected JTextField myTextField;
		
		public ButtonHandler(JButton button, JTextField text){
			myButton	= button;
			myTextField	= text;
		}
		
		/** 
		 * @param button that was pressed
		 * @return True if I can handle this button, false otherwise
		 */
		public boolean canHandle(JButton button) {
			return button == myButton;
		}
		
		/**
		 * Is this roll valid? i.e., does it meet the requirements of 
		 * this category
		 * @return True if it is valid, false otherwise
		 */
		public abstract boolean validRoll();
		
		/**
		 * Reset my button and text field for a new game
		 */
		public void reset() {
			myButton.setEnabled(true);
			myTextField.setText("");
		}
				
		public void updateScore() {
			myButton.setEnabled(false);
			//resetDice();
			//update grand total?
			
		}
	}
	
	abstract class UpperButtonHandler extends ButtonHandler {

		protected int myVal;
		public UpperButtonHandler(JButton button, JTextField text) {
			super(button, text);
			myVal = 0;
		}
		
		@Override
		public boolean validRoll() { return true; }
		
		@Override
		public void updateScore() {
			findResults();
			
			int score = results[myVal - 1] * myVal;
			
			myTextField.setText(String.valueOf(score));
			
			//updateLowerScore();
			super.updateScore();
		}
		
	}
	
	class OnesButtonHandler extends UpperButtonHandler {

		public OnesButtonHandler(JButton button, JTextField text) {
			super(button, text);
			myVal = 1;
		}
		
	}
	
	class TwosButtonHandler extends UpperButtonHandler {

		public TwosButtonHandler(JButton button, JTextField text) {
			super(button, text);
			myVal = 2;
		}
		
	}
	
	class ThreesButtonHandler extends UpperButtonHandler {

		public ThreesButtonHandler(JButton button, JTextField text) {
			super(button, text);
			myVal = 3;
		}
		
	}
	
	class FoursButtonHandler extends UpperButtonHandler {

		public FoursButtonHandler(JButton button, JTextField text) {
			super(button, text);
			myVal = 4;
		}
		
	}
	
	class FivesButtonHandler extends UpperButtonHandler {

		public FivesButtonHandler(JButton button, JTextField text) {
			super(button, text);
			myVal = 5;
		}
		
	}
	
	class SixesButtonHandler extends UpperButtonHandler {

		public SixesButtonHandler(JButton button, JTextField text) {
			super(button, text);
			myVal = 6;
		}
		
	}
	
	class ChanceButtonHandler extends ButtonHandler {

		public ChanceButtonHandler(JButton button, JTextField text) {
			super(button, text);
		}

		@Override
		public boolean validRoll() {
			return true;
		}
		
		@Override
		public void updateScore() {
			
			
			int sum = 0;
			for(Dice d : diceList){
				sum += d.getValue();
			}
			
			myTextField.setText(String.valueOf(sum));

			//update lower total
			super.updateScore();
		}
		
	}

}
