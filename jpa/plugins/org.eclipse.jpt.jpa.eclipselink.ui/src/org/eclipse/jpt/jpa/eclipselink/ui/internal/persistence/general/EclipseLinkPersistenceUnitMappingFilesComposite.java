/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.general;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkGeneralProperties;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.BooleanStringTransformer;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceUnitMappingFilesComposite;
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
			this.buildExcludeEclipselinkOrmModel(),
			this.buildExcludeEclipselinkOrmStringModel(),
			JpaHelpContextIds.PERSISTENCE_XML_GENERAL
		);
	}
	
	protected PropertyValueModel<EclipseLinkGeneralProperties> buildGeneralPropertiesModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), EclipseLinkPersistenceUnit.GENERAL_PROPERTIES_TRANSFORMER);
	}
	
	private ModifiablePropertyValueModel<Boolean> buildExcludeEclipselinkOrmModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkGeneralProperties, Boolean>(
			buildGeneralPropertiesModel(),
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

	private PropertyValueModel<String> buildExcludeEclipselinkOrmStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultExcludeEclipselinkOrmModel(), EXCLUDE_ECLIPSELINK_ORM_TRANSFORMER);
	}	

	private static final Transformer<Boolean, String> EXCLUDE_ECLIPSELINK_ORM_TRANSFORMER = new BooleanStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_GENERAL_TAB_EXCLUDE_ECLIPSELINK_ORM_WITH_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_GENERAL_TAB_EXCLUDE_ECLIPSELINK_ORM
		);

	private PropertyValueModel<Boolean> buildDefaultExcludeEclipselinkOrmModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkGeneralProperties, Boolean>(
			buildGeneralPropertiesModel(),
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
