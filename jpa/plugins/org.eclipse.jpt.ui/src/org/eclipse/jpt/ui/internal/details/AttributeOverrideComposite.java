/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | ColumnComposite                                                           |
 * |                                                                           |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see AttributeOverride
 * @see OverridesComposite - The parent container
 * @see ColumnComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class AttributeOverrideComposite extends FormPane<AttributeOverride>
{

	/**
	 * Creates a new <code>AttributeOverrideComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>AttributeOverride</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public AttributeOverrideComposite(FormPane<?> parentPane, 
								PropertyValueModel<? extends AttributeOverride> subjectHolder,
								Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		new ColumnComposite(
			this,
			buildColumnHolder(),
			container,
			false
		);
	}
	
	private PropertyValueModel<Column> buildColumnHolder() {
		return new TransformationPropertyValueModel<AttributeOverride, Column>(getSubjectHolder()) {
			@Override
			protected Column transform_(AttributeOverride value) {
				return value.getColumn();
			}
		};
	}
}