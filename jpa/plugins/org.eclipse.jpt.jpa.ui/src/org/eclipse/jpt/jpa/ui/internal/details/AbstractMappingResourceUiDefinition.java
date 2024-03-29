/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.ui.MappingResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JpaUiFactory;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.AbstractResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.EmbeddableUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.EntityUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.MappedSuperclassUiDefinition;
import org.eclipse.swt.widgets.Composite;

/**
 * All the state in the definition should be "static"
 * (i.e. unchanging once it is initialized).
 */
public abstract class AbstractMappingResourceUiDefinition
	extends AbstractResourceUiDefinition
	implements MappingResourceUiDefinition
{
	private final JpaUiFactory factory;

	private ArrayList<MappingUiDefinition> typeMappingUiDefinitions;

	private ArrayList<MappingUiDefinition> specifiedAttributeMappingUiDefinitions;

	/**
	 * zero-argument constructor
	 */
	protected AbstractMappingResourceUiDefinition() {
		super();
		this.factory = this.buildUiFactory();
	}

	protected abstract JpaUiFactory buildUiFactory();


	// ********** type mappings **********

	public JpaComposite buildTypeMappingComposite(String mappingKey, PropertyValueModel<TypeMapping> mappingModel, PropertyValueModel<Boolean> enabledModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		MappingUiDefinition definition = this.getTypeMappingUiDefinition(mappingModel.getValue());
		return definition.buildMappingComposite(this.factory, mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	protected MappingUiDefinition getTypeMappingUiDefinition(TypeMapping typeMapping) {
		String mappingKey = (typeMapping == null) ? null : typeMapping.getKey();
		return this.getTypeMappingUiDefinition(mappingKey);
	}

	public MappingUiDefinition getTypeMappingUiDefinition(String mappingKey) {
		return (mappingKey == null) ?
				this.getDefaultTypeMappingUiDefinition() :
				this.getTypeMappingUiDefinition_(mappingKey);
	}

	protected MappingUiDefinition getTypeMappingUiDefinition_(String mappingKey) {
		for (MappingUiDefinition definition : this.getTypeMappingUiDefinitions()) {
			if (ObjectTools.equals(definition.getKey(), mappingKey)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + mappingKey); //$NON-NLS-1$
	}

	public synchronized Iterable<MappingUiDefinition> getTypeMappingUiDefinitions() {
		if (this.typeMappingUiDefinitions == null) {
			this.typeMappingUiDefinitions = this.buildTypeMappingUiDefinitions();
		}
		return this.typeMappingUiDefinitions;
	}

	protected ArrayList<MappingUiDefinition> buildTypeMappingUiDefinitions() {
		ArrayList<MappingUiDefinition> definitions = new ArrayList<MappingUiDefinition>();
		this.addTypeMappingUiDefinitionsTo(definitions);
		return definitions;
	}

	protected void addTypeMappingUiDefinitionsTo(List<MappingUiDefinition> definitions) {
		definitions.add(EntityUiDefinition.instance());
		definitions.add(MappedSuperclassUiDefinition.instance());
		definitions.add(EmbeddableUiDefinition.instance());
	}


	// ********** attribute mappings **********

	public JpaComposite buildAttributeMappingComposite(String mappingKey, PropertyValueModel<AttributeMapping> mappingModel, PropertyValueModel<Boolean> enabledModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		MappingUiDefinition definition = this.getAttributeMappingUiDefinition(mappingModel.getValue());
		return definition.buildMappingComposite(this.factory, mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	protected MappingUiDefinition getAttributeMappingUiDefinition(AttributeMapping attributeMapping) {
		String mappingKey = (attributeMapping == null) ? null : attributeMapping.getKey();
		return this.getAttributeMappingUiDefinition(mappingKey);
	}

	public synchronized Iterable<MappingUiDefinition> getAttributeMappingUiDefinitions() {
		if (this.specifiedAttributeMappingUiDefinitions == null) {
			this.specifiedAttributeMappingUiDefinitions = this.buildSpecifiedAttributeMappingUiDefinitions();
		}
		return this.specifiedAttributeMappingUiDefinitions;
	}

	protected ArrayList<MappingUiDefinition> buildSpecifiedAttributeMappingUiDefinitions() {
		ArrayList<MappingUiDefinition> definitions = new ArrayList<MappingUiDefinition>();
		this.addSpecifiedAttributeMappingUiDefinitionsTo(definitions);
		return definitions;
	}

	protected abstract void addSpecifiedAttributeMappingUiDefinitionsTo(List<MappingUiDefinition> definitions);

}
