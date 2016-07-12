/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.SpecifiedAttributeOverride;
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
 * @see SpecifiedAttributeOverride
 * @see EntityOverridesComposite - The parent container
 * @see ColumnComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class AttributeOverrideComposite extends Pane<AttributeOverride>
{

	/**
	 * Creates a new <code>AttributeOverrideComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>AttributeOverride</code>
	 * @param parent The parent container
	 */
	public AttributeOverrideComposite(Pane<?> parentPane, 
								PropertyValueModel<? extends AttributeOverride> subjectHolder,
								PropertyValueModel<Boolean> enabledModel,
								Composite parent) {

		super(parentPane, subjectHolder, enabledModel, parent);
	}

	@Override
	protected Composite addComposite(Composite container) {
		return (Composite) new ColumnComposite(
			this,
			buildColumnModel(),
			container
		).getControl();
	}

	@Override
	protected void initializeLayout(Composite container) {
		//see addContainer(Composite) - reducing USER handles
	}
	
	private PropertyValueModel<Column> buildColumnModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), AttributeOverride.COLUMN_TRANSFORMER);
	}
}