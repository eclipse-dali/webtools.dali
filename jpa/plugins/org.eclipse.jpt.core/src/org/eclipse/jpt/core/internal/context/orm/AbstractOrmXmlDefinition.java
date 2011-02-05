/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;

import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingDefinition;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.core.context.orm.UnsupportedOrmAttributeMappingDefinition;

/**
 * All the state in the definition should be "static"
 * (i.e. unchanging once it is initialized).
 */
public abstract class AbstractOrmXmlDefinition
	implements OrmXmlDefinition
{
	protected final OrmXmlContextNodeFactory factory;

	protected ArrayList<OrmTypeMappingDefinition> typeMappingDefinitions;

	protected ArrayList<OrmAttributeMappingDefinition> attributeMappingDefinitions;


	/**
	 * zero-argument constructor
	 */
	protected AbstractOrmXmlDefinition() {
		super();
		this.factory = this.buildContextNodeFactory();
	}


	// ********** factory **********

	protected abstract OrmXmlContextNodeFactory buildContextNodeFactory();

	public OrmXmlContextNodeFactory getContextNodeFactory() {
		return this.factory;
	}


	// ********** type mapping definitions **********

	public OrmTypeMappingDefinition getTypeMappingDefinition(String mappingKey) {
		for (OrmTypeMappingDefinition definition : this.getTypeMappingDefinitions()) {
			if (Tools.valuesAreEqual(definition.getKey(), mappingKey)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + mappingKey); //$NON-NLS-1$
	}

	/**
	 * Return a list of mapping definitions to use for types in
	 * <code>orm.xml</code> mapping files.
	 * The order is unimportant.
	 */
	protected synchronized ArrayList<OrmTypeMappingDefinition> getTypeMappingDefinitions() {
		if (this.typeMappingDefinitions == null) {
			this.typeMappingDefinitions = this.buildTypeMappingDefinitions();
		}
		return this.typeMappingDefinitions;
	}

	protected ArrayList<OrmTypeMappingDefinition> buildTypeMappingDefinitions() {
		ArrayList<OrmTypeMappingDefinition> definitions = new ArrayList<OrmTypeMappingDefinition>();
		this.addTypeMappingDefinitionsTo(definitions);
		return definitions;
	}

	protected abstract void addTypeMappingDefinitionsTo(ArrayList<OrmTypeMappingDefinition> definitions);


	// ********** attribute mapping definitions **********

	public OrmAttributeMappingDefinition getAttributeMappingDefinition(String mappingKey) {
		for (OrmAttributeMappingDefinition definition : this.getAttributeMappingDefinitions()) {
			if (Tools.valuesAreEqual(definition.getKey(), mappingKey)) {
				return definition;
			}
		}
		return UnsupportedOrmAttributeMappingDefinition.instance();
	}

	/**
	 * Return a list of mapping definitions to use for attributes in
	 * <code>orm.xml</code> mapping files.
	 * The order is unimportant.
	 */
	protected synchronized ArrayList<OrmAttributeMappingDefinition> getAttributeMappingDefinitions() {
		if (this.attributeMappingDefinitions == null) {
			this.attributeMappingDefinitions = this.buildAttributeMappingDefinitions();
		}
		return this.attributeMappingDefinitions;
	}

	protected ArrayList<OrmAttributeMappingDefinition> buildAttributeMappingDefinitions() {
		ArrayList<OrmAttributeMappingDefinition> definitions = new ArrayList<OrmAttributeMappingDefinition>();
		this.addAttributeMappingDefinitionsTo(definitions);
		return definitions;
	}

	protected abstract void addAttributeMappingDefinitionsTo(ArrayList<OrmAttributeMappingDefinition> definitions);
}
