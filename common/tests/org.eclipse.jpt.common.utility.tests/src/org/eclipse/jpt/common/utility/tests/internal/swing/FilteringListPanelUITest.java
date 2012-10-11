/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.swing;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.common.utility.internal.Classpath;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.swing.FilteringListPanel;
import org.eclipse.jpt.common.utility.internal.swing.SimpleListCellRenderer;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Simple test class for playing around with {@link FilteringListPanel}.
 * <p>
 * Optional command line parm:<ul>
 * <li>the name of a jar (or class folder) to use to populate the list box
 * </ul>
 */
@SuppressWarnings("nls")
public class FilteringListPanelUITest {
	private Type[] completeList1;
	private Type[] completeList2;
	private FilteringListPanel<Type> filteringListPanel;
	private Font font;


	public static void main(String[] args) {
		new FilteringListPanelUITest().exec(args);
	}

	private FilteringListPanelUITest() {
		super();
		this.initialize();
	}

	private void initialize() {
		this.font = this.buildFont();
	}

	private Font buildFont() {
		return new Font("Dialog", Font.PLAIN, 12);
	}

	private void exec(String[] args) {
		this.completeList1 = this.buildTypeList(args);
		this.completeList2 = this.buildCompleteList2();
		JFrame frame = new JFrame(this.getClass().getSimpleName());
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(this.buildWindowListener());
		frame.getContentPane().add(this.buildMainPanel(), "Center");
		frame.setLocation(300, 300);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}

	private Type[] buildTypeList(String[] args) {
		return ArrayTools.sort(ArrayTools.array(this.buildTypes(args), Type.class));
	}

	private Type[] buildCompleteList2() {
		String classpathEntry = Classpath.locationFor(this.getClass());
		return ArrayTools.sort(ArrayTools.array(this.buildTypes(new String[] {classpathEntry}), Type.class));
	}

	private Iterable<Type> buildTypes(String[] args) {
		return new TransformationIterable<String, Type>(this.buildClassNames(args), TYPE_STRING_TRANSFORMER);
	}

	private static final Transformer<String, Type> TYPE_STRING_TRANSFORMER = new TypeStringTransformer();
	/* CU private */ static class TypeStringTransformer
		extends TransformerAdapter<String, Type>
	{
		@Override
		public Type transform(String string) {
			return new Type(string);
		}
	}

	private Iterable<String> buildClassNames(String[] args) {
		return ((args == null) || (args.length == 0)) ?
				Classpath.bootClasspath().getClassNames() :
				new Classpath(new String[] { args[0] }).getClassNames();
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

	private JPanel buildMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		this.filteringListPanel = this.buildFilteringListPanel();
		panel.add(this.filteringListPanel, BorderLayout.CENTER);
		panel.add(this.buildControlPanel(), BorderLayout.SOUTH);
		return panel;
	}

	private FilteringListPanel<Type> buildFilteringListPanel() {
		Type initialSelection = this.getTypeNamed(this.completeList1, "java.lang.Object");
		FilteringListPanel<Type> panel = new FilteringListPanel<Type>(this.completeList1, initialSelection, TYPE_NAME_TRANSFORMER);
		panel.setTextFieldLabelText("Choose a Type (? = any char, * = any string):");
		panel.setListBoxLabelText("Matching Types:");
		panel.setComponentsFont(this.font);
		panel.setListBoxCellRenderer(new TypeCellRenderer());
		return panel;
	}

	private static final Transformer<Type, String> TYPE_NAME_TRANSFORMER = new TypeNameTransformer();
	/* CU private */ static class TypeNameTransformer
		extends TransformerAdapter<Type, String>
	{
		@Override
		public String transform(Type type) {
			return (type == null) ? StringTools.EMPTY_STRING : type.getName();
		}
	}

	/* CU private */ class TypeCellRenderer
		extends SimpleListCellRenderer
	{
		private static final long serialVersionUID = 1L;
		@Override
		protected Icon buildIcon(Object value) {
			return UIManager.getIcon("Tree.leafIcon");
		}
		@Override
		protected String buildText(Object value) {
			return ((Type) value).getName();
		}
	}

