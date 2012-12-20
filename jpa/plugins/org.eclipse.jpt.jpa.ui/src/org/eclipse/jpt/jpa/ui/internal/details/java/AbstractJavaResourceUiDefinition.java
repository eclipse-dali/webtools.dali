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
import org.eclipse.jpt.common.ui.internal.jface.SimpleItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaSourceFileDefinition;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappingResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.structure.JavaStructureItemContentProviderFactory;
import org.eclipse.jpt.jpa.ui.internal.structure.JavaStructureItemLabelProviderFactory;

/**
 * All the state in the definition should be "static"
 * (i.e. unchanging once it is initialized).
 */
public abstract class AbstractJavaResourceUiDefinition
	extends AbstractMappingResourceUiDefinition
{

	private ArrayList<DefaultMappingUiDefinition> defaultAttributeMappingUiDefinitions;


	/**
	 * zero-argument constructor
	 */
	protected AbstractJavaResourceUiDefinition() {
		super();
	}

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

	public DefaultMappingUiDefinition getDefaultTypeMappingUiDefinition() {
		return NullJavaTypeMappingUiDefinition.instance();
	}


	// ********** attribute mappings **********

	public MappingUiDefinition getAttributeMappingUiDefinition(String mappingKey) {
		return (mappingKey == null) ?
				this.getDefaultAttributeMappingUiDefinition(mappingKey) :
				this.getSpecifiedAttributeMappingUiDefinition(mappingKey);
	}

	protected MappingUiDefinition getSpecifiedAttributeMappingUiDefinition(String mappingKey) {
		for (MappingUiDefinition definition : this.getAttributeMappingUiDefinitions()) {
			if (ObjectTools.equals(definition.getKey(), mappingKey)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal attribute mapping key: " + mappingKey); //$NON-NLS-1$
	}


	// ********** default attribute mappings **********

	public DefaultMappingUiDefinition getDefaultAttributeMappingUiDefinition(String mappingKey) {
		for (DefaultMappingUiDefinition definition : this.getDefaultAttributeMappingUiDefinitions()) {
			if (ObjectTools.equals(definition.getDefaultKey(), mappingKey)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal attribute mapping key: " + mappingKey); //$NON-NLS-1$
	}

	protected synchronized ArrayList<DefaultMappingUiDefinition> getDefaultAttributeMappingUiDefinitions() {
		if (this.defaultAttributeMappingUiDefinitions == null) {
			this.defaultAttributeMappingUiDefinitions = this.buildDefaultAttributeMappingUiDefinitions();
		}
		return this.defaultAttributeMappingUiDefinitions;
	}

	protected ArrayList<DefaultMappingUiDefinition> buildDefaultAttributeMappingUiDefinitions() {
		ArrayList<DefaultMappingUiDefinition> definitions = new ArrayList<DefaultMappingUiDefinition>();
		this.addDefaultAttributeMappingUiDefinitionsTo(definitions);
		return definitions;
	}

	protected abstract void addDefaultAttributeMappingUiDefinitionsTo(List<DefaultMappingUiDefinition> definitions);

}
