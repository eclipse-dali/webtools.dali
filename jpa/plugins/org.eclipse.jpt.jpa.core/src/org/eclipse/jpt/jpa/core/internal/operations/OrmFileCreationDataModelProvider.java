/*******************************************************************************
 *  Copyright (c) 2008, 2012  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.operations;

import java.util.Set;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.internal.JptCoreMessages;
import org.eclipse.jpt.jpa.core.resource.orm.AccessType;
import org.eclipse.jpt.jpa.core.resource.orm.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.JPA2_0;
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
	protected String getDefaultFileName() {
		return JptJpaCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.lastSegment();
	}
	
	@Override
	protected String getDefaultVersion() {
		if (getProject() == null) {
			return null;
		}
		JpaPlatform jpaPlatform;
		JpaProject jpaProject = getJpaProject();
		jpaPlatform = (jpaProject == null) 
				? JptJpaCorePlugin.getJpaPlatformManager().buildJpaPlatformImplementation(getProject()) 
				: jpaProject.getJpaPlatform();
		return jpaPlatform.getMostRecentSupportedResourceType(JptJpaCorePlugin.ORM_XML_CONTENT_TYPE).getVersion();
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
		if (persistence.getPersistenceUnitsSize() == 0) {
			return null;
		}
		return persistence.getPersistenceUnits().iterator().next();
	}
	
	@Override
	public boolean propertySet(String propertyName, Object propertyValue) {
		boolean ok = super.propertySet(propertyName, propertyValue);
		if (propertyName.equals(CONTAINER_PATH)) {
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
				new TransformationIterable<String, DataModelPropertyDescriptor>(new CompositeIterable<String>(null, getPersistenceUnitNames())) {
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
		if (propertyName.equals(DEFAULT_ACCESS)) {
			return accessPropertyDescriptor((String) getProperty(DEFAULT_ACCESS));
		}
		if (propertyName.equals(PERSISTENCE_UNIT)) {
			return persistenceUnitPropertyDescriptor(getStringProperty(PERSISTENCE_UNIT));
		}
		return super.getPropertyDescriptor(propertyName);
	}
	
	protected DataModelPropertyDescriptor accessPropertyDescriptor(String accessType) {
		if (accessType == null) {
			return new DataModelPropertyDescriptor(null, JptCoreMessages.NONE);
		}
		return new DataModelPropertyDescriptor(accessType);
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
		if (jpaFacetVersion.equals(JpaFacet.VERSION_1_0.getVersionString())
				&& fileVersion.equals(JPA2_0.SCHEMA_VERSION)) {
			return false;
		}
		return true;
	}
	
	protected IStatus validatePersistenceUnit() {
		boolean addToPUnit = getBooleanProperty(ADD_TO_PERSISTENCE_UNIT);
		String projectName = getProject().getName();
		String pUnitName = getStringProperty(PERSISTENCE_UNIT);
		if (addToPUnit) {
			if (StringTools.stringIsEmpty(pUnitName)) {
				return new Status(
					IStatus.ERROR, JptJpaCorePlugin.PLUGIN_ID,
					NLS.bind(JptCoreMessages.VALIDATE_PERSISTENCE_UNIT_DOES_NOT_SPECIFIED, pUnitName));
			}
			if (getPersistenceUnit() == null) {
				return new Status(
					IStatus.ERROR, JptJpaCorePlugin.PLUGIN_ID,
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
			for (PersistenceUnit next : persistence.getPersistenceUnits()) {
				if (pUnitName.equals(next.getName())) {
					return next;
				}
			}
		}
		return null;
	}
	
	protected Iterable<PersistenceUnit> getPersistenceUnits() {
		//only get the persistence units for the selected JpaProject, 
		//if no jpa project is selected, then no persistence units will be listed in the combo
		JpaProject jpaProject = getJpaProject();
		PersistenceXml persistenceXml = (jpaProject == null) ? null : jpaProject.getRootContextNode().getPersistenceXml();
		Persistence persistence = (persistenceXml == null) ? null : persistenceXml.getPersistence();
		return (persistence == null) ? EmptyIterable.<PersistenceUnit>instance() : persistence.getPersistenceUnits();
	}
	
	protected Iterable<String> getPersistenceUnitNames() {
		return new TransformationIterable<PersistenceUnit, String>(getPersistenceUnits()) {
			@Override
			protected String transform(PersistenceUnit next) {
				return next.getName();
			}
		};
	}
}
