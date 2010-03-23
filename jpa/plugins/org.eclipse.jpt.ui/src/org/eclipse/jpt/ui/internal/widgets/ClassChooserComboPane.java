/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import org.eclipse.jdt.internal.ui.refactoring.contentassist.ControlContentAssistHelper;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This chooser allows the user to choose a type when browsing and it adds code
 * completion support to the text field, which is the main component.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |        ---------------------------------------------------- ------------- |
 * | Label: | I                                             X  | | Browse... | |
 * |        ---------------------------------------------------- ------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class ClassChooserComboPane<T extends Model> extends ClassChooserPane<T>
{

	/**
	 * Creates a new <code>ClassChooserPane</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public ClassChooserComboPane(Pane<? extends T> parentPane,
	                        Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>ClassChooserPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public ClassChooserComboPane(Pane<?> parentPane,
	                        PropertyValueModel<? extends T> subjectHolder,
	                        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected Control addMainControl(Composite container) {
		Composite subPane = addSubPane(container);
    	Combo combo = this.addClassCombo(subPane);

		ControlContentAssistHelper.createComboContentAssistant(
			combo,
			javaTypeCompletionProcessor
		);

		return subPane;
	}
	
	protected Combo addClassCombo(Composite container) {
		return this.addEditableCombo(
			container,
			this.buildClassListHolder(),
			this.buildTextHolder(),
			this.buildClassConverter()
		);
 	}
	
	protected abstract ListValueModel<String> buildClassListHolder();
	
	protected StringConverter<String> buildClassConverter() {
		return StringConverter.Default.instance();
	}
}