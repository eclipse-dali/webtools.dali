/*******************************************************************************
 *  Copyright (c) 2008, 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.operations;

import java.util.Iterator;
import java.util.Set;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.JPA;
import org.eclipse.jpt.core.resource.orm.v2_0.JPA2_0;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.frameworks.datamodel.DataModelPropertyDescriptor;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelOperation;

public class OrmFileCreationDataModelProvider
	extends AbstractJpaFileCreationDataModelProvider
	implements OrmFileCreationDataModelProperties
{
	/**
	 * required default constructor
	 */
	public OrmFileCreationDataModelProvider() {
		super();
	}
	
	
	@Override
	public IDataModelOperation getDefaultOperation() {
		return new OrmFileCreationOperation(getDataModel());
	}
	
	@Override
	public Set<String> getPropertyNames() {
		@SuppressWarnings("unchecked")
		Set<String> propertyNames = super.getPropertyNames();
		propertyNames.add(DEFAULT_ACCESS);
		propertyNames.add(ADD_TO_PERSISTENCE_UNIT);
		propertyNames.add(PERSISTENCE_UNIT);
		return propertyNames;
	}
	
	@Override
	public boolean isPropertyEnabled(String propertyName) {
		if (propertyName.equals(PERSISTENCE_UNIT)) {
			return getBooleanProperty(ADD_TO_PERSISTENCE_UNIT);
		}
		return super.isPropertyEnabled(propertyName);
	}
	
	@Override
	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(DEFAULT_ACCESS)) {
			return null;
		}
		else if (propertyName.equals(ADD_TO_PERSISTENCE_UNIT)) {
			return Boolean.FALSE;
		}
		else if (propertyName.equals(PERSISTENCE_UNIT)) {
			PersistenceUnit pUnit = getDefaultPersistenceUnit();
			if (pUnit != null) {
				return pUnit.getName();
			}
		}
		return super.getDefaultProperty(propertyName);
	}
	
	@Override
	protected String getDefaultFilePath() {
		return new Path(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH).toPortableString();
	}
	
	@Override
	protected String getDefaultVersion() {
		if (getProject() == null) {
			return null;
		}
		return getJpaProject().getJpaPlatform().getMostRecentSupportedResourceType(
				JptCorePlugin.ORM_XML_CONTENT_TYPE).getVersion();
	}
	
	protected PersistenceUnit getDefaultPersistenceUnit() {
		JpaProject jpaProject = getJpaProject();
		if (jpaProject == null) {
			return null;
		}
		PersistenceXml persistenceXml = jpaProject.getRootContextNode().getPersistenceXml();
		if (persistenceXml == null) {
			return null;
		}
		Persistence persistence = persistenceXml.getPersistence();
		if (persistence == null) {
			return null;
		}
		if (persistence.persistenceUnitsSize() == 0) {
			return null;
		}
		return persistence.persistenceUnits().next();
	}
	
	@Override
	public boolean propertySet(String propertyName, Object propertyValue) {
		boolean ok = super.propertySet(propertyName, propertyValue);
		if (propertyName.equals(PROJECT_NAME)) {
			this.model.notifyPropertyChange(PERSISTENCE_UNIT, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(PERSISTENCE_UNIT, IDataModel.VALID_VALUES_CHG);
		}
		else if (propertyName.equals(ADD_TO_PERSISTENCE_UNIT)) {
			this.model.notifyPropertyChange(PERSISTENCE_UNIT, IDataModel.ENABLE_CHG);
		}
		return ok;
	}
	
	@Override
	public DataModelPropertyDescriptor[] getValidPropertyDescriptors(String propertyName) {
		if (propertyName.equals(DEFAULT_ACCESS)) {
			DataModelPropertyDescriptor[] accessTypes = new DataModelPropertyDescriptor[3];
			accessTypes[0] = accessPropertyDescriptor(null);
			accessTypes[1] = accessPropertyDescriptor(AccessType.FIELD);
			accessTypes[2] = accessPropertyDescriptor(AccessType.PROPERTY);
			return accessTypes;
		}
		else if (propertyName.equals(PERSISTENCE_UNIT)) {
			return ArrayTools.array(
				new TransformationIterator<String, DataModelPropertyDescriptor>(new CompositeIterator<String>(null, persistenceUnitNames())) {
					@Override
					protected DataModelPropertyDescriptor transform(String next) {
						return persistenceUnitPropertyDescriptor(next);
					}
				},
				new DataModelPropertyDescriptor[0]);
		}
		return super.getValidPropertyDescriptors(propertyName);
	}
	
	@Override
	public DataModelPropertyDescriptor getPropertyDescriptor(String propertyName) {
		if (propertyName.equals(PERSISTENCE_UNIT)) {
			return persistenceUnitPropertyDescriptor(getStringProperty(PERSISTENCE_UNIT));
		}
		return super.getPropertyDescriptor(propertyName);
	}
	
	protected DataModelPropertyDescriptor accessPropertyDescriptor(AccessType accessType) {
		if (accessType == null) {
			return new DataModelPropertyDescriptor(null, JptCoreMessages.NONE);
		}
		return new DataModelPropertyDescriptor(accessType, accessType.getName());
	}
	
	DataModelPropertyDescriptor persistenceUnitPropertyDescriptor(String persistenceUnitName) {
		if (StringTools.stringIsEmpty(persistenceUnitName)) {
			return new DataModelPropertyDescriptor(null, JptCoreMessages.NONE);
		}
		return new DataModelPropertyDescriptor(persistenceUnitName);
	}
	
	
	// **************** validation *********************************************
	
	@Override
	public IStatus validate(String propertyName) {
		IStatus status = super.validate(propertyName);
		if (! status.isOK()) {
			return status;
		}
		
		if (propertyName.equals(ADD_TO_PERSISTENCE_UNIT)
				|| propertyName.equals(PERSISTENCE_UNIT)) {
			status = validatePersistenceUnit();
		}
		if (! status.isOK()) {
			return status;
		}
		
		return Status.OK_STATUS;
	}
	
	@Override
	protected boolean fileVersionSupported(String fileVersion) {
		return (fileVersion.equals(JPA.SCHEMA_VERSION)
				|| fileVersion.equals(JPA2_0.SCHEMA_VERSION));
	}
	
	@Override
	protected boolean fileVersionSupportedForFacetVersion(String fileVersion, String jpaFacetVersion) {
		if (jpaFacetVersion.equals(JptCorePlugin.JPA_FACET_VERSION_1_0)
				&& fileVersion.equals(JPA2_0.SCHEMA_VERSION)) {
			return false;
		}
		return true;
	}
	
	protected IStatus validatePersistenceUnit() {
		boolean addToPUnit = getBooleanProperty(ADD_TO_PERSISTENCE_UNIT);
		String projectName = getStringProperty(PROJECT_NAME);
		String pUnitName = getStringProperty(PERSISTENCE_UNIT);
		if (addToPUnit) {
			if (StringTools.stringIsEmpty(pUnitName)) {
				return new Status(
					IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
					NLS.bind(JptCoreMessages.VALIDATE_PERSISTENCE_UNIT_DOES_NOT_SPECIFIED, pUnitName));
			}
			if (getPersistenceUnit() == null) {
				return new Status(
					IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
					NLS.bind(JptCoreMessages.VALIDATE_PERSISTENCE_UNIT_NOT_IN_PROJECT, pUnitName, projectName));
			}
		}
		return Status.OK_STATUS;
	}
	
	
	// **************** helper methods *****************************************
	
	protected PersistenceUnit getPersistenceUnit() {
		String pUnitName = getStringProperty(PERSISTENCE_UNIT);
		JpaProject jpaProject = 
			(StringTools.stringIsEmpty(pUnitName)) ? null : getJpaProject();
		PersistenceXml persistenceXml = 
			(jpaProject == null) ? null : jpaProject.getRootContextNode().getPersistenceXml();
		Persistence persistence = 
			(persistenceXml == null) ? null : persistenceXml.getPersistence();
		if (persistence != null) {
			for (Iterator<PersistenceUnit> stream = persistence.persistenceUnits(); stream.hasNext(); ) {
				PersistenceUnit next = stream.next();
				if (pUnitName.equals(next.getName())) {
					return next;
				}
			}
		}
		return null;
	}
	
	protected Iterator<PersistenceUnit> persistenceUnits() {
		//only get the persistence units for the selected JpaProject, 
		//if no jpa project is selected, then no persistence units will be listed in the combo
		JpaProject jpaProject = getJpaProject();
		PersistenceXml persistenceXml = (jpaProject == null) ? null : jpaProject.getRootContextNode().getPersistenceXml();
		Persistence persistence = (persistenceXml == null) ? null : persistenceXml.getPersistence();
		return (persistence == null) ? EmptyIterator.<PersistenceUnit>instance() : persistence.persistenceUnits();
	}
	
	protected Iterator<String> persistenceUnitNames() {
		return new TransformationIterator<PersistenceUnit, String>(persistenceUnits()) {
			@Override
			protected String transform(PersistenceUnit next) {
				return next.getName();
			}
		};
	}
}
