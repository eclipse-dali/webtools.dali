/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards.gen;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Collection of utility methods to create SWT UI
 *
 */
public class SWTUtil {
	/**
	 * Set the layoutData of the input control to occupy specified number of columns
	 * @param c
	 * @param columns
	 */
	public static void fillColumns(Control c, int columns){
		GridData layoutData = new GridData();
		layoutData.horizontalSpan = columns;
		layoutData.verticalAlignment = SWT.FILL;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = false;
		c.setLayoutData(layoutData);
		return ;
	}

	public static void fillColumnsWithIndent(Control c, int columns, int indent){
		GridData layoutData = new GridData();
		layoutData.horizontalSpan = columns;
		layoutData.verticalAlignment = SWT.FILL;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = false;
		layoutData.horizontalIndent = indent ;
		c.setLayoutData(layoutData);
		return ;
	}
	
	public static Label createLabel(Composite container, int span, String text) {
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		label.setLayoutData(gd);
		return label;
	}

	/**
	 * Create a new label which occupies one grid column
	 * @param parent
	 * @param text
	 */
	public static Label newLabel(Composite parent, String text) {
		Label label = new Label( parent, SWT.NONE);
		label.setText( text );
		label.setLayoutData(new GridData());
		return label;
	}

	/**
	 * Create a new label which occupies one grid column
	 * @param parent
	 * @param text
	 */
	public static Label newLabelWithIndent(Composite parent, String text, int indent) {
		Label label = new Label( parent, SWT.NONE);
		label.setText( text );
		GridData layoutData = new GridData();
		layoutData.horizontalAlignment = SWT.BEGINNING;
		layoutData.verticalAlignment = SWT.TOP ;
		layoutData.horizontalIndent = indent ;
		label.setLayoutData(layoutData);
		return label;
	}
	
	/**
	 * Creates a separator line. Expects a <code>GridLayout</code> with at least 1 column.
	 * 
	 * @param composite the parent composite
	 * @param nColumns number of columns to span
	 */
	@SuppressWarnings("restriction")
	public static void createSeparator(Composite composite, int nColumns) {
		(new org.eclipse.jdt.internal.ui.wizards.dialogfields.Separator(
				SWT.SEPARATOR | SWT.HORIZONTAL)).doFillIntoGrid(composite, nColumns, 5);		
	}

	
	public static Button createButton(Composite container, int span, String text, int style) {
		Button btn = new Button(container, style);
		btn.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		btn.setLayoutData(gd);
		return btn;
	}
	
	public static Combo createCombo(Composite container, int span ) {
		Combo combo = new Combo(container, SWT.SINGLE | SWT.READ_ONLY);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		gd.grabExcessHorizontalSpace=true;
		gd.horizontalAlignment = SWT.FILL;
		combo.setLayoutData(gd);
		return combo;
	}

	public static Text createText(Composite container, int span ) {
		Text text = new Text(container, SWT.BORDER);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		gd.grabExcessHorizontalSpace=true;
		gd.horizontalAlignment = SWT.FILL;
		text.setLayoutData(gd);
		return text;
	}
	
}
