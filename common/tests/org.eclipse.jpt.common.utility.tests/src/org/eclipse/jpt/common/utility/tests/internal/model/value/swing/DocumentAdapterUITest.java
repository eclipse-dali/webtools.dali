/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.DocumentAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

/**
 * Play around with a set of entry fields.
 */
@SuppressWarnings("nls")
public class DocumentAdapterUITest {

	private TestModel testModel;
		private static final String DEFAULT_NAME = "Scooby Doo";
	private ModifiablePropertyValueModel<TestModel> testModelHolder;
	private ModifiablePropertyValueModel<String> nameHolder;
	private Document nameDocument;
	private Document upperCaseNameDocument;

	public static void main(String[] args) throws Exception {
		new DocumentAdapterUITest().exec();
	}

	private DocumentAdapterUITest() {
		super();
	}

	private void exec() throws Exception {
		this.testModel = new TestModel(DEFAULT_NAME);
		this.testModelHolder = new SimplePropertyValueModel<TestModel>(this.testModel);
		this.nameHolder = this.buildNameHolder(this.testModelHolder);
		this.nameDocument = this.buildNameDocument(this.nameHolder);
		this.upperCaseNameDocument = this.buildUpperCaseNameDocument(this.nameHolder);
		this.openWindow();
	}

	private ModifiablePropertyValueModel<String> buildNameHolder(PropertyValueModel<TestModel> vm) {
		return new PropertyAspectAdapter<TestModel, String>(vm, TestModel.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getName();
			}
			@Override
			protected void setValue_(String value) {
				this.subject.setName(value);
			}
		};
	}

	private Document buildNameDocument(ModifiablePropertyValueModel<String> stringHolder) {
		return new DocumentAdapter(stringHolder);
	}

	private Document buildUpperCaseNameDocument(ModifiablePropertyValueModel<String> stringHolder) {
		return new DocumentAdapter(stringHolder, this.buildUpperCaseNameDocumentDelegate());
	}

	private AbstractDocument buildUpperCaseNameDocumentDelegate() {
		return new PlainDocument() {
			@Override
			public void insertString(int offset, String string, AttributeSet a) throws BadLocationException {
				if (string == null) {
					return;
				}
				char[] upper = string.toCharArray();
				for (int i = 0; i < upper.length; i++) {
					upper[i] = Character.toUpperCase(upper[i]);
				}
				super.insertString(offset, new String(upper), a);
			}
		};
	}

	private void openWindow() {
		JFrame window = new JFrame(this.getClass().getName());
		window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		window.addWindowListener(this.buildWindowListener());
		window.getContentPane().add(this.buildMainPanel(), "Center");
		window.setSize(400, 100);
		window.setVisible(true);
	}

	private WindowListener buildWindowListener() {
		return new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				e.getWindow().setVisible(false);
				System.exit(0);
			}
		};
	}

	private Component buildMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(this.buildTextFieldPanel(), BorderLayout.NORTH);
		mainPanel.add(this.buildControlPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}

	private Component buildTextFieldPanel() {
		JPanel taskListPanel = new JPanel(new GridLayout(1, 0));
		taskListPanel.add(this.buildNameTextField());
		taskListPanel.add(this.buildReadOnlyNameTextField());
		taskListPanel.add(this.buildUpperCaseNameTextField());
		return taskListPanel;
	}

	private JTextField buildNameTextField() {
		return new JTextField(this.nameDocument, null, 0);
	}

	private JTextField buildReadOnlyNameTextField() {
		JTextField nameTextField = this.buildNameTextField();
		nameTextField.setEditable(false);
		return nameTextField;
	}

	private JTextField buildUpperCaseNameTextField() {
		return new JTextField(this.upperCaseNameDocument, null, 0);
	}

	private Component buildControlPanel() {
		JPanel controlPanel = new JPanel(new GridLayout(1, 0));
		controlPanel.add(this.buildResetNameButton());
		controlPanel.add(this.buildClearModelButton());
		controlPanel.add(this.buildRestoreModelButton());
		controlPanel.add(this.buildPrintModelButton());
		return controlPanel;
	}

	private JButton buildResetNameButton() {
		return new JButton(this.buildResetNameAction());
	}

	private Action buildResetNameAction() {
		Action action = new AbstractAction("reset name") {
			public void actionPerformed(ActionEvent event) {
				DocumentAdapterUITest.this.resetName();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void resetName() {
		this.testModel.setName(DEFAULT_NAME);
	}

	private JButton buildClearModelButton() {
		return new JButton(this.buildClearModelAction());
	}

	private Action buildClearModelAction() {
		Action action = new AbstractAction("clear model") {
			public void actionPerformed(ActionEvent event) {
				DocumentAdapterUITest.this.clearModel();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void clearModel() {
		this.testModelHolder.setValue(null);
	}

	private JButton buildRestoreModelButton() {
		return new JButton(this.buildRestoreModelAction());
	}

	private Action buildRestoreModelAction() {
		Action action = new AbstractAction("restore model") {
			public void actionPerformed(ActionEvent event) {
				DocumentAdapterUITest.this.restoreModel();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void restoreModel() {
		this.testModelHolder.setValue(this.testModel);
	}

	private JButton buildPrintModelButton() {
		return new JButton(this.buildPrintModelAction());
	}

	private Action buildPrintModelAction() {
		Action action = new AbstractAction("print model") {
			public void actionPerformed(ActionEvent event) {
				DocumentAdapterUITest.this.printModel();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void printModel() {
		System.out.println("name: " + this.testModel.getName());
	}


	private class TestModel extends AbstractModel {
		private String name;
			public static final String NAME_PROPERTY = "name";
	
		public TestModel(String name) {
			this.name = name;
		}
		public String getName() {
			return this.name;
		}
		public void setName(String name) {
			Object old = this.name;
			this.name = name;
			this.firePropertyChanged(NAME_PROPERTY, old, name);
		}
		@Override
		public String toString() {
			return "TestModel(" + this.getName() + ")";
		}
	}

}
