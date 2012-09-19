/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.java;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.jface.SimpleItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.SuperIterableWrapper;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaSourceFileDefinition;
import org.eclipse.jpt.jpa.ui.MappingResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.DefaultJavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.DefaultJavaTypeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaTypeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.structure.JavaStructureItemContentProviderFactory;
import org.eclipse.jpt.jpa.ui.internal.structure.JavaStructureItemLabelProviderFactory;
import org.eclipse.swt.widgets.Composite;

/**
 * All the state in the definition should be "static"
 * (i.e. unchanging once it is initialized).
 */
public abstract class AbstractJavaResourceUiDefinition
	implements MappingResourceUiDefinition
{
	private final JavaUiFactory factory;

	private ArrayList<JavaTypeMappingUiDefinition<? extends TypeMapping>> specifiedTypeMappingUiDefinitions;

	private ArrayList<JavaAttributeMappingUiDefinition<? extends AttributeMapping>> specifiedAttributeMappingUiDefinitions;

	private ArrayList<DefaultJavaAttributeMappingUiDefinition<?>> defaultAttributeMappingUiDefinitions;


	/**
	 * zero-argument constructor
	 */
	protected AbstractJavaResourceUiDefinition() {
		super();
		this.factory = this.buildJavaUiFactory();
	}


	protected abstract JavaUiFactory buildJavaUiFactory();

	public boolean providesUi(JptResourceType resourceType) {
		return resourceType.equals(JavaSourceFileDefinition.instance().getResourceType());
	}

	public ItemTreeStateProviderFactoryProvider getStructureViewFactoryProvider() {
		return STRUCTURE_VIEW_FACTORY_PROVIDER;
	}

	private static final ItemTreeStateProviderFactoryProvider STRUCTURE_VIEW_FACTORY_PROVIDER =
			new SimpleItemTreeStateProviderFactoryProvider(
					JavaStructureItemContentProviderFactory.instance(),
					JavaStructureItemLabelProviderFactory.instance()
				);


	// ********** type mappings **********

	public JpaComposite buildTypeMappingComposite(String mappingKey, PropertyValueModel<TypeMapping> mappingModel, Composite parent, WidgetFactory widgetFactory) {
		JavaTypeMappingUiDefinition<TypeMapping> definition = this.getJavaTypeMappingUiDefinition(mappingModel.getValue());
		return definition.buildTypeMappingComposite(this.factory, mappingModel, parent, widgetFactory);
	}

	@SuppressWarnings("unchecked")
	protected JavaTypeMappingUiDefinition<TypeMapping> getJavaTypeMappingUiDefinition(TypeMapping typeMapping) {
		return (JavaTypeMappingUiDefinition<TypeMapping>) this.getJavaTypeMappingUiDefinition_(typeMapping);
	}

	protected JavaTypeMappingUiDefinition<? extends TypeMapping> getJavaTypeMappingUiDefinition_(TypeMapping typeMapping) {
		String mappingKey = (typeMapping == null) ? null : typeMapping.getKey();
		return this.getTypeMappingUiDefinition(mappingKey);
	}

	public JavaTypeMappingUiDefinition<? extends TypeMapping> getTypeMappingUiDefinition(String mappingKey) {
		return (mappingKey == null) ?
				this.getDefaultTypeMappingUiDefinition() :
				this.getSpecifiedJavaTypeMappingUiDefinition(mappingKey);
	}

	protected JavaTypeMappingUiDefinition<? extends TypeMapping> getSpecifiedJavaTypeMappingUiDefinition(String mappingKey) {
		for (JavaTypeMappingUiDefinition<? extends TypeMapping> definition : this.getSpecifiedTypeMappingUiDefinitions()) {
			if (Tools.valuesAreEqual(definition.getKey(), mappingKey)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + mappingKey); //$NON-NLS-1$
	}

	public Iterable<MappingUiDefinition<PersistentType, ? extends TypeMapping>> getTypeMappingUiDefinitions() {
		return new SuperIterableWrapper<MappingUiDefinition<PersistentType, ? extends TypeMapping>>(this.getSpecifiedTypeMappingUiDefinitions());
	}

	protected synchronized Iterable<JavaTypeMappingUiDefinition<? extends TypeMapping>> getSpecifiedTypeMappingUiDefinitions() {
		if (this.specifiedTypeMappingUiDefinitions == null) {
			this.specifiedTypeMappingUiDefinitions = this.buildSpecifiedTypeMappingUiDefinitions();
		}
		return this.specifiedTypeMappingUiDefinitions;
	}

	protected ArrayList<JavaTypeMappingUiDefinition<? extends TypeMapping>> buildSpecifiedTypeMappingUiDefinitions() {
		ArrayList<JavaTypeMappingUiDefinition<? extends TypeMapping>> definitions = new ArrayList<JavaTypeMappingUiDefinition<? extends TypeMapping>>();
		this.addSpecifiedTypeMappingUiDefinitionsTo(definitions);
		return definitions;
	}

	protected abstract void addSpecifiedTypeMappingUiDefinitionsTo(List<JavaTypeMappingUiDefinition<? extends TypeMapping>> definitions);

	public DefaultJavaTypeMappingUiDefinition<? extends TypeMapping> getDefaultTypeMappingUiDefinition() {
		return NullJavaTypeMappingUiDefinition.instance();
	}


	// ********** attribute mappings **********

	public JpaComposite buildAttributeMappingComposite(String mappingKey, PropertyValueModel<AttributeMapping> mappingModel,PropertyValueModel<Boolean> enabledModel,  Composite parent, WidgetFactory widgetFactory) {
		JavaAttributeMappingUiDefinition<AttributeMapping> definition = this.getAttributeMappingUiDefinition(mappingModel.getValue());
		return definition.buildAttributeMappingComposite(this.factory, mappingModel, enabledModel, parent, widgetFactory);
	}

	@SuppressWarnings("unchecked")
	protected JavaAttributeMappingUiDefinition<AttributeMapping> getAttributeMappingUiDefinition(AttributeMapping attributeMapping) {
		return (JavaAttributeMappingUiDefinition<AttributeMapping>) this.getAttributeMappingUiDefinition_(attributeMapping);
	}

	protected JavaAttributeMappingUiDefinition<? extends AttributeMapping> getAttributeMappingUiDefinition_(AttributeMapping attributeMapping) {
		String mappingKey = (attributeMapping == null) ? null : attributeMapping.getKey();
		return this.getAttributeMappingUiDefinition(mappingKey);
	}

	public JavaAttributeMappingUiDefinition<? extends AttributeMapping> getAttributeMappingUiDefinition(String mappingKey) {
		return (mappingKey == null) ?
				this.getDefaultAttributeMappingUiDefinition(mappingKey) :
				this.getSpecifiedAttributeMappingUiDefinition(mappingKey);
	}

	protected JavaAttributeMappingUiDefinition<? extends AttributeMapping> getSpecifiedAttributeMappingUiDefinition(String mappingKey) {
		for (JavaAttributeMappingUiDefinition<? extends AttributeMapping> definition : this.getSpecifiedAttributeMappingUiDefinitions()) {
			if (Tools.valuesAreEqual(definition.getKey(), mappingKey)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal attribute mapping key: " + mappingKey); //$NON-NLS-1$
	}

	public Iterable<MappingUiDefinition<ReadOnlyPersistentAttribute, ? extends AttributeMapping>> getAttributeMappingUiDefinitions() {
		return new SuperIterableWrapper<MappingUiDefinition<ReadOnlyPersistentAttribute, ? extends AttributeMapping>>(this.getSpecifiedAttributeMappingUiDefinitions());
	}

	protected synchronized ArrayList<JavaAttributeMappingUiDefinition<? extends AttributeMapping>> getSpecifiedAttributeMappingUiDefinitions() {
		if (this.specifiedAttributeMappingUiDefinitions == null) {
			this.specifiedAttributeMappingUiDefinitions = this.buildSpecifiedAttributeMappingUiDefinitions();
		}
		return this.specifiedAttributeMappingUiDefinitions;
	}

	protected ArrayList<JavaAttributeMappingUiDefinition<? extends AttributeMapping>> buildSpecifiedAttributeMappingUiDefinitions() {
		ArrayList<JavaAttributeMappingUiDefinition<? extends AttributeMapping>> definitions = new ArrayList<JavaAttributeMappingUiDefinition<? extends AttributeMapping>>();
		this.addSpecifiedAttributeMappingUiDefinitionsTo(definitions);
		return definitions;
	}

	protected abstract void addSpecifiedAttributeMappingUiDefinitionsTo(List<JavaAttributeMappingUiDefinition<? extends AttributeMapping>> definitions);


	// ********** default attribute mappings **********

	public DefaultJavaAttributeMappingUiDefinition<? extends AttributeMapping> getDefaultAttributeMappingUiDefinition(String mappingKey) {
		for (DefaultJavaAttributeMappingUiDefinition<? extends AttributeMapping> definition : this.getDefaultAttributeMappingUiDefinitions()) {
			if (Tools.valuesAreEqual(definition.getDefaultKey(), mappingKey)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal attribute mapping key: " + mappingKey); //$NON-NLS-1$
	}

	protected synchronized ArrayList<DefaultJavaAttributeMappingUiDefinition<? extends AttributeMapping>> getDefaultAttributeMappingUiDefinitions() {
		if (this.defaultAttributeMappingUiDefinitions == null) {
			this.defaultAttributeMappingUiDefinitions = this.buildDefaultAttributeMappingUiDefinitions();
		}
		return this.defaultAttributeMappingUiDefinitions;
	}

	protected ArrayList<DefaultJavaAttributeMappingUiDefinition<?>> buildDefaultAttributeMappingUiDefinitions() {
		ArrayList<DefaultJavaAttributeMappingUiDefinition<?>> definitions = new ArrayList<DefaultJavaAttributeMappingUiDefinition<?>>();
		this.addDefaultAttributeMappingUiDefinitionsTo(definitions);
		return definitions;
	}

	protected abstract void addDefaultAttributeMappingUiDefinitionsTo(List<DefaultJavaAttributeMappingUiDefinition<?>> definitions);
}
