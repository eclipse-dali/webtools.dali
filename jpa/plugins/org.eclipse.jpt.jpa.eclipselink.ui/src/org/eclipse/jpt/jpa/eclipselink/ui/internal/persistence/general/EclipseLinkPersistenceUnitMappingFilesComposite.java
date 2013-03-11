/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.general;

import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkGeneralProperties;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceUnitMappingFilesComposite;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkPersistenceUnitMappingFilesComposite
	extends PersistenceUnitMappingFilesComposite
{
	public EclipseLinkPersistenceUnitMappingFilesComposite(
			Pane<? extends PersistenceUnit> parent,
			Composite parentComposite) {
		super(parent, parentComposite);
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.addMappingFilesList(container);
		this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_GENERAL_TAB_EXCLUDE_ECLIPSELINK_ORM,
			this.buildExcludeEclipselinkOrmHolder(),
			this.buildExcludeEclipselinkOrmStringHolder(),
			JpaHelpContextIds.PERSISTENCE_XML_GENERAL
		);
	}
	
	protected PropertyValueModel<EclipseLinkGeneralProperties> buildGeneralPropertiesHolder() {
		return new TransformationPropertyValueModel<PersistenceUnit, EclipseLinkGeneralProperties>(getSubjectHolder()) {
			@Override
			protected EclipseLinkGeneralProperties transform_(PersistenceUnit persistenceUnit) {
				return ((EclipseLinkPersistenceUnit) persistenceUnit).getGeneralProperties();
			}
		};
	}
	
	private ModifiablePropertyValueModel<Boolean> buildExcludeEclipselinkOrmHolder() {
		return new PropertyAspectAdapter<EclipseLinkGeneralProperties, Boolean>(
			buildGeneralPropertiesHolder(),
			EclipseLinkGeneralProperties.EXCLUDE_ECLIPSELINK_ORM_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return this.subject.getExcludeEclipselinkOrm();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setExcludeEclipselinkOrm(value);
			}
		};
	}

	private PropertyValueModel<String> buildExcludeEclipselinkOrmStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultExcludeEclipselinkOrmHolder()) {
			@Override
			protected String transform(Boolean b) {
				if (b != null) {
					String defaultStringValue = b.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_GENERAL_TAB_EXCLUDE_ECLIPSELINK_ORM_WITH_DEFAULT, defaultStringValue);
				}
				return JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_GENERAL_TAB_EXCLUDE_ECLIPSELINK_ORM;
			}
		};
	}	
	
	private PropertyValueModel<Boolean> buildDefaultExcludeEclipselinkOrmHolder() {
		return new PropertyAspectAdapter<EclipseLinkGeneralProperties, Boolean>(
			buildGeneralPropertiesHolder(),
			EclipseLinkGeneralProperties.EXCLUDE_ECLIPSELINK_ORM_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getExcludeEclipselinkOrm() != null) {
					return null;
				}
				return this.subject.getDefaultExcludeEclipselinkOrm();
			}
		};
	}
}
