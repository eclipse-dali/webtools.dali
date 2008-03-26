/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.schema.generation;

import java.util.Map;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkPersistenceUnitProperties;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * EclipseLinkSchemaGeneration encapsulates EclipseLink SchemaGeneration
 * properties.
 */
public class EclipseLinkSchemaGeneration
	extends EclipseLinkPersistenceUnitProperties implements SchemaGeneration
{
	// ********** EclipseLink properties **********
	
	private OutputMode outputMode;
	private DdlGenerationType ddlGenerationType;
	private String createFileName;
	private String dropFileName;
	private String applicationLocation;

	// ********** constructors/initialization **********
	
	public EclipseLinkSchemaGeneration(PersistenceUnit parent, ListValueModel<Property> propertyListAdapter) {
		super(parent, propertyListAdapter);
	}

	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		this.outputMode = 
			this.getEnumValue(ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE, OutputMode.values());
		this.ddlGenerationType = 
			this.getEnumValue(ECLIPSELINK_DDL_GENERATION_TYPE, DdlGenerationType.values());
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
	public void propertyChanged(PropertyChangeEvent event) {
		String aspectName = event.getAspectName();
		
		if (aspectName.equals(OUTPUT_MODE_PROPERTY)) {
			this.outputModeChanged(event);
		}
		else if (aspectName.equals(DDL_GENERATION_TYPE_PROPERTY)) {
			this.ddlGenerationTypeChanged(event);
		}
		else if (aspectName.equals(CREATE_FILE_NAME_PROPERTY)) {
			this.createFileNameChanged(event);
		}
		else if (aspectName.equals(DROP_FILE_NAME_PROPERTY)) {
			this.dropFileNameChanged(event);
		}
		else if (aspectName.equals(APPLICATION_LOCATION_PROPERTY)) {
			this.applicationLocationChanged(event);
		}
		else {
			throw new IllegalArgumentException("Illegal event received - property not applicable: " + aspectName);
		}
		return;
	}

	// ********** DdlGenerationType **********
	public DdlGenerationType getDdlGenerationType() {
		return this.ddlGenerationType;
	}

	public void setDdlGenerationType(DdlGenerationType newDdlGenType) {
		DdlGenerationType old = this.ddlGenerationType;
		this.ddlGenerationType = newDdlGenType;
		this.firePropertyChanged(DDL_GENERATION_TYPE_PROPERTY, old, newDdlGenType);
		this.putEnumValue(ECLIPSELINK_DDL_GENERATION_TYPE, newDdlGenType);
	}

	private void ddlGenerationTypeChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		DdlGenerationType newValue = getEnumValueOf(stringValue, DdlGenerationType.values());
		DdlGenerationType old = this.ddlGenerationType;
		this.ddlGenerationType = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public DdlGenerationType getDefaultDdlGenerationType() {
		return DEFAULT_SCHEMA_GENERATION_DDL_GENERATION_TYPE;
	}

	// ********** OutputMode **********
	public OutputMode getOutputMode() {
		return this.outputMode;
	}

	public void setOutputMode(OutputMode newOutputMode) {
		OutputMode old = this.outputMode;
		this.outputMode = newOutputMode;
		this.firePropertyChanged(OUTPUT_MODE_PROPERTY, old, newOutputMode);
		this.putEnumValue(ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE, newOutputMode);
	}

	private void outputModeChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		OutputMode newValue = getEnumValueOf(stringValue, OutputMode.values());
		OutputMode old = this.outputMode;
		this.outputMode = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public OutputMode getDefaultOutputMode() {
		return DEFAULT_SCHEMA_GENERATION_OUTPUT_MODE;
	}

	// ********** CreateFileName **********
	public String getCreateFileName() {
		return this.createFileName;
	}

	public void setCreateFileName(String newCreateFileName) {
		String old = this.createFileName;
		this.createFileName = newCreateFileName;
		this.firePropertyChanged(CREATE_FILE_NAME_PROPERTY, old, newCreateFileName);
		this.putStringValue(ECLIPSELINK_CREATE_FILE_NAME, newCreateFileName);
	}

	private void createFileNameChanged(PropertyChangeEvent event) {
		String newValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		String old = this.createFileName;
		this.createFileName = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
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
		this.firePropertyChanged(DROP_FILE_NAME_PROPERTY, old, newDropFileName);
		this.putStringValue(ECLIPSELINK_DROP_FILE_NAME, newDropFileName);
	}

	private void dropFileNameChanged(PropertyChangeEvent event) {
		String newValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		String old = this.dropFileName;
		this.dropFileName = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
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
		this.applicationLocation = newApplicationLocation;
		this.firePropertyChanged(APPLICATION_LOCATION_PROPERTY, old, newApplicationLocation);
		this.putStringValue(ECLIPSELINK_APPLICATION_LOCATION, newApplicationLocation);
	}

	private void applicationLocationChanged(PropertyChangeEvent event) {
		String newValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		String old = this.applicationLocation;
		this.applicationLocation = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public String getDefaultApplicationLocation() {
		return DEFAULT_SCHEMA_GENERATION_APPLICATION_LOCATION;
	}
}
