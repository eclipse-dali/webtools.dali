/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.context.persistence.schemagen;

import java.util.Map;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnitProperties;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.SchemaGenerationAction2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.SchemaGenerationTarget;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.schemagen.SchemaGeneration2_1;

/**
 * <em>null</em> schema generation
 */
public class NullGenericSchemaGeneration2_1
	extends AbstractPersistenceUnitProperties
	implements SchemaGeneration2_1
{
	// ********** constructors **********
	public NullGenericSchemaGeneration2_1(PersistenceUnit parent) {
		super(parent);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		//do nothing
	}

	// ********** behavior **********
	
	public void propertyValueChanged(String propertyName, String newValue) {
		//do nothing
	}

	public void propertyRemoved(String propertyName) {
		//do nothing
	}

	/**
	 * Adds property names key/value pairs, where: 
	 * 		key = PU property key
	 * 		value = property id
	 */
	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
		//do nothing
	}


	// ********** SchemaGenDatabaseAction **********
	
	public SchemaGenerationAction2_1 getSchemaGenDatabaseAction() {
		return null;
	}
	
	public void setSchemaGenDatabaseAction(SchemaGenerationAction2_1 newSchemaGenDatabaseAction) {
		throw new UnsupportedOperationException();
	}

	public SchemaGenerationAction2_1 getDefaultSchemaGenDatabaseAction() {
		return DEFAULT_SCHEMAGEN_DATABASE_ACTION;
	}

	// ********** SchemaGenScriptsAction **********
	
	public SchemaGenerationAction2_1 getSchemaGenScriptsAction() {
		return null;
	}
	
	public void setSchemaGenScriptsAction(SchemaGenerationAction2_1 newSchemaGenScriptsAction) {
		throw new UnsupportedOperationException();
	}

	public SchemaGenerationAction2_1 getDefaultSchemaGenScriptsAction() {
		return DEFAULT_SCHEMAGEN_SCRIPTS_ACTION;
	}

	// ********** SchemaGenCreateSource **********
	
	public SchemaGenerationTarget getSchemaGenCreateSource() {
		return null;
	}
	
	public void setSchemaGenCreateSource(SchemaGenerationTarget newSchemaGenCreateSource) {
		throw new UnsupportedOperationException();
	}

	public SchemaGenerationTarget getDefaultSchemaGenCreateSource() {
		return DEFAULT_SCHEMAGEN_CREATE_SOURCE;
	}

	// ********** SchemaGenDropSource **********
	
	public SchemaGenerationTarget getSchemaGenDropSource() {
		return null;
	}
	
	public void setSchemaGenDropSource(SchemaGenerationTarget newSchemaGenDropSource) {
		throw new UnsupportedOperationException();
	}

	public SchemaGenerationTarget getDefaultSchemaGenDropSource() {
		return DEFAULT_SCHEMAGEN_DROP_SOURCE;
	}

	// ********** CreateDatabaseSchemas **********
	public Boolean getCreateDatabaseSchemas() {
		return null;
	}

	public void setCreateDatabaseSchemas(Boolean newCreateDatabaseSchemas) {
		throw new UnsupportedOperationException();
	}
	
	public Boolean getDefaultCreateDatabaseSchemas() {
		return DEFAULT_CREATE_DATABASE_SCHEMAS;
	}

	// ********** ScriptsCreateTarget **********
	public String getScriptsCreateTarget() {
		return null;
	}

	public void setScriptsCreateTarget(String newScriptsCreateTarget) {
		throw new UnsupportedOperationException();
	}


	public String getDefaultScriptsCreateTarget() {
		return DEFAULT_SCRIPTS_CREATE_TARGET;
	}

	// ********** ScriptsDropTarget **********
	public String getScriptsDropTarget() {
		return null;
	}

	public void setScriptsDropTarget(String newScriptsDropTarget) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultScriptsDropTarget() {
		return DEFAULT_SCRIPTS_DROP_TARGET;
	}

	// ********** DatabaseProductName **********
	public String getDatabaseProductName() {
		return null;
	}

	public void setDatabaseProductName(String newDatabaseProductName) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultDatabaseProductName() {
		return DEFAULT_DATABASE_PRODUCT_NAME;
	}

	// ********** DatabaseMajorVersion **********
	public String getDatabaseMajorVersion() {
		return null;
	}

	public void setDatabaseMajorVersion(String newDatabaseMajorVersion) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultDatabaseMajorVersion() {
		return DEFAULT_DATABASE_MAJOR_VERSION;
	}

	// ********** DatabaseMinorVersion **********
	public String getDatabaseMinorVersion() {
		return null;
	}

	public void setDatabaseMinorVersion(String newDatabaseMinorVersion) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultDatabaseMinorVersion() {
		return DEFAULT_DATABASE_MINOR_VERSION;
	}

	// ********** CreateScriptSource **********
	public String getCreateScriptSource() {
		return null;
	}

	public void setCreateScriptSource(String newCreateScriptSource) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultCreateScriptSource() {
		return DEFAULT_CREATE_SCRIPT_SOURCE;
	}

	// ********** DropScriptSource **********
	public String getDropScriptSource() {
		return null;
	}

	public void setDropScriptSource(String newDropScriptSource) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultDropScriptSource() {
		return DEFAULT_DROP_SCRIPT_SOURCE;
	}

	// ********** Connection **********
	public String getConnection() {
		return null;
	}

	public void setConnection(String newConnection) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultConnection() {
		return DEFAULT_CONNECTION;
	}

	// ********** SqlLoadScriptSource **********
	public String getSqlLoadScriptSource() {
		return null;
	}

	public void setSqlLoadScriptSource(String newSqlLoadScriptSource) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultSqlLoadScriptSource() {
		return DEFAULT_SQL_LOAD_SCRIPT_SOURCE;
	}
}
