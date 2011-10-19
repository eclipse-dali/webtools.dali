/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.QueryContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.QueriesComposite;
import org.eclipse.swt.widgets.Composite;

public class OrmEclipseLinkMappedSuperclass2_1Composite
	extends AbstractOrmEclipseLinkMappedSuperclassComposite {

	public OrmEclipseLinkMappedSuperclass2_1Composite(
			PropertyValueModel<? extends OrmEclipseLinkMappedSuperclass> subjectHolder, 
			Composite parent, WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.initializeMappedSuperclassCollapsibleSection(container);
		this.initializeCachingCollapsibleSection(container);
		this.initializeQueriesCollapsibleSection(container);
		this.initializeConvertersCollapsibleSection(container);
		this.initializeAdvancedCollapsibleSection(container);
	}
	
	@Override
	protected void initializeCachingSection(Composite container, PropertyValueModel<OrmEclipseLinkCaching> cachingHolder) {
		new OrmEclipseLinkCaching2_0Composite(this, cachingHolder, container);
	}

	protected void initializeQueriesCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
				container,
				EclipseLinkUiDetailsMessages.EclipseLinkMappedSuperclassComposite_queries);
		this.initializeQueriesSection(container, buildQueryContainerHolder());
		
	}

	protected void initializeQueriesSection(Composite container, PropertyValueModel<QueryContainer> queryContainerHolder) {
		new QueriesComposite(this, queryContainerHolder, container);
	}
	
	private PropertyValueModel<QueryContainer> buildQueryContainerHolder() {
		return new PropertyAspectAdapter<OrmMappedSuperclass, QueryContainer>(
				getSubjectHolder()) {
			@Override
			protected QueryContainer buildValue_() {
				return ((OrmEclipseLinkMappedSuperclass) this.subject).getQueryContainer();
			}
		};
	}
}
