/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.ui.FileUiDefinition;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.MappingUiDefinition;
import org.eclipse.jpt.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.ui.details.orm.OrmTypeMappingUiDefinition;
import org.eclipse.jpt.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * All the state in the definition should be "static" (i.e. unchanging once it is initialized).
 */
public abstract class AbstractOrmXmlUiDefinition
	implements FileUiDefinition
{
	
	private OrmTypeMappingUiDefinition<? extends TypeMapping>[] ormTypeMappingUiDefintions;

	private OrmAttributeMappingUiDefinition<? extends AttributeMapping>[] ormAttributeMappingUiDefintions;
	
	private final OrmXmlUiFactory factory;
	
	
	/**
	 * zero-argument constructor
	 */
	protected AbstractOrmXmlUiDefinition() {
		super();
		this.factory = buildOrmXmlUiFactory();
	}
	
	
	protected abstract OrmXmlUiFactory buildOrmXmlUiFactory();
	
	public OrmXmlUiFactory getFactory() {
		return this.factory;
	}
	
	
	// ********** ORM type mappings **********
	
	public JpaComposite buildTypeMappingComposite(String key, PropertyValueModel<TypeMapping> mappingHolder, Composite parent, WidgetFactory widgetFactory) {
		OrmTypeMappingUiDefinition<TypeMapping> mappingUiDefinition = 
			(OrmTypeMappingUiDefinition<TypeMapping>) getOrmTypeMappingUiDefinition(key);
		return mappingUiDefinition.buildTypeMappingComposite(
			getFactory(), 
			mappingHolder,
			parent,
			widgetFactory
		);
	}
	
	protected OrmTypeMappingUiDefinition<? extends TypeMapping> getOrmTypeMappingUiDefinition(String mappingKey) {
		for (OrmTypeMappingUiDefinition<? extends TypeMapping> definition : getOrmTypeMappingUiDefinitions()) {
			if (Tools.valuesAreEqual(definition.getKey(), mappingKey)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + mappingKey); //$NON-NLS-1$
	}
	
	public Iterator<MappingUiDefinition<? extends TypeMapping>> typeMappingUiDefinitions() {
		return new ArrayIterator<MappingUiDefinition<? extends TypeMapping>>(getOrmTypeMappingUiDefinitions());
	}
	
	protected synchronized OrmTypeMappingUiDefinition<? extends TypeMapping>[] getOrmTypeMappingUiDefinitions() {
		if (this.ormTypeMappingUiDefintions == null) {
			this.ormTypeMappingUiDefintions = this.buildOrmTypeMappingUiDefinitions();
		}
		return this.ormTypeMappingUiDefintions;
	}
	
	
	/**
	 * Return an array of mapping definitions to use for types in mapping files of this type.  
	 * The order is unimportant.
	 */
	protected OrmTypeMappingUiDefinition<? extends TypeMapping>[] buildOrmTypeMappingUiDefinitions() {
		ArrayList<OrmTypeMappingUiDefinition<? extends TypeMapping>> definitions = new ArrayList<OrmTypeMappingUiDefinition<? extends TypeMapping>>();
		this.addOrmTypeMappingUiDefinitionsTo(definitions);
		@SuppressWarnings("unchecked")
		OrmTypeMappingUiDefinition<? extends TypeMapping>[] definitionArray = definitions.toArray(new OrmTypeMappingUiDefinition[definitions.size()]);
		return definitionArray;
	}


	protected abstract void addOrmTypeMappingUiDefinitionsTo(List<OrmTypeMappingUiDefinition<? extends TypeMapping>> definitions);
	
	public DefaultMappingUiDefinition<? extends TypeMapping> getDefaultTypeMappingUiDefinition() {
		//there is no way to choose an type in the orm.xml that doesn't have a specified mapping so we can return null here
		return null;
	}
	
	// ********** ORM attribute mappings **********
	
	public JpaComposite buildAttributeMappingComposite(String key, PropertyValueModel<AttributeMapping> mappingHolder, Composite parent, WidgetFactory widgetFactory) {

		OrmAttributeMappingUiDefinition<AttributeMapping> mappingUiDefinition = 
			(OrmAttributeMappingUiDefinition<AttributeMapping>) getOrmAttributeMappingUiDefinition(key);
		return mappingUiDefinition.buildAttributeMappingComposite(
			getFactory(), 
			mappingHolder,
			parent,
			widgetFactory
		);
	}
	
	protected OrmAttributeMappingUiDefinition<? extends AttributeMapping> getOrmAttributeMappingUiDefinition(String mappingKey) {
		for (OrmAttributeMappingUiDefinition<? extends AttributeMapping> definition : getOrmAttributeMappingUiDefinitions()) {
			if (Tools.valuesAreEqual(definition.getKey(), mappingKey)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + mappingKey); //$NON-NLS-1$
	}
	
	public ListIterator<MappingUiDefinition<? extends AttributeMapping>> attributeMappingUiDefinitions() {
		return new ArrayListIterator<MappingUiDefinition<? extends AttributeMapping>>(getOrmAttributeMappingUiDefinitions());
	}
	
	protected synchronized OrmAttributeMappingUiDefinition<? extends AttributeMapping>[] getOrmAttributeMappingUiDefinitions() {
		if (this.ormAttributeMappingUiDefintions == null) {
			this.ormAttributeMappingUiDefintions = this.buildOrmAttributeMappingUiDefinitions();
		}
		return this.ormAttributeMappingUiDefintions;
	}
	
	/**
	 * Return an array of mapping definitions to use for attributes in mapping files of this type.  
	 * The order is unimportant.
	 */
	protected OrmAttributeMappingUiDefinition<? extends AttributeMapping>[] buildOrmAttributeMappingUiDefinitions() {
		ArrayList<OrmAttributeMappingUiDefinition<? extends AttributeMapping>> definitions = new ArrayList<OrmAttributeMappingUiDefinition<? extends AttributeMapping>>();
		this.addOrmAttributeMappingUiDefinitionsTo(definitions);
		@SuppressWarnings("unchecked")
		OrmAttributeMappingUiDefinition<? extends AttributeMapping>[] definitionArray = definitions.toArray(new OrmAttributeMappingUiDefinition[definitions.size()]);
		return definitionArray;
	}


	protected abstract void addOrmAttributeMappingUiDefinitionsTo(List<OrmAttributeMappingUiDefinition<? extends AttributeMapping>> definitions);

	public DefaultMappingUiDefinition<? extends AttributeMapping> getDefaultAttributeMappingUiDefinition(String key) {
		return null;
	}
}
