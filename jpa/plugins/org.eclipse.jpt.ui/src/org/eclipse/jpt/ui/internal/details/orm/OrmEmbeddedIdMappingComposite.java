/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.orm;

import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.AbstractEmbeddedIdMappingComposite;
import org.eclipse.jpt.ui.internal.details.EmbeddedMappingOverridesComposite;
import org.eclipse.jpt.ui.internal.details.orm.OrmMappingNameChooser;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here's the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EmbeddedAttributeOverridesComposite                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EmbeddedIdMapping
 * @see BaseJavaUiFactory - The factory creating this pane
 *
 * @version 2.3
 * @since 2.2
 */
public class OrmEmbeddedIdMappingComposite extends AbstractEmbeddedIdMappingComposite<OrmEmbeddedIdMapping>
                                        implements JpaComposite
{
	/**
	 * Creates a new <code>EclipseLink1_1OrmEmbeddedIdMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>EmbeddedIdMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmEmbeddedIdMappingComposite(PropertyValueModel<? extends OrmEmbeddedIdMapping> subjectHolder,
	                                  Composite parent,
	                                  WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	protected void initializeEmbeddedIdSection(Composite container) {
		new OrmMappingNameChooser(this, getSubjectHolder(), container);

		new EmbeddedMappingOverridesComposite(
			this,
			container
		);
	}	
}