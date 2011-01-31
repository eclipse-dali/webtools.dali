/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.AbstractEmbeddedMappingComposite;
import org.eclipse.jpt.ui.internal.jpa2.details.EmbeddedMapping2_0OverridesComposite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EmbeddedAttributeOverridesComposite                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EmbeddedMapping
 *
 * @version 2.3
 * @since 1.0
 */
public class JavaEmbeddedMapping2_0Composite extends AbstractEmbeddedMappingComposite<JavaEmbeddedMapping>
                                      implements JpaComposite
{
	/**
	 * Creates a new <code>EmbeddedMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>EmbeddedMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JavaEmbeddedMapping2_0Composite(PropertyValueModel<? extends JavaEmbeddedMapping> subjectHolder,
	                                Composite parent,
	                                WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	protected void initializeEmbeddedSection(Composite container) {
		new EmbeddedMapping2_0OverridesComposite(
			this,
			container
		);
	}
}