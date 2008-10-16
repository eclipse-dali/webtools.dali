/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.core.context.ReadOnly;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmMappedSuperclass;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.ChangeTrackingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.CustomizerComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkMappedSuperclassComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.ReadOnlyComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.mappings.details.IdClassComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmMappedSuperclassComposite
	extends EclipseLinkMappedSuperclassComposite<OrmMappedSuperclass>
{
	public EclipseLinkOrmMappedSuperclassComposite(
			PropertyValueModel<? extends OrmMappedSuperclass> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		new IdClassComposite(this, container);
		initializeCachingPane(container);
//		TODO - initializeConvertersPane(container);
		initializeAdvancedPane(container);
	}
	
	protected void initializeAdvancedPane(Composite container) {
		container = addCollapsableSection(
			container,
			EclipseLinkUiMappingsMessages.EclipseLinkTypeMappingComposite_advanced);
		new ReadOnlyComposite(this, buildReadOnlyHolder(), container);
		new CustomizerComposite(this, buildCustomizerHolder(), container);
		new ChangeTrackingComposite(this, buildChangeTrackingHolder(), container);
	}
	
	private PropertyValueModel<ReadOnly> buildReadOnlyHolder() {
		return new PropertyAspectAdapter<OrmMappedSuperclass, ReadOnly>(getSubjectHolder()) {
			@Override
			protected ReadOnly buildValue_() {
				return ((EclipseLinkOrmMappedSuperclass) this.subject).getReadOnly();
			}
		};
	}
	
	private PropertyValueModel<Customizer> buildCustomizerHolder() {
		return new PropertyAspectAdapter<OrmMappedSuperclass, Customizer>(getSubjectHolder()) {
			@Override
			protected Customizer buildValue_() {
				return ((EclipseLinkOrmMappedSuperclass) this.subject).getCustomizer();
			}
		};
	}
	
	private PropertyValueModel<ChangeTracking> buildChangeTrackingHolder() {
		return new PropertyAspectAdapter<OrmMappedSuperclass, ChangeTracking>(getSubjectHolder()) {
			@Override
			protected ChangeTracking buildValue_() {
				return ((EclipseLinkOrmMappedSuperclass) this.subject).getChangeTracking();
			}
		};
	}
}
