/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence;

import java.util.Map;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkDdlGenerationType;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkOutputMode;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkSchemaGeneration;

public class EclipseLinkSchemaGenerationImpl
	extends EclipseLinkPersistenceUnitProperties
	implements EclipseLinkSchemaGeneration
{
	// ********** EclipseLink properties **********
	
	private EclipseLinkOutputMode outputMode;
	private EclipseLinkDdlGenerationType ddlGenerationType;
	private String createFileName;
	private String dropFileName;
	private String applicationLocation;

	// ********** constructors/initialization **********
	
	public EclipseLinkSchemaGenerationImpl(PersistenceUnit parent) {
		super(parent);
	}

	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		this.outputMode = 
			this.getEnumValue(ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE, EclipseLinkOutputMode.values());
		this.ddlGenerationType = 
			this.getEnumValue(ECLIPSELINK_DDL_GENERATION_TYPE, EclipseLinkDdlGenerationType.values());
		this.createFileName = 
			this.getStringValue(ECLIPSELINK_CREATE_FILE_NAME);
		this.dropFileName = 
			this.getStringValue(ECLIPSELINK_DROP_FILE_NAME);
		this.applicationLocation = 
			this.getStringValue(ECLIPSELINK_APPLICATION_LOCATION);
	}

	/**
	 * Adds property names key/value pairs, where: key = EclipseLink property
	 * key; value = property id
	 */
	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
		propertyNames.put(
			ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE,
			OUTPUT_MODE_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_DDL_GENERATION_TYPE,
			DDL_GENERATION_TYPE_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_CREATE_FILE_NAME,
			CREATE_FILE_NAME_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_DROP_FILE_NAME,
			DROP_FILE_NAME_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_APPLICATION_LOCATION,
			APPLICATION_LOCATION_PROPERTY);
	}

	// ********** behavior **********
	
	public void propertyValueChanged(String propertyName, String newValue) {
		if (propertyName.equals(ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE)) {
			this.outputModeChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_DDL_GENERATION_TYPE)) {
			this.ddlGenerationTypeChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_CREATE_FILE_NAME)) {
			this.createFileNameChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_DROP_FILE_NAME)) {
			this.dropFileNameChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_APPLICATION_LOCATION)) {
			this.applicationLocationChanged(newValue);
		}
	}
	
	public void propertyRemoved(String propertyName) {
		if (propertyName.equals(ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE)) {
			this.outputModeChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_DDL_GENERATION_TYPE)) {
			this.ddlGenerationTypeChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_CREATE_FILE_NAME)) {
			this.createFileNameChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_DROP_FILE_NAME)) {
			this.dropFileNameChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_APPLICATION_LOCATION)) {
			this.applicationLocationChanged(null);
		}
	}

	// ********** DdlGenerationType **********
	public EclipseLinkDdlGenerationType getDdlGenerationType() {
		return this.ddlGenerationType;
	}

	public void setDdlGenerationType(EclipseLinkDdlGenerationType newDdlGenType) {
		EclipseLinkDdlGenerationType old = this.ddlGenerationType;
		this.ddlGenerationType = newDdlGenType;
		this.putProperty(DDL_GENERATION_TYPE_PROPERTY, newDdlGenType);
		this.firePropertyChanged(DDL_GENERATION_TYPE_PROPERTY, old, newDdlGenType);
	}

	private void ddlGenerationTypeChanged(String stringValue) {
		EclipseLinkDdlGenerationType newValue = getEnumValueOf(stringValue, EclipseLinkDdlGenerationType.values());
		EclipseLinkDdlGenerationType old = this.ddlGenerationType;
		this.ddlGenerationType = newValue;
		this.firePropertyChanged(DDL_GENERATION_TYPE_PROPERTY, old, newValue);
	}

	public EclipseLinkDdlGenerationType getDefaultDdlGenerationType() {
		return DEFAULT_SCHEMA_GENERATION_DDL_GENERATION_TYPE;
	}

	// ********** OutputMode **********
	public EclipseLinkOutputMode getOutputMode() {
		return this.outputMode;
	}

	public void setOutputMode(EclipseLinkOutputMode newOutputMode) {
		EclipseLinkOutputMode old = this.outputMode;
		this.outputMode = newOutputMode;
		this.putProperty(OUTPUT_MODE_PROPERTY, newOutputMode);
		this.firePropertyChanged(OUTPUT_MODE_PROPERTY, old, newOutputMode);
	}

	private void outputModeChanged(String stringValue) {
		EclipseLinkOutputMode newValue = getEnumValueOf(stringValue, EclipseLinkOutputMode.values());
		EclipseLinkOutputMode old = this.outputMode;
		this.outputMode = newValue;
		this.firePropertyChanged(OUTPUT_MODE_PROPERTY, old, newValue);
	}

	public EclipseLinkOutputMode getDefaultOutputMode() {
		return DEFAULT_SCHEMA_GENERATION_OUTPUT_MODE;
	}

	// ********** CreateFileName **********
	public String getCreateFileName() {
		return this.createFileName;
	}

	public void setCreateFileName(String newCreateFileName) {
		String old = this.createFileName;
		this.createFileName = newCreateFileName;
		this.putProperty(CREATE_FILE_NAME_PROPERTY, newCreateFileName);
		this.firePropertyChanged(CREATE_FILE_NAME_PROPERTY, old, newCreateFileName);
	}

	private void createFileNameChanged(String newValue) {
		String old = this.createFileName;
		this.createFileName = newValue;
		this.firePropertyChanged(CREATE_FILE_NAME_PROPERTY, old, newValue);
	}

	public String getDefaultCreateFileName() {
		return DEFAULT_SCHEMA_GENERATION_CREATE_FILE_NAME;
	}

	// ********** dropFileName **********
	public String getDropFileName() {
		return this.dropFileName;
	}

	public void setDropFileName(String newDropFileName) {
		String old = this.dropFileName;
		this.dropFileName = newDropFileName;
		this.putProperty(DROP_FILE_NAME_PROPERTY, newDropFileName);
		this.firePropertyChanged(DROP_FILE_NAME_PROPERTY, old, newDropFileName);
	}

	private void dropFileNameChanged(String newValue) {
		String old = this.dropFileName;
		this.dropFileName = newValue;
		this.firePropertyChanged(DROP_FILE_NAME_PROPERTY, old, newValue);
	}

	public String getDefaultDropFileName() {
		return DEFAULT_SCHEMA_GENERATION_DROP_FILE_NAME;
	}

	// ********** applicationLocation **********
	public String getApplicationLocation() {
		return this.applicationLocation;
	}

	public void setApplicationLocation(String newApplicationLocation) {
		String old = this.applicationLocation;
		this.applicationLocation = (StringTools.isNotBlank(newApplicationLocation)) ? 
												newApplicationLocation : 
												this.getDefaultApplicationLocation();
		this.putProperty(APPLICATION_LOCATION_PROPERTY, this.applicationLocation);
		this.firePropertyChanged(APPLICATION_LOCATION_PROPERTY, old, this.applicationLocation);
	}

	private void applicationLocationChanged(String newValue) {
		String old = this.applicationLocation;
		this.applicationLocation = newValue;
		this.firePropertyChanged(APPLICATION_LOCATION_PROPERTY, old, newValue);
	}

	public String getDefaultApplicationLocation() {
		return DEFAULT_SCHEMA_GENERATION_APPLICATION_LOCATION;
	}
}
