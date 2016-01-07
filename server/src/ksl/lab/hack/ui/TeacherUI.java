package ksl.lab.hack.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.bytes.hack.model.assignment.Assignment;

public class TeacherUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtQuestion;
	private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherUI frame = new TeacherUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TeacherUI() {
		setTitle("Teacher Dashboard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panelAssignments = new JPanel();
		panelAssignments.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Assignments", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelAssignments = new GridBagConstraints();
		gbc_panelAssignments.insets = new Insets(0, 0, 5, 5);
		gbc_panelAssignments.fill = GridBagConstraints.BOTH;
		gbc_panelAssignments.gridx = 0;
		gbc_panelAssignments.gridy = 0;
		contentPane.add(panelAssignments, gbc_panelAssignments);
		panelAssignments.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneAssignments = new JScrollPane();
		panelAssignments.add(scrollPaneAssignments, BorderLayout.CENTER);
		
		JList listAssignments = new JList();
		scrollPaneAssignments.setViewportView(listAssignments);
		
		JPanel pnlAsgControls = new JPanel();
		panelAssignments.add(pnlAsgControls, BorderLayout.SOUTH);
		
		JButton btnAddAssignment = new JButton("Add");
		btnAddAssignment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AssignmentDetails details = new AssignmentDetails();
				details.setVisible(true);
			}
		});
		
		JButton btnAssign = new JButton("Assign");
		btnAssign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AssignDetails details = new AssignDetails();
				details.setVisible(true);
			}
		});
		btnAssign.setAction(action);
		pnlAsgControls.add(btnAssign);
		pnlAsgControls.add(btnAddAssignment);
		
		JButton btnRemoveAssignment = new JButton("Delete");
		pnlAsgControls.add(btnRemoveAssignment);
		
		JPanel panelQuestions = new JPanel();
		panelQuestions.setBorder(new TitledBorder(null, "Questions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelQuestions = new GridBagConstraints();
		gbc_panelQuestions.insets = new Insets(0, 0, 5, 0);
		gbc_panelQuestions.fill = GridBagConstraints.BOTH;
		gbc_panelQuestions.gridx = 1;
		gbc_panelQuestions.gridy = 0;
		contentPane.add(panelQuestions, gbc_panelQuestions);
		panelQuestions.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneQuestions = new JScrollPane();
		panelQuestions.add(scrollPaneQuestions, BorderLayout.CENTER);
		
		JList listQuestions = new JList();
		scrollPaneQuestions.setViewportView(listQuestions);
		
		JPanel panelQuesControls = new JPanel();
		panelQuestions.add(panelQuesControls, BorderLayout.SOUTH);
		
		JButton btnAddQuestion = new JButton("Add");
		panelQuesControls.add(btnAddQuestion);
		
		JButton btnDelQuestion = new JButton("Delete");
		panelQuesControls.add(btnDelQuestion);
		
		JPanel panelDetails = new JPanel();
		GridBagConstraints gbc_panelDetails = new GridBagConstraints();
		gbc_panelDetails.gridheight = 2;
		gbc_panelDetails.fill = GridBagConstraints.BOTH;
		gbc_panelDetails.weightx = 1.0;
		gbc_panelDetails.weighty = 0.7;
		gbc_panelDetails.gridwidth = 2;
		gbc_panelDetails.insets = new Insets(0, 0, 0, 5);
		gbc_panelDetails.gridx = 0;
		gbc_panelDetails.gridy = 1;
		contentPane.add(panelDetails, gbc_panelDetails);
		panelDetails.setLayout(new BorderLayout(0, 0));
		
		txtQuestion = new JTextField();
		txtQuestion.setText("Question: ");
		panelDetails.add(txtQuestion, BorderLayout.NORTH);
		txtQuestion.setColumns(10);
		
		JTextArea txtrAnswer = new JTextArea();
		txtrAnswer.setText("Answer:");
		panelDetails.add(txtrAnswer, BorderLayout.CENTER);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
