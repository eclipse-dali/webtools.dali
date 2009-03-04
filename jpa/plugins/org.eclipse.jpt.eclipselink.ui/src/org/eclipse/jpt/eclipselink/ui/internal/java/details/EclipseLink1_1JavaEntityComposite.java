/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.java.details;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.eclipselink.core.context.java.JavaCaching;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConverterHolder;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkEntityAdvancedComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.java.details.JavaInheritanceComposite;
import org.eclipse.jpt.ui.internal.java.details.JavaSecondaryTablesComposite;
import org.eclipse.jpt.ui.internal.mappings.details.AbstractEntityComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EntityNameComposite;
import org.eclipse.jpt.ui.internal.mappings.details.IdClassComposite;
import org.eclipse.jpt.ui.internal.mappings.details.TableComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for an EclipseLink1.1 Java entity.
 *
 * @see EclipseLinkJavaEntity
 * @see EclipselinkJpaUiFactory - The factory creating this pane
 *
 * @version 2.2
 * @since 2.2
 */
public class EclipseLink1_1JavaEntityComposite extends AbstractEntityComposite<JavaEntity>
{
	/**
	 * Creates a new <code>EclipseLinkJavaEntityComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>JavaEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLink1_1JavaEntityComposite(PropertyValueModel<? extends JavaEntity> subjectHolder,
	                           Composite parent,
	                           WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	
	@Override
	protected void initializeLayout(Composite container) {
		initializeGeneralPane(container);
		initializeCachingPane(container);
		initializeQueriesPane(container);
		initializeInheritancePane(container);
		initializeAttributeOverridesPane(container);
		initializeGeneratorsPane(container);
		initializeConvertersPane(container);
		initializeSecondaryTablesPane(container);
		initializeAdvancedPane(container);
	}
	
	@Override
	protected void initializeGeneralPane(Composite container) {
		int groupBoxMargin = getGroupBoxMargin();

		new TableComposite(this, container);
		new EntityNameComposite(this, addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin));	
		new AccessTypeComposite(this, buildAccessHolder(), addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin));	
		new IdClassComposite(this, addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin), false);
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolder() {
		return new PropertyAspectAdapter<JavaEntity, AccessHolder>(
			getSubjectHolder())
		{
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentType();
			}
		};
	}
	
	protected void initializeCachingPane(Composite container) {

		container = addCollapsableSection(
			addSubPane(container, 5),
			EclipseLinkUiMappingsMessages.EclipseLinkTypeMappingComposite_caching
		);

		new JavaCachingComposite(this, buildCachingHolder(), container);
	}

	private PropertyAspectAdapter<JavaEntity, JavaCaching> buildCachingHolder() {
		return new PropertyAspectAdapter<JavaEntity, JavaCaching>(
			getSubjectHolder())
		{
			@Override
			protected JavaCaching buildValue_() {
				return ((EclipseLinkJavaEntity) this.subject).getCaching();
			}
		};
		
	}

	protected void initializeConvertersPane(Composite container) {

		container = addCollapsableSection(
			container,
			EclipseLinkUiMappingsMessages.EclipseLinkTypeMappingComposite_converters
		);

		new ConvertersComposite(this, buildConverterHolderValueModel(), container);
	}

	protected PropertyValueModel<JavaConverterHolder> buildConverterHolderValueModel() {
		return new PropertyAspectAdapter<JavaEntity, JavaConverterHolder>(getSubjectHolder()) {
			@Override
			protected JavaConverterHolder buildValue_() {
				return ((EclipseLinkJavaEntity) this.subject).getConverterHolder();
			}	
		};
	}
	
	@Override
	protected void addSecondaryTablesComposite(Composite container) {
		new JavaSecondaryTablesComposite(this, container);
	}
	
	@Override
	protected void addInheritanceComposite(Composite container) {
		new JavaInheritanceComposite(this, container);
	}
	
	protected void initializeAdvancedPane(Composite container) {
		new EclipseLinkEntityAdvancedComposite(this, container);
	}
}