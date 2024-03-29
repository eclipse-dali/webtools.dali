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
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.ComboBoxModelAdapter;
import org.eclipse.jpt.common.utility.internal.swing.FilteringListBrowser;
import org.eclipse.jpt.common.utility.internal.swing.ListChooser;
import org.eclipse.jpt.common.utility.internal.swing.SimpleListCellRenderer;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;


/**
 * Play around with a set of combo-boxes.
 * 
 * DefaultLongListBrowserDialogUITest subclasses this class; so be
 * careful when making changes.
 */
@SuppressWarnings("nls")
public class ComboBoxModelAdapterUITest {

	protected JFrame window;
	private TestModel testModel;
	private ModifiablePropertyValueModel<TestModel> testModelHolder;
	private ModifiablePropertyValueModel<Object> colorHolder;
	private SimpleListValueModel<String> colorListHolder;
	protected ComboBoxModel colorComboBoxModel;
	private int nextColorNumber = 0;

	public static void main(String[] args) throws Exception {
		new ComboBoxModelAdapterUITest().exec();
	}

	protected ComboBoxModelAdapterUITest() {
		super();
	}

	protected void exec() throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());	// Metal LAF
//		UIManager.setLookAndFeel(com.sun.java.swing.plaf.windows.WindowsLookAndFeel.class.getName());
//		UIManager.setLookAndFeel(com.sun.java.swing.plaf.motif.MotifLookAndFeel.class.getName());
//		UIManager.setLookAndFeel(oracle.bali.ewt.olaf.OracleLookAndFeel.class.getName());
		this.testModel = this.buildTestModel();
		this.testModelHolder = new SimplePropertyValueModel<TestModel>(this.testModel);
		this.colorHolder = this.buildColorHolder(this.testModelHolder);
		this.colorListHolder = this.buildColorListHolder();
		this.colorComboBoxModel = this.buildComboBoxModelAdapter(this.colorListHolder, this.colorHolder);
		this.openWindow();
	}

	private ModifiablePropertyValueModel<Object> buildColorHolder(PropertyValueModel<TestModel> vm) {
		return new PropertyAspectAdapter<TestModel, Object>(vm, TestModel.COLOR_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getColor();
			}
			@Override
			protected void setValue_(Object value) {
				this.subject.setColor((String) value);
			}
		};
	}

	protected TestModel buildTestModel() {
		return new TestModel();
	}

	private SimpleListValueModel<String> buildColorListHolder() {
		return new SimpleListValueModel<String>(TestModel.validColors());
//		return new AbstractReadOnlyListValueModel() {
//			public Object value() {
//				return new ArrayListIterator(TestModel.VALID_COLORS);
//			}
//			public int size() {
//				return TestModel.VALID_COLORS.length;
//			}
//		};
	}

	protected ListValueModel<String> uiColorListHolder() {
		return this.colorListHolder;
	}

	private ComboBoxModel buildComboBoxModelAdapter(ListValueModel<String> listHolder, ModifiablePropertyValueModel<Object> selectionHolder) {
		return new ComboBoxModelAdapter(listHolder, selectionHolder);
	}

	private void openWindow() {
		this.window = new JFrame(this.getClass().getSimpleName());
		this.window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.window.addWindowListener(this.buildWindowListener());
		this.window.getContentPane().add(this.buildMainPanel(), "Center");
		this.window.setLocation(300, 300);
		this.window.setSize(400, 150);
		this.window.setVisible(true);
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
		mainPanel.add(this.buildComboBoxPanel(), BorderLayout.NORTH);
		mainPanel.add(this.buildControlPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}

	protected JPanel buildComboBoxPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 0));
		panel.add(this.buildComboBox());
		panel.add(this.buildComboBox());
		panel.add(this.buildListChooser1());
		panel.add(this.buildListChooser2());
		return panel;
	}

	private JComboBox buildComboBox() {
		JComboBox comboBox = new JComboBox(this.colorComboBoxModel);
		comboBox.setRenderer(this.buildComboBoxRenderer());
		return comboBox;
	}

	protected ListCellRenderer buildComboBoxRenderer() {
		return new SimpleListCellRenderer() {
			@Override
			protected String buildText(Object value) {
				return super.buildText(value);
			}
		};
	}

	private ListChooser buildListChooser1() {
		return new LocalListChooser1(this.colorComboBoxModel);
	}

	private ListChooser buildListChooser2() {
		return new LocalListChooser2(this.colorComboBoxModel);
	}

	private Component buildControlPanel() {
		JPanel controlPanel = new JPanel(new GridLayout(2, 0));
		controlPanel.add(this.buildResetColorButton());
		controlPanel.add(this.buildClearModelButton());
		controlPanel.add(this.buildRestoreModelButton());
		controlPanel.add(this.buildPrintModelButton());
		controlPanel.add(this.buildAddTenButton());
		controlPanel.add(this.buildRemoveTenButton());
		return controlPanel;
	}

	// ********** reset color button **********
	private JButton buildResetColorButton() {
		return new JButton(this.buildResetColorAction());
	}

	private Action buildResetColorAction() {
		Action action = new AbstractAction("reset color") {
			public void actionPerformed(ActionEvent event) {
				ComboBoxModelAdapterUITest.this.resetColor();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void resetColor() {
		this.testModel.setColor(TestModel.DEFAULT_COLOR);
	}

	// ********** clear model button **********
	private JButton buildClearModelButton() {
		return new JButton(this.buildClearModelAction());
	}

	private Action buildClearModelAction() {
		Action action = new AbstractAction("clear model") {
			public void actionPerformed(ActionEvent event) {
				ComboBoxModelAdapterUITest.this.clearModel();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void clearModel() {
		this.testModelHolder.setValue(null);
	}

	// ********** restore model button **********
	private JButton buildRestoreModelButton() {
		return new JButton(this.buildRestoreModelAction());
	}

	private Action buildRestoreModelAction() {
		Action action = new AbstractAction("restore model") {
			public void actionPerformed(ActionEvent event) {
				ComboBoxModelAdapterUITest.this.restoreModel();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void restoreModel() {
		this.testModelHolder.setValue(this.testModel);
	}

	// ********** print model button **********
	private JButton buildPrintModelButton() {
		return new JButton(this.buildPrintModelAction());
	}

	private Action buildPrintModelAction() {
		Action action = new AbstractAction("print model") {
			public void actionPerformed(ActionEvent event) {
				ComboBoxModelAdapterUITest.this.printModel();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void printModel() {
		System.out.println(this.testModel);
	}

	// ********** add 20 button **********
	private JButton buildAddTenButton() {
		return new JButton(this.buildAddTenAction());
	}

	private Action buildAddTenAction() {
		Action action = new AbstractAction("add 20") {
			public void actionPerformed(ActionEvent event) {
				ComboBoxModelAdapterUITest.this.addTen();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void addTen() {
		for (int i = this.nextColorNumber; i < this.nextColorNumber + 20; i++) {
			this.colorListHolder.add(this.colorListHolder.size(), "color" + i);
		}
		this.nextColorNumber += 20;
	}

	// ********** remove 20 button **********
	private JButton buildRemoveTenButton() {
		return new JButton(this.buildRemoveTenAction());
	}

	private Action buildRemoveTenAction() {
		Action action = new AbstractAction("remove 20") {
			public void actionPerformed(ActionEvent event) {
				ComboBoxModelAdapterUITest.this.removeTen();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void removeTen() {
		for (int i = 0; i < 20; i++) {
			if (this.colorListHolder.size() > 0) {
				this.colorListHolder.remove(this.colorListHolder.size() - 1);
			}
		}
	}


	protected static class TestModel extends AbstractModel {
		private String color;
			public static final String COLOR_PROPERTY = "color";
			public static final String RED = "red";
			public static final String ORANGE = "orange";
			public static final String YELLOW = "yellow";
			public static final String GREEN = "green";
			public static final String BLUE = "blue";
			public static final String INDIGO = "indigo";
			public static final String VIOLET = "violet";
			public static final String DEFAULT_COLOR = RED;
			public static List<String> validColors;
			public static final String[] DEFAULT_VALID_COLORS = {
				RED,
				ORANGE,
				YELLOW,
				GREEN,
				BLUE,
				INDIGO,
				VIOLET
			};
	
		public static List<String> validColors() {
			if (validColors == null) {
				validColors = buildDefaultValidColors();
			}
			return validColors;
		}
		public static List<String> buildDefaultValidColors() {
			List<String> result = new ArrayList<String>();
			CollectionTools.addAll(result, DEFAULT_VALID_COLORS);
			return result;
		}
	
		public TestModel() {
			this(DEFAULT_COLOR);
		}
		public TestModel(String color) {
			this.color = color;
		}
		public String getColor() {
			return this.color;
		}
		public void setColor(String color) {
			this.checkColor(color);
			Object old = this.color;
			this.color = color;
			this.firePropertyChanged(COLOR_PROPERTY, old, color);
		}
		public void checkColor(String c) {
			if ( ! validColors().contains(c)) {
				throw new IllegalArgumentException(c);
			}
		}
		@Override
		public String toString() {
			return "TestModel(" + this.color + ")";
		}
	}


	private class LocalListChooser1 extends ListChooser {
		public LocalListChooser1(ComboBoxModel model) {
			super(model);
		}
	}


	private class LocalListChooser2 extends ListChooser {
		public LocalListChooser2(ComboBoxModel model) {
			super(model);
		}
		@Override
		protected ListBrowser buildBrowser() {
			return new FilteringListBrowser<String>();
		}
	}

}
