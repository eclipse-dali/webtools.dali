/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jpt.common.ui.internal.jface.SimpleItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.jpa.core.context.ManagedType;
import org.eclipse.jpt.jpa.ui.JavaManagedTypeUiDefinition;
import org.eclipse.jpt.jpa.ui.PersistenceResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.structure.JpaFileStructureItemContentProviderFactory;
import org.eclipse.jpt.jpa.ui.internal.structure.PersistenceStructureItemLabelProviderFactory;

/**
 * All the state in the definition should be "static" (i.e. unchanging once it is initialized).
 */
public abstract class AbstractPersistenceResourceUiDefinition
	extends AbstractResourceUiDefinition
	implements PersistenceResourceUiDefinition
{
	private ArrayList<JavaManagedTypeUiDefinition> javaManagedTypeUiDefinitions;


	/**
	 * zero-argument constructor
	 */
	protected AbstractPersistenceResourceUiDefinition() {
		super();
	}


	// ********** java managed types ui definitions **********

	public JavaManagedTypeUiDefinition getJavaManagedTypeUiDefinition(Class<? extends ManagedType> type) {
		for (JavaManagedTypeUiDefinition definition : this.getJavaManagedTypeUiDefinitions()) {
			if (definition.getManagedTypeType() == type) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal type : " + type); //$NON-NLS-1$
	}

	protected synchronized Iterable<JavaManagedTypeUiDefinition> getJavaManagedTypeUiDefinitions() {
		if (this.javaManagedTypeUiDefinitions == null) {
			this.javaManagedTypeUiDefinitions = this.buildJavaManagedTypeUiDefinitions();
		}
		return this.javaManagedTypeUiDefinitions;
	}

	protected ArrayList<JavaManagedTypeUiDefinition> buildJavaManagedTypeUiDefinitions() {
		ArrayList<JavaManagedTypeUiDefinition> definitions = new ArrayList<JavaManagedTypeUiDefinition>();
		this.addJavaManagedTypeUiDefinitionsTo(definitions);
		return definitions;
	}

	protected void addJavaManagedTypeUiDefinitionsTo(List<JavaManagedTypeUiDefinition> definitions) {
		definitions.add(JavaPersistentTypeUiDefinition.instance());
	}
	
	public ItemTreeStateProviderFactoryProvider getStructureViewFactoryProvider() {
		return STRUCTURE_VIEW_FACTORY_PROVIDER;
	}
	
	public static final ItemTreeStateProviderFactoryProvider STRUCTURE_VIEW_FACTORY_PROVIDER =
			new SimpleItemTreeStateProviderFactoryProvider(
					JpaFileStructureItemContentProviderFactory.instance(),
					PersistenceStructureItemLabelProviderFactory.instance()
				);
}
