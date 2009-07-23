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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmMappedSuperclass;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkMappedSuperclassAdvancedComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.IdClassComposite;
import org.eclipse.jpt.ui.internal.orm.details.MetadataCompleteComposite;
import org.eclipse.jpt.ui.internal.orm.details.OrmJavaClassChooser;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmMappedSuperclassComposite extends FormPane<EclipseLinkOrmMappedSuperclass> implements JpaComposite
{
	public EclipseLinkOrmMappedSuperclassComposite(
			PropertyValueModel<? extends EclipseLinkOrmMappedSuperclass> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		initializeGeneralPane(container);
		initializeCachingPane(container);
		initializeConvertersPane(container);
		initializeAdvancedPane(container);
	}
	
	protected void initializeGeneralPane(Composite container) {
		new OrmJavaClassChooser(this, getSubjectHolder(), container);
		new AccessTypeComposite(this, buildAccessHolder(), container);
		new IdClassComposite(this, container);
		new MetadataCompleteComposite(this, getSubjectHolder(), container);
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolder() {
		return new PropertyAspectAdapter<EclipseLinkOrmMappedSuperclass, AccessHolder>(
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

		new OrmCachingComposite(this, buildCachingHolder(), container);
	}

	private PropertyAspectAdapter<EclipseLinkOrmMappedSuperclass, EclipseLinkCaching> buildCachingHolder() {
		return new PropertyAspectAdapter<EclipseLinkOrmMappedSuperclass, EclipseLinkCaching>(
			getSubjectHolder())
		{
			@Override
			protected EclipseLinkCaching buildValue_() {
				return this.subject.getCaching();
			}
		};
	}

	protected void initializeConvertersPane(Composite container) {

		container = addCollapsableSection(
			container,
			EclipseLinkUiMappingsMessages.ConvertersComposite_Label
		);

		new ConvertersComposite(this, buildConverterHolder(), container);
	}
	
	private PropertyValueModel<EclipseLinkConverterHolder> buildConverterHolder() {
		return new PropertyAspectAdapter<EclipseLinkOrmMappedSuperclass, EclipseLinkConverterHolder>(getSubjectHolder()) {
			@Override
			protected EclipseLinkConverterHolder buildValue_() {
				return this.subject.getConverterHolder();
			}
		};
	}
	
	protected void initializeAdvancedPane(Composite container) {
		new EclipseLinkMappedSuperclassAdvancedComposite(this, container);
	}
}
