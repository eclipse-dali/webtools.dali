/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AccessHolder;
import org.eclipse.jpt.jpa.core.context.GeneratorContainer;
import org.eclipse.jpt.jpa.core.context.QueryContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkEntity;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.jpa.ui.internal.details.EntityNameComposite;
import org.eclipse.jpt.jpa.ui.internal.details.IdClassComposite;
import org.eclipse.jpt.jpa.ui.internal.details.TableComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Entity2_0OverridesComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Generation2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Queries2_0Composite;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for an EclipseLink1.1 Java entity.
 *
 * @see JavaEclipseLinkEntity
 * @see EclipselinkJpaUiFactory - The factory creating this pane
 *
 * @version 2.3
 * @since 2.2
 */
public class JavaEclipseLinkEntity2_0Composite
	extends AbstractJavaEclipseLinkEntityComposite<JavaEntity>
{
	/**
	 * Creates a new <code>EclipseLinkJavaEntityComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>JavaEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JavaEclipseLinkEntity2_0Composite(
			PropertyValueModel<? extends JavaEntity> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	protected void initializeEntitySection(Composite container) {
		new TableComposite(this, container);
		new EntityNameComposite(this, container);	
		new AccessTypeComposite(this, buildAccessHolder(), container);	
		new IdClassComposite(this, buildIdClassReferenceHolder(), container);
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolder() {
		return new PropertyAspectAdapter<JavaEntity, AccessHolder>(getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentType();
			}
		};
	}

	@Override
	protected void initializeAttributeOverridesSection(Composite container) {
		new Entity2_0OverridesComposite(this, container);
	}

	@Override
	protected void initializeGeneratorsSection(Composite container, PropertyValueModel<GeneratorContainer> generatorContainerHolder) {
		new Generation2_0Composite(this, generatorContainerHolder, container);
	}

	@Override
	protected void initializeCachingSection(Composite container, PropertyValueModel<JavaEclipseLinkCaching> cachingHolder) {
		new JavaEclipseLinkCaching2_0Composite(this, cachingHolder, container);
	}

	@Override
	protected void initializeQueriesSection(Composite container, PropertyValueModel<QueryContainer> queryContainerHolder) {
		new Queries2_0Composite(this, queryContainerHolder, container);
	}
}
