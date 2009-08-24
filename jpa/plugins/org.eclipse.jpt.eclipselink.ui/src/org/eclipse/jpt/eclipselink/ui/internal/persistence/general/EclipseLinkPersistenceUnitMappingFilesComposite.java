/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.general;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.eclipselink.core.context.persistence.general.GeneralProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.persistence.details.PersistenceUnitMappingFilesComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | AddRemoveListPane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see PersistenceUnit
 * @see PersistenceUnitGeneralComposite - The parent container
 * @see AddRemoveListPane
 *
 * @version 2.0
 * @since 2.0
 */
public class EclipseLinkPersistenceUnitMappingFilesComposite extends PersistenceUnitMappingFilesComposite
{
	/**
	 * Creates a new <code>PersistenceUnitMappingFilesComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public EclipseLinkPersistenceUnitMappingFilesComposite(Pane<? extends PersistenceUnit> parentPane,
	                                            Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.addMappingFilesList(container);
		this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlGeneralTab_excludeEclipselinkOrm,
			this.buildExcludeEclipselinkOrmHolder(),
			this.buildExcludeEclipselinkOrmStringHolder(),
			JpaHelpContextIds.PERSISTENCE_XML_GENERAL
		);
	}
	
	protected PropertyValueModel<GeneralProperties> buildGeneralPropertiesHolder() {
		return new TransformationPropertyValueModel<PersistenceUnit, GeneralProperties>(getSubjectHolder()) {
			@Override
			protected GeneralProperties transform_(PersistenceUnit persistenceUnit) {
				return ((EclipseLinkPersistenceUnit) persistenceUnit).getGeneralProperties();
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildExcludeEclipselinkOrmHolder() {
		return new PropertyAspectAdapter<GeneralProperties, Boolean>(
			buildGeneralPropertiesHolder(),
			GeneralProperties.EXCLUDE_ECLIPSELINK_ORM_PROPERTY)
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
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? EclipseLinkUiMessages.Boolean_True : EclipseLinkUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlGeneralTab_excludeEclipselinkOrmWithDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlGeneralTab_excludeEclipselinkOrm;
			}
		};
	}	
	
	private PropertyValueModel<Boolean> buildDefaultExcludeEclipselinkOrmHolder() {
		return new PropertyAspectAdapter<GeneralProperties, Boolean>(
			buildGeneralPropertiesHolder(),
			GeneralProperties.EXCLUDE_ECLIPSELINK_ORM_PROPERTY)
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