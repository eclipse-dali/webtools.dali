/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingDefinition;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;

/**
 * All the state in the definition should be "static" (i.e. unchanging once it is initialized).
 */
public abstract class AbstractOrmXmlDefinition
	implements OrmXmlDefinition
{
	private OrmTypeMappingDefinition[] ormTypeMappingDefinitions;
	
	private OrmAttributeMappingDefinition[] ormAttributeMappingDefinitions;
	
	private final OrmXmlContextNodeFactory factory;
	
	
	/**
	 * zero-argument constructor
	 */
	protected AbstractOrmXmlDefinition() {
		super();
		this.factory = buildContextNodeFactory();
	}
	
	
	protected abstract OrmXmlContextNodeFactory buildContextNodeFactory();
	
	public OrmXmlContextNodeFactory getContextNodeFactory() {
		return this.factory;
	}
	
	
	// ********** ORM type mappings **********
	
	public OrmTypeMappingDefinition getOrmTypeMappingDefinition(String mappingKey) {
		for (OrmTypeMappingDefinition definition : CollectionTools.iterable(ormTypeMappingDefinitions())) {
			if (Tools.valuesAreEqual(definition.getKey(), mappingKey)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + mappingKey); //$NON-NLS-1$
	}
	
	public ListIterator<OrmTypeMappingDefinition> ormTypeMappingDefinitions() {
		return new ArrayListIterator<OrmTypeMappingDefinition>(getOrmTypeMappingDefinitions());
	}
	
	protected synchronized OrmTypeMappingDefinition[] getOrmTypeMappingDefinitions() {
		if (this.ormTypeMappingDefinitions == null) {
			this.ormTypeMappingDefinitions = this.buildOrmTypeMappingDefinitions();
		}
		return this.ormTypeMappingDefinitions;
	}
	
	/**
	 * Return an array of mapping definitions to use for types in mapping files of this type.  
	 * The order is unimportant.
	 */
	protected abstract OrmTypeMappingDefinition[] buildOrmTypeMappingDefinitions();
	
	
	// ********** ORM attribute mappings **********
	
	public OrmAttributeMappingDefinition getOrmAttributeMappingDefinition(String mappingKey) {
		for (OrmAttributeMappingDefinition definition : CollectionTools.iterable(ormAttributeMappingDefinitions())) {
			if (Tools.valuesAreEqual(definition.getKey(), mappingKey)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + mappingKey); //$NON-NLS-1$
	}
	
	public ListIterator<OrmAttributeMappingDefinition> ormAttributeMappingDefinitions() {
		return new ArrayListIterator<OrmAttributeMappingDefinition>(getOrmAttributeMappingDefinitions());
	}
	
	protected synchronized OrmAttributeMappingDefinition[] getOrmAttributeMappingDefinitions() {
		if (this.ormAttributeMappingDefinitions == null) {
			this.ormAttributeMappingDefinitions = this.buildOrmAttributeMappingDefinitions();
		}
		return this.ormAttributeMappingDefinitions;
	}
	
	/**
	 * Return an array of mapping definitions to use for attributes in mapping files of this type.  
	 * The order is unimportant.
	 */
	protected abstract OrmAttributeMappingDefinition[] buildOrmAttributeMappingDefinitions();
}
