/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import java.util.Set;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.wst.common.frameworks.datamodel.DataModelPropertyDescriptor;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class JpaFacetInstallDataModelProvider
	extends JpaFacetDataModelProvider
	implements JpaFacetInstallDataModelProperties
{
	/**
	 * required default constructor
	 */
	public JpaFacetInstallDataModelProvider() {
		super();
	}
	
	@Override
	public Set<String> getPropertyNames() {
		Set<String> propertyNames = super.getPropertyNames();
		propertyNames.add(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH);
		propertyNames.add(DB_DRIVER_NAME);
		propertyNames.add(CREATE_ORM_XML);
		return propertyNames;
	}
	
	
	// ********** properties **********
	
	protected boolean userWantsToAddDbDriverJarsToClasspath() {
		return this.getBooleanProperty(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH);
	}
	
	protected String getDriverName() {
		return (String) this.getProperty(DB_DRIVER_NAME);
	}
	
	
	// ********** enabled **********
	
	@Override
	public boolean isPropertyEnabled(String propertyName) {
		if (propertyName.equals(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH)) {
			return (this.getConnectionProfile() != null);
		}
		if (propertyName.equals(DB_DRIVER_NAME)) {
			return this.userWantsToAddDbDriverJarsToClasspath();
		}
		
		return super.isPropertyEnabled(propertyName);
	}
	
	
	// ********** defaults **********
	
	@Override
	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH)) {
			return Boolean.FALSE;
		}
		if (propertyName.equals(DB_DRIVER_NAME)) {
			return this.getDefaultDriverName();
		}
		if (propertyName.equals(CREATE_ORM_XML)) {
			return Boolean.FALSE;
		}
		
		return super.getDefaultProperty(propertyName);
	}
	
	@Override
	protected String getDefaultPlatformId() {
		return JptCorePlugin.getDefaultJpaPlatform(getProjectFacetVersion()).getId();
	}
	
	@Override
	protected String getDefaultConnection() {
		return null;
	}
	
	@Override
	protected Boolean getDefaultUserWantsToOverrideDefaultCatalog() {
		return Boolean.FALSE;
	}
	
	@Override
	protected String getDefaultCatalogIdentifier() {
		Database db = this.getDatabase();
		return (db == null) ? null : db.getDefaultCatalogIdentifier();
	}
	
	@Override
	protected Boolean getDefaultUserWantsToOverrideDefaultSchema() {
		return Boolean.FALSE;
	}
	
	@Override
	protected String getDefaultSchemaIdentifier()  {
		SchemaContainer sc = this.getSchemaContainer();
		return (sc == null) ? null : sc.getDefaultSchemaIdentifier();
	}
	
	protected String getDefaultDriverName() {
		ConnectionProfile cp = this.getConnectionProfile();
		return (cp == null) ? null : cp.getDriverName();
	}
	
	@Override
	protected Boolean getDefaultDiscoverAnnotatedClasses() {
		return Boolean.valueOf(this.runtimeSupportsEjb30());
	}
	
	
	// ********** synchronize data model **********
	
	/**
	 * The specified property's value has changed to the specified value.
	 * Return whether to fire a VALUE_CHG DataModelEvent.
	 */
	@Override
	public boolean propertySet(String propertyName, Object propertyValue) {
		boolean ok = super.propertySet(propertyName, propertyValue);
		
		if (propertyName.equals(CONNECTION)) {
			// db driver
			if (propertyValue == null) {  // connection set to '<None>'
				this.setBooleanProperty(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH, false);
			}
			this.model.notifyPropertyChange(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH, IDataModel.ENABLE_CHG);
			this.model.notifyPropertyChange(DB_DRIVER_NAME, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(DB_DRIVER_NAME, IDataModel.VALID_VALUES_CHG);
		}
		else if (propertyName.equals(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH)) {
			this.model.notifyPropertyChange(DB_DRIVER_NAME, IDataModel.ENABLE_CHG);
			if (this.propertyValueIsFalse(propertyValue)) {
				this.setProperty(DB_DRIVER_NAME, null);
			}
		}
		
		return ok;
	}
	
	
	// ********** property descriptors **********

	@Override
	public DataModelPropertyDescriptor[] getValidPropertyDescriptors(String propertyName) {
		if (propertyName.equals(PLATFORM_ID)) {
			return this.buildValidPlatformDescriptors();
		}

		if (propertyName.equals(CONNECTION)) {
			return this.buildValidConnectionDescriptors();
		}

		if (propertyName.equals(DB_DRIVER_NAME)) {
			return this.buildValidDriverDescriptors();
		}

		if (propertyName.equals(USER_OVERRIDE_DEFAULT_CATALOG)) {
			return this.buildValidCatalogDescriptors();
		}

		if (propertyName.equals(USER_OVERRIDE_DEFAULT_SCHEMA)) {
			return this.buildValidSchemaDescriptors();
		}

		return super.getValidPropertyDescriptors(propertyName);
	}
	
	protected DataModelPropertyDescriptor[] buildValidDriverDescriptors() {
		return new DataModelPropertyDescriptor[] { new DataModelPropertyDescriptor(this.getDriverName()) };
	}
	
	
	// ********** validation **********
	
	@Override
	public IStatus validate(String propertyName) {
		if (propertyName.equals(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH)
				|| propertyName.equals(DB_DRIVER_NAME)) {
			return this.validateDbDriverName();
		}
		
		return super.validate(propertyName);
	}
	
	protected IStatus validateDbDriverName() {
		return OK_STATUS;
	}
}