	private JPanel buildControlPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 0));
		panel.add(this.buildSwapButton());
		panel.add(this.buildStringButton());
		panel.add(this.buildNullButton());
		panel.add(this.buildMax10Button());
		panel.add(this.buildPrintButton());
		return panel;
	}

	// ********** swap button **********

	private JButton buildSwapButton() {
		JButton button = new JButton(this.buildSwapAction());
		button.setFont(this.font);
		return button;
	}

	private Action buildSwapAction() {
		return new AbstractAction("swap") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent event) {
				FilteringListPanelUITest.this.swap();
			}
		};
	}

	/**
	 * swap in a new list
	 */
	void swap() {
		if (this.filteringListPanel.getCompleteList() == this.completeList1) {
			this.filteringListPanel.setCompleteList(this.completeList2);
		} else {
			this.filteringListPanel.setCompleteList(this.completeList1);
		}
	}

	// ********** string button **********

	private JButton buildStringButton() {
		JButton button = new JButton(this.buildStringAction());
		button.setFont(this.font);
		return button;
	}

	private Action buildStringAction() {
		return new AbstractAction("String") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent event) {
				FilteringListPanelUITest.this.selectStringType();
			}
		};
	}

	/**
	 * force a selection from "outside" the filtering list panel
	 */
	void selectStringType() {
		this.filteringListPanel.setSelection(this.typeNamed("java.lang.String"));
	}

	private Type typeNamed(String name) {
		return this.getTypeNamed(this.filteringListPanel.getCompleteList(), name);
	}

	private Type getTypeNamed(Type[] types, String name) {
		for (int i = types.length; i-- > 0; ) {
			Type type = types[i];
			if (type.getName().equals(name)) {
				return type;
			}
		}
		return null;
	}

	// ********** null button **********

	private JButton buildNullButton() {
		JButton button = new JButton(this.buildNullAction());
		button.setFont(this.font);
		return button;
	}

	private Action buildNullAction() {
		return new AbstractAction("null") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent event) {
				FilteringListPanelUITest.this.selectNull();
			}
		};
	}

	/**
	 * set the current selection to null
	 */
	void selectNull() {
		this.filteringListPanel.setSelection(null);
	}

	// ********** null button **********

	private JButton buildMax10Button() {
		JButton button = new JButton(this.buildMax10Action());
		button.setFont(this.font);
		return button;
	}

	private Action buildMax10Action() {
		return new AbstractAction("max = 10") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent event) {
				FilteringListPanelUITest.this.setMax10();
			}
		};
	}

	/**
	 * toggle between allowing only 10 entries in the list box
	 * and no limit
	 */
	void setMax10() {
		if (this.filteringListPanel.getMaxListSize() == 10) {
			this.filteringListPanel.setMaxListSize(-1);
		} else {
			this.filteringListPanel.setMaxListSize(10);
		}
	}

	// ********** print button **********

	private JButton buildPrintButton() {
		JButton button = new JButton(this.buildPrintAction());
		button.setFont(this.font);
		return button;
	}

	private Action buildPrintAction() {
		return new AbstractAction("print") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent event) {
				FilteringListPanelUITest.this.printType();
			}
		};
	}

	/**
	 * print the currently selected type to the console
	 */
	void printType() {
		System.out.println("selected item: " + this.filteringListPanel.getSelection());
	}


	// ********** type **********

	/* CU private */ static class Type
		implements Comparable<Type>
	{
		private String name;

		Type(String name) {
			super();
			this.name = name;
		}
		public String shortName() {
			return ClassNameTools.simpleName(this.name);
		}
		public String getName() {
			return this.name;
		}
		@Override
		public String toString() {
			return "Type: " + this.name ;
		}
		public int compareTo(Type type) {
			return this.name.compareTo(type.name);
		}
	}
}
