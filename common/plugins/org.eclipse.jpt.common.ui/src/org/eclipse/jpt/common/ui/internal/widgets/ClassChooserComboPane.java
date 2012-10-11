/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import org.eclipse.jdt.internal.ui.refactoring.contentassist.ControlContentAssistHelper;
import org.eclipse.jpt.common.utility.internal.transformer.StringObjectTransformer;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 * This chooser allows the user to choose a type when browsing and it adds code
 * completion support to the text field, which is the main component.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------
 * |  ---------------------------------------------------- ------------- |
 * |  | I                                             X  | | Browse... | |
 * |  ---------------------------------------------------- ------------- |
 * -----------------------------------------------------------------------</pre>
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class ClassChooserComboPane<T extends Model> extends ClassChooserPane<T>
{

	/**
	 * Creates a new <code>ClassChooserComboPane</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public ClassChooserComboPane(Pane<? extends T> parentPane,
	                        Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>ClassChooserComboPane</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 * @param hyperlink include a Hyperlink widget to select/or create a Type
	 */
	public ClassChooserComboPane(Pane<? extends T> parentPane,
        	Composite parent,
        	Hyperlink hyperlink) {

		super(parentPane, parent, hyperlink);
	}

	/**
	 * Creates a new <code>ClassChooserComboPane</code>.
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

	/**
	 * Creates a new <code>ClassChooserComboPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param hyperlink include a Hyperlink widget to select/or create a Type
	 */
	public ClassChooserComboPane(Pane<?> parentPane,
	                        PropertyValueModel<? extends T> subjectHolder,
	                        Composite parent,
	                        Hyperlink hyperlink) {

		super(parentPane, subjectHolder, parent, hyperlink);
	}


	@Override
	protected Control addMainControl(Composite container) {
    	Combo combo = this.addClassCombo(container);

		ControlContentAssistHelper.createComboContentAssistant(
			combo,
			javaTypeCompletionProcessor
		);

		return combo;
	}
	
	protected Combo addClassCombo(Composite container) {
		return this.addEditableCombo(
			container,
			this.buildClassListHolder(),
			this.buildTextHolder(),
			this.buildClassConverter(),
			getHelpId()
		);
 	}
	
	protected abstract ListValueModel<String> buildClassListHolder();
	
	protected Transformer<String, String> buildClassConverter() {
		return StringObjectTransformer.instance();
	}
}