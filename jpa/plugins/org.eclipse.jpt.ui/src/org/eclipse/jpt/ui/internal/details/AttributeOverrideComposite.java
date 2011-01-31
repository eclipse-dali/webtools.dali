/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.core.context.ReadOnlyColumn;
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
 * @see EntityOverridesComposite - The parent container
 * @see ColumnComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class AttributeOverrideComposite extends Pane<ReadOnlyAttributeOverride>
{

	/**
	 * Creates a new <code>AttributeOverrideComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>AttributeOverride</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public AttributeOverrideComposite(Pane<?> parentPane, 
								PropertyValueModel<? extends ReadOnlyAttributeOverride> subjectHolder,
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
	
	private PropertyValueModel<ReadOnlyColumn> buildColumnHolder() {
		return new TransformationPropertyValueModel<ReadOnlyAttributeOverride, ReadOnlyColumn>(getSubjectHolder()) {
			@Override
			protected ReadOnlyColumn transform_(ReadOnlyAttributeOverride value) {
				return value.getColumn();
			}
		};
	}
}