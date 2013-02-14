/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.ArrayList;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.jpa.core.context.orm.UnsupportedOrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManagedType;

/**
 * All the state in the definition should be "static"
 * (i.e. unchanging once it is initialized).
 */
public abstract class AbstractOrmXmlDefinition
	implements OrmXmlDefinition
{
	protected final OrmXmlContextNodeFactory factory;

	protected ArrayList<OrmManagedTypeDefinition> managedTypeDefinitions;

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

	// ********** managed type definitions **********

	public OrmManagedTypeDefinition getManagedTypeDefinition(Class<? extends XmlManagedType> resourceType) {
		for (OrmManagedTypeDefinition definition : this.getManagedTypeDefinitions()) {
			if (definition.getResourceType() == resourceType) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal managed type resource type: " + resourceType); //$NON-NLS-1$
	}

	/**
	 * Return a list of mapping definitions to use for types in
	 * <code>orm.xml</code> mapping files.
	 * The order is unimportant.
	 */
	protected synchronized ArrayList<OrmManagedTypeDefinition> getManagedTypeDefinitions() {
		if (this.managedTypeDefinitions == null) {
			this.managedTypeDefinitions = this.buildManagedTypeDefinitions();
		}
		return this.managedTypeDefinitions;
	}

	protected ArrayList<OrmManagedTypeDefinition> buildManagedTypeDefinitions() {
		ArrayList<OrmManagedTypeDefinition> definitions = new ArrayList<OrmManagedTypeDefinition>();
		this.addManagedTypeDefinitionsTo(definitions);
		return definitions;
	}

	protected void addManagedTypeDefinitionsTo(ArrayList<OrmManagedTypeDefinition> definitions) {
		CollectionTools.addAll(definitions, MANAGED_TYPE_DEFINITIONS);
	}

	/**
	 * Order should not matter here; but we'll use the same order as for Java.
	 * @see org.eclipse.jpt.jpa.core.internal.GenericJpaPlatformProvider
	 */
	protected static final OrmManagedTypeDefinition[] MANAGED_TYPE_DEFINITIONS = new OrmManagedTypeDefinition[] {
		OrmPersistentTypeDefinition.instance(),
	};


	// ********** type mapping definitions **********

	public OrmTypeMappingDefinition getTypeMappingDefinition(String mappingKey) {
		for (OrmTypeMappingDefinition definition : this.getTypeMappingDefinitions()) {
			if (ObjectTools.equals(definition.getKey(), mappingKey)) {
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

	protected void addTypeMappingDefinitionsTo(ArrayList<OrmTypeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, TYPE_MAPPING_DEFINITIONS);
	}

	/**
	 * Order should not matter here; but we'll use the same order as for Java.
	 * @see org.eclipse.jpt.jpa.core.internal.GenericJpaPlatformProvider
	 */
	protected static final OrmTypeMappingDefinition[] TYPE_MAPPING_DEFINITIONS = new OrmTypeMappingDefinition[] {
		OrmEntityDefinition.instance(),
		OrmEmbeddableDefinition.instance(),
		OrmMappedSuperclassDefinition.instance()
	};


	// ********** attribute mapping definitions **********

	public OrmAttributeMappingDefinition getAttributeMappingDefinition(String mappingKey) {
		for (OrmAttributeMappingDefinition definition : this.getAttributeMappingDefinitions()) {
			if (ObjectTools.equals(definition.getKey(), mappingKey)) {
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


	// ********** misc **********

	protected JptResourceType getResourceType(IContentType contentType, String version) {
		return PlatformTools.getResourceType(contentType, version);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
