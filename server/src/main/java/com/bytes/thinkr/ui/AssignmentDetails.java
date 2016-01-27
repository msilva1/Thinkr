package com.bytes.thinkr.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class AssignmentDetails extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtAssignmentName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AssignmentDetails dialog = new AssignmentDetails();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AssignmentDetails() {
		setTitle("Create Assignment");
		setBounds(100, 100, 450, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblName = new JLabel("Name:");
			GridBagConstraints gbc_lblName = new GridBagConstraints();
			gbc_lblName.insets = new Insets(0, 0, 5, 5);
			gbc_lblName.anchor = GridBagConstraints.EAST;
			gbc_lblName.gridx = 0;
			gbc_lblName.gridy = 0;
			contentPanel.add(lblName, gbc_lblName);
		}
		{
			txtAssignmentName = new JTextField();
			txtAssignmentName.setText("Assignment Name");
			GridBagConstraints gbc_txtAssignmentName = new GridBagConstraints();
			gbc_txtAssignmentName.insets = new Insets(0, 0, 5, 0);
			gbc_txtAssignmentName.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtAssignmentName.gridx = 1;
			gbc_txtAssignmentName.gridy = 0;
			contentPanel.add(txtAssignmentName, gbc_txtAssignmentName);
			txtAssignmentName.setColumns(10);
		}
		{
			JLabel lblDate = new JLabel("Date:");
			lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gbc_lblDate = new GridBagConstraints();
			gbc_lblDate.anchor = GridBagConstraints.EAST;
			gbc_lblDate.insets = new Insets(0, 0, 5, 5);
			gbc_lblDate.gridx = 0;
			gbc_lblDate.gridy = 1;
			contentPanel.add(lblDate, gbc_lblDate);
		}
		{
			JLabel lblCurrentTime = new JLabel("Current Time");
			GridBagConstraints gbc_lblCurrentTime = new GridBagConstraints();
			gbc_lblCurrentTime.anchor = GridBagConstraints.WEST;
			gbc_lblCurrentTime.insets = new Insets(0, 0, 5, 0);
			gbc_lblCurrentTime.gridx = 1;
			gbc_lblCurrentTime.gridy = 1;
			contentPanel.add(lblCurrentTime, gbc_lblCurrentTime);
		}
		{
			JLabel lblCategory = new JLabel("Category:");
			GridBagConstraints gbc_lblCategory = new GridBagConstraints();
			gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
			gbc_lblCategory.gridx = 0;
			gbc_lblCategory.gridy = 2;
			contentPanel.add(lblCategory, gbc_lblCategory);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.gridheight = 2;
			gbc_scrollPane.weighty = 1.0;
			gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 1;
			gbc_scrollPane.gridy = 2;
			contentPanel.add(scrollPane, gbc_scrollPane);
			{
				JPanel panel = new JPanel();
				scrollPane.setViewportView(panel);
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				{
					JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Homework");
					panel.add(rdbtnNewRadioButton_1);
				}
				{
					JRadioButton rdbtnNewRadioButton = new JRadioButton("Project");
					panel.add(rdbtnNewRadioButton);
				}
				{
					JRadioButton rdbtnLanguage = new JRadioButton("Extra Credit");
					panel.add(rdbtnLanguage);
				}
				{
					JRadioButton rdbtnOthers = new JRadioButton("Others");
					panel.add(rdbtnOthers);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
