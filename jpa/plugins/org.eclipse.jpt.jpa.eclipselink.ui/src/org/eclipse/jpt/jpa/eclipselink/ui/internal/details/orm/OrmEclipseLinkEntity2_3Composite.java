/*******************************************************************************
 *  Copyright (c) 2011 Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkMultitenancyComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;

public class OrmEclipseLinkEntity2_3Composite extends AbstractOrmEclipseLinkEntity2_xComposite
{
	public OrmEclipseLinkEntity2_3Composite(
			PropertyValueModel<? extends OrmEntity> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.initializeEntityCollapsibleSection(container);
		this.initializeCachingCollapsibleSectionPane(container);
		this.initializeQueriesCollapsibleSection(container);
		this.initializeInheritanceCollapsibleSection(container);
		this.initializeAttributeOverridesCollapsibleSection(container);
		this.initializeMultitenancyCollapsibleSectionPane(container);
		this.initializeGeneratorsCollapsibleSection(container);
		this.initializeConvertersCollapsibleSection(container);
		this.initializeSecondaryTablesCollapsibleSection(container);
		this.initializeAdvancedCollapsibleSection(container);
	}

	protected void initializeMultitenancyCollapsibleSectionPane(Composite container) {
		container = addCollapsibleSection(
				container,
				EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_multitenancy);
		initializeMultitenancySection(container, buildMultitenancyHolder());
	}

	protected void initializeMultitenancySection(Composite container, PropertyValueModel<OrmEclipseLinkMultitenancy2_3> multitenancyHolder) {
		new EclipseLinkMultitenancyComposite(this, multitenancyHolder, container);
	}

	private PropertyAspectAdapter<OrmEntity, OrmEclipseLinkMultitenancy2_3> buildMultitenancyHolder() {
		return new PropertyAspectAdapter<OrmEntity, OrmEclipseLinkMultitenancy2_3>(getSubjectHolder()) {
			@Override
			protected OrmEclipseLinkMultitenancy2_3 buildValue_() {
				return ((OrmEclipseLinkEntity) this.subject).getMultitenancy();
			}
		};
	}
}
