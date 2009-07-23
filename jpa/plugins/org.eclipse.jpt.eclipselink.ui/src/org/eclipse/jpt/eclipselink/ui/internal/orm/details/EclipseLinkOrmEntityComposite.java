/*******************************************************************************
 *  Copyright (c) 2008, 2009 Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.eclipselink.core.context.Caching;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEntity;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkEntityAdvancedComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.AbstractEntityComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EntityNameComposite;
import org.eclipse.jpt.ui.internal.mappings.details.IdClassComposite;
import org.eclipse.jpt.ui.internal.mappings.details.TableComposite;
import org.eclipse.jpt.ui.internal.orm.details.MetadataCompleteComposite;
import org.eclipse.jpt.ui.internal.orm.details.OrmInheritanceComposite;
import org.eclipse.jpt.ui.internal.orm.details.OrmJavaClassChooser;
import org.eclipse.jpt.ui.internal.orm.details.OrmSecondaryTablesComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmEntityComposite extends AbstractEntityComposite<OrmEntity>
{
	public EclipseLinkOrmEntityComposite(
			PropertyValueModel<? extends OrmEntity> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
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
		
		new OrmJavaClassChooser(this, getSubjectHolder(), addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin), false);
		new TableComposite(this, container);
		new EntityNameComposite(this, addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin));
		new AccessTypeComposite(this, buildAccessHolder(), addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin));
		new IdClassComposite(this, addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin), false);
		new MetadataCompleteComposite(this, getSubjectHolder(), addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin));
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolder() {
		return new PropertyAspectAdapter<OrmEntity, AccessHolder>(
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
			EclipseLinkUiMappingsMessages.EclipseLinkTypeMappingComposite_caching);
		new OrmCachingComposite(this, buildCachingHolder(), container);
	}
	
	private PropertyAspectAdapter<OrmEntity, Caching> buildCachingHolder() {
		return new PropertyAspectAdapter<OrmEntity, Caching>(getSubjectHolder()) {
			@Override
			protected Caching buildValue_() {
				return ((EclipseLinkOrmEntity) this.subject).getCaching();
			}
		};
	}
	
	@Override
	protected void addInheritanceComposite(Composite container) {
		new OrmInheritanceComposite(this, container);
	}
	
	@Override
	protected void addSecondaryTablesComposite(Composite container) {
		new OrmSecondaryTablesComposite(this, container);
	}

	protected void initializeConvertersPane(Composite container) {

		container = addCollapsableSection(
			container,
			EclipseLinkUiMappingsMessages.ConvertersComposite_Label
		);

		new ConvertersComposite(this, buildConverterHolder(), container);
	}
	
	private PropertyValueModel<EclipseLinkConverterHolder> buildConverterHolder() {
		return new PropertyAspectAdapter<OrmEntity, EclipseLinkConverterHolder>(getSubjectHolder()) {
			@Override
			protected EclipseLinkConverterHolder buildValue_() {
				return ((EclipseLinkOrmEntity) this.subject).getConverterHolder();
			}
		};
	}
	
	protected void initializeAdvancedPane(Composite container) {
		new EclipseLinkEntityAdvancedComposite(this, container);
	}
}
