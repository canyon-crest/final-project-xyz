import javax.swing.SwingUtilities;

public class GUIRunner {
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(HareUI::new);

	}

}
