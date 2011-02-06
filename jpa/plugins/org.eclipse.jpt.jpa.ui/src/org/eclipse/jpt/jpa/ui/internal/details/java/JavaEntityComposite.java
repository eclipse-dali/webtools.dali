/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEntityComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for a Java entity.
 *
 * @see JavaEntity
 * @see JavaUiFactory - The factory creating this pane
 * @see JavaSecondaryTablesComposite
 *
 * @version 2.3
 * @since 1.0
 */
public class JavaEntityComposite extends AbstractEntityComposite<JavaEntity>
{
	/**
	 * Creates a new <code>JavaEntityComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>JavaEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JavaEntityComposite(PropertyValueModel<? extends JavaEntity> subjectHolder,
	                           Composite parent,
	                           WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeSecondaryTablesSection(Composite container) {
		new JavaSecondaryTablesComposite(this, container);
	}

	@Override
	protected void initializeInheritanceSection(Composite container) {
		new JavaInheritanceComposite(this, container);
	}
}