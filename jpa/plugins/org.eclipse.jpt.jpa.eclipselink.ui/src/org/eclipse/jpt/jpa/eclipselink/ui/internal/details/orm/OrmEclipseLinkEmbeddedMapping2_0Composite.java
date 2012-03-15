/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AccessHolder;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEmbeddedMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.jpa.ui.internal.details.java.BaseJavaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmMappingNameChooser;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.EmbeddedMapping2_0OverridesComposite;
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
 * @see BaseJavaUiFactory - The factory creating this pane
 *
 * @version 2.3
 * @since 2.2
 */
public class OrmEclipseLinkEmbeddedMapping2_0Composite
	extends AbstractEmbeddedMappingComposite<EmbeddedMapping>
{
	/**
	 * Creates a new <code>EmbeddedMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>EmbeddedMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmEclipseLinkEmbeddedMapping2_0Composite(PropertyValueModel<? extends EmbeddedMapping> subjectHolder,
	                                Composite parent,
	                                WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeEmbeddedSection(Composite container) {
		new OrmMappingNameChooser(this, getSubjectHolder(), container);
		new OrmAttributeTypeComposite(this, getSubjectHolder(), container);
		new AccessTypeComposite(this, buildAccessHolderHolder(), container);

		new EmbeddedMapping2_0OverridesComposite(
			this,
			container
		);
	}

	protected PropertyValueModel<AccessHolder> buildAccessHolderHolder() {
		return new PropertyAspectAdapter<EmbeddedMapping, AccessHolder>(getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}
}