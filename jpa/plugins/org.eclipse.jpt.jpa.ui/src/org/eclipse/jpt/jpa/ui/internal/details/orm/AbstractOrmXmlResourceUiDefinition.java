/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperIterableWrapper;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.ui.MappingResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.orm.OrmTypeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.jpa.ui.internal.AbstractResourceUiDefinition;
import org.eclipse.swt.widgets.Composite;

/**
 * All the state in the definition should be "static" (i.e. unchanging once it is initialized).
 */
public abstract class AbstractOrmXmlResourceUiDefinition
	extends AbstractResourceUiDefinition
	implements MappingResourceUiDefinition
{

	private ArrayList<OrmTypeMappingUiDefinition<? extends TypeMapping>> ormTypeMappingUiDefinitions;

	private ArrayList<OrmAttributeMappingUiDefinition<? extends AttributeMapping>> ormAttributeMappingUiDefinitions;

	private final OrmXmlUiFactory factory;


	/**
	 * zero-argument constructor
	 */
	protected AbstractOrmXmlResourceUiDefinition() {
		super();
		this.factory = this.buildOrmXmlUiFactory();
	}


	protected abstract OrmXmlUiFactory buildOrmXmlUiFactory();


	// ********** type mappings **********

	public JpaComposite buildTypeMappingComposite(String mappingKey, PropertyValueModel<TypeMapping> mappingModel, Composite parent, WidgetFactory widgetFactory) {
		OrmTypeMappingUiDefinition<TypeMapping> definition = this.getOrmTypeMappingUiDefinition(mappingKey);
		return definition.buildTypeMappingComposite(this.factory, mappingModel, parent, widgetFactory);
	}

	public MappingUiDefinition<PersistentType, ? extends TypeMapping> getTypeMappingUiDefinition(String mappingKey) {
		return this.getOrmTypeMappingUiDefinition_(mappingKey);
	}

	@SuppressWarnings("unchecked")
	protected OrmTypeMappingUiDefinition<TypeMapping> getOrmTypeMappingUiDefinition(String mappingKey) {
		return (OrmTypeMappingUiDefinition<TypeMapping>) this.getOrmTypeMappingUiDefinition_(mappingKey);
	}

	protected OrmTypeMappingUiDefinition<? extends TypeMapping> getOrmTypeMappingUiDefinition_(String mappingKey) {
		for (OrmTypeMappingUiDefinition<? extends TypeMapping> definition : this.getOrmTypeMappingUiDefinitions()) {
			if (ObjectTools.equals(definition.getKey(), mappingKey)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + mappingKey); //$NON-NLS-1$
	}

	public Iterable<MappingUiDefinition<PersistentType, ? extends TypeMapping>> getTypeMappingUiDefinitions() {
		return new SuperIterableWrapper<MappingUiDefinition<PersistentType, ? extends TypeMapping>>(this.getOrmTypeMappingUiDefinitions());
	}

	protected synchronized Iterable<OrmTypeMappingUiDefinition<? extends TypeMapping>> getOrmTypeMappingUiDefinitions() {
		if (this.ormTypeMappingUiDefinitions == null) {
			this.ormTypeMappingUiDefinitions = this.buildOrmTypeMappingUiDefinitions();
		}
		return this.ormTypeMappingUiDefinitions;
	}

	protected ArrayList<OrmTypeMappingUiDefinition<? extends TypeMapping>> buildOrmTypeMappingUiDefinitions() {
		ArrayList<OrmTypeMappingUiDefinition<? extends TypeMapping>> definitions = new ArrayList<OrmTypeMappingUiDefinition<? extends TypeMapping>>();
		this.addOrmTypeMappingUiDefinitionsTo(definitions);
		return definitions;
	}

	protected abstract void addOrmTypeMappingUiDefinitionsTo(List<OrmTypeMappingUiDefinition<? extends TypeMapping>> definitions);

	public DefaultMappingUiDefinition<PersistentType, ? extends TypeMapping> getDefaultTypeMappingUiDefinition() {
		//there is no way to choose an type in the orm.xml that doesn't have a specified mapping so we can return null here
		return null;
	}


	// ********** attribute mappings **********

	public JpaComposite buildAttributeMappingComposite(String mappingKey, PropertyValueModel<AttributeMapping> mappingModel, PropertyValueModel<Boolean> enabledModel, Composite parent, WidgetFactory widgetFactory) {
		OrmAttributeMappingUiDefinition<AttributeMapping> definition = this.getOrmAttributeMappingUiDefinition(mappingKey);
		return definition.buildAttributeMappingComposite(this.factory, mappingModel, enabledModel, parent, widgetFactory);
	}

	public MappingUiDefinition<ReadOnlyPersistentAttribute, ? extends AttributeMapping> getAttributeMappingUiDefinition(String mappingKey) {
		return this.getOrmAttributeMappingUiDefinition_(mappingKey);
	}

	@SuppressWarnings("unchecked")
	protected OrmAttributeMappingUiDefinition<AttributeMapping> getOrmAttributeMappingUiDefinition(String mappingKey) {
		return (OrmAttributeMappingUiDefinition<AttributeMapping>) this.getOrmAttributeMappingUiDefinition_(mappingKey);
	}

	protected OrmAttributeMappingUiDefinition<? extends AttributeMapping> getOrmAttributeMappingUiDefinition_(String mappingKey) {
		for (OrmAttributeMappingUiDefinition<? extends AttributeMapping> definition : this.getOrmAttributeMappingUiDefinitions()) {
			if (ObjectTools.equals(definition.getKey(), mappingKey)) {
				return definition;
			}
		}
		return this.getUnsupportedOrmAttributeMappingUiDefinition();
	}

	@SuppressWarnings("unchecked")
	protected OrmAttributeMappingUiDefinition<? extends AttributeMapping> getUnsupportedOrmAttributeMappingUiDefinition() {
		return UnsupportedOrmAttributeMappingUiDefinition.instance();
	}

	public Iterable<MappingUiDefinition<ReadOnlyPersistentAttribute, ? extends AttributeMapping>> getAttributeMappingUiDefinitions() {
		return new SuperIterableWrapper<MappingUiDefinition<ReadOnlyPersistentAttribute, ? extends AttributeMapping>>(this.getOrmAttributeMappingUiDefinitions());
	}

	protected synchronized Iterable<OrmAttributeMappingUiDefinition<? extends AttributeMapping>> getOrmAttributeMappingUiDefinitions() {
		if (this.ormAttributeMappingUiDefinitions == null) {
			this.ormAttributeMappingUiDefinitions = this.buildOrmAttributeMappingUiDefinitions();
		}
		return this.ormAttributeMappingUiDefinitions;
	}

	protected ArrayList<OrmAttributeMappingUiDefinition<? extends AttributeMapping>> buildOrmAttributeMappingUiDefinitions() {
		ArrayList<OrmAttributeMappingUiDefinition<? extends AttributeMapping>> definitions = new ArrayList<OrmAttributeMappingUiDefinition<? extends AttributeMapping>>();
		this.addOrmAttributeMappingUiDefinitionsTo(definitions);
		return definitions;
	}

	protected abstract void addOrmAttributeMappingUiDefinitionsTo(List<OrmAttributeMappingUiDefinition<? extends AttributeMapping>> definitions);

	public DefaultMappingUiDefinition<ReadOnlyPersistentAttribute, ? extends AttributeMapping> getDefaultAttributeMappingUiDefinition(String mappingKey) {
		return null;
	}
}
