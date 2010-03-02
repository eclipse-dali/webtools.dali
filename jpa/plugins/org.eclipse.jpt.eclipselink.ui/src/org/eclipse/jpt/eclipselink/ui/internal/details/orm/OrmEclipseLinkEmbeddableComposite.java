/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkEmbeddable;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkEmbeddableAdvancedComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.AbstractEmbeddableComposite;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.details.orm.MetadataCompleteComposite;
import org.eclipse.jpt.ui.internal.details.orm.OrmJavaClassChooser;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * This pane does not have any widgets.
 *
 * @see Embeddable
 * @see EmbeddableUiProvider
 *
 * @version 2.3
 * @since 2.1
 */
public class OrmEclipseLinkEmbeddableComposite<T extends OrmEclipseLinkEmbeddable> 
	extends AbstractEmbeddableComposite<T>
	implements JpaComposite
{
	/**
	 * Creates a new <code>EmbeddableComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmEclipseLinkEmbeddableComposite(PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent,
	                           WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.initializeEmbeddableCollapsibleSection(container);
		initializeConvertersCollapsibleSection(container);
		initializeAdvancedCollapsibleSection(container);
	}

	@Override
	protected void initializeEmbeddableSection(Composite container) {
		new OrmJavaClassChooser(this, getSubjectHolder(), container);
		new AccessTypeComposite(this, buildAccessHolder(), container);
		new MetadataCompleteComposite(this, getSubjectHolder(), container);
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolder() {
		return new PropertyAspectAdapter<T, AccessHolder>(
			getSubjectHolder())
		{
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentType();
			}
		};
	}
	protected void initializeConvertersCollapsibleSection(Composite container) {

		container = addCollapsibleSection(
			addSubPane(container, 5),
			EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_converters
		);
		initializeConvertersSection(container, this.buildConverterHolder());
	}

	protected void initializeConvertersSection(Composite container, PropertyValueModel<EclipseLinkConverterHolder> converterHolder) {
		new OrmEclipseLinkConvertersComposite(this, converterHolder, container);
	}
	
	private PropertyValueModel<EclipseLinkConverterHolder> buildConverterHolder() {
		return new PropertyAspectAdapter<T, EclipseLinkConverterHolder>(getSubjectHolder()) {
			@Override
			protected EclipseLinkConverterHolder buildValue_() {
				return this.subject.getConverterHolder();
			}
		};
	}
	
	protected void initializeAdvancedCollapsibleSection(Composite container) {
		new EclipseLinkEmbeddableAdvancedComposite(this, container);
	}
}