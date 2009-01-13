/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.ConverterHolder;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEmbeddable;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkEmbeddableAdvancedComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.orm.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.orm.details.MetadataCompleteComposite;
import org.eclipse.jpt.ui.internal.orm.details.OrmJavaClassChooser;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * This pane does not have any widgets.
 *
 * @see Embeddable
 * @see BaseJpaUiFactory - The factory creating this pane
 * @see EmbeddableUiProvider
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkOrmEmbeddableComposite extends FormPane<OrmEmbeddable>
                                 implements JpaComposite
{
	/**
	 * Creates a new <code>EmbeddableComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkOrmEmbeddableComposite(PropertyValueModel<? extends OrmEmbeddable> subjectHolder,
	                           Composite parent,
	                           WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeGeneralPane(container);
		initializeConvertersPane(container);
		initializeAdvancedPane(container);
	}
	
	protected void initializeGeneralPane(Composite container) {
		new OrmJavaClassChooser(this, getSubjectHolder(), container);
		new AccessTypeComposite(this, getSubjectHolder(), container);
		new MetadataCompleteComposite(this, getSubjectHolder(), container);
	}

	protected void initializeConvertersPane(Composite container) {

		container = addCollapsableSection(
			addSubPane(container, 5),
			EclipseLinkUiMappingsMessages.ConvertersComposite_Label
		);

		new ConvertersComposite(this, buildConverterHolder(), container);
	}
	
	private PropertyValueModel<ConverterHolder> buildConverterHolder() {
		return new PropertyAspectAdapter<OrmEmbeddable, ConverterHolder>(getSubjectHolder()) {
			@Override
			protected ConverterHolder buildValue_() {
				return ((EclipseLinkOrmEmbeddable) this.subject).getConverterHolder();
			}
		};
	}
	
	protected void initializeAdvancedPane(Composite container) {
		new EclipseLinkEmbeddableAdvancedComposite(this, container);
	}
}