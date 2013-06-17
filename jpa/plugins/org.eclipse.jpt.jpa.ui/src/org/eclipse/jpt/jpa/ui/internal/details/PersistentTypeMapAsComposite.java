/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.ManagedType;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

/**
 * This "Map As" composite is responsible for showing the mapping name and
 * mapping type for a type.
 */
public class PersistentTypeMapAsComposite
	extends MapAsComposite<PersistentType>
{
	public PersistentTypeMapAsComposite(Pane<? extends PersistentType> parentPane, Composite parent) {
		super(parentPane, parent);
	}
	
	protected String getMappingKey() {
		return getSubject().getMappingKey();
	}

	@Override
	protected MappingChangeHandler buildMappingChangeHandler() {
		return new TypeMappingChangeHandler();
	}

	public class TypeMappingChangeHandler
		extends AbstractMappingChangeHandler
	{
		public String getLabelText() {
			String mappingKey = getMappingKey();
			return (mappingKey != null) ?
					JptJpaUiDetailsMessages.MAP_AS_COMPOSITE_MAPPED_TYPE_TEXT :
					JptJpaUiDetailsMessages.MAP_AS_COMPOSITE_UNMAPPED_TYPE_TEXT;
		}

		public String getMappingText() {
			return getMappingUiDefinition().getLinkLabel();
		}

		@Override
		protected void morphMapping_(MappingUiDefinition definition) {
			getSubject().setMappingKey(definition.getKey());
		}

		public String getName() {
			return getSubject().getTypeQualifiedName();
		}

		public Iterable<MappingUiDefinition> getMappingUiDefinitions() {
			return getTypeMappingUiDefinitions();
		}

		public MappingUiDefinition getMappingUiDefinition() {
			return getTypeMappingUiDefinition();
		}
	}

	/**
	 * Retrieves the list of definitions that are registered with the JPT plugin.
	 *
	 * @return The supported types of mapping
	 */
	protected Iterable<MappingUiDefinition> getTypeMappingUiDefinitions() {
		JpaPlatformUi ui = this.getJpaPlatformUi();
		return (ui != null) ? ui.getTypeMappingUiDefinitions(getSubject()) : IterableTools.<MappingUiDefinition>emptyIterable();
	}
	
	protected MappingUiDefinition getTypeMappingUiDefinition() {
		JpaPlatformUi ui = this.getJpaPlatformUi();
		return (ui == null) ? null : ui.getTypeMappingUiDefinition(getSubject().getResourceType(), getMappingKey());
	}
	
	@Override
	protected DefaultMappingUiDefinition getDefaultDefinition() {
		JpaPlatformUi ui = this.getJpaPlatformUi();
		return (ui == null) ? null : ui.getDefaultTypeMappingUiDefinition(getSubject().getResourceType());
	}
	
	@Override
	protected DefaultMappingUiDefinition getDefaultDefinition(String mappingKey) {
		return getDefaultDefinition();
	}
	
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(PersistentType.MAPPING_PROPERTY);
		propertyNames.add(ManagedType.NAME_PROPERTY);
	}

	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == PersistentType.MAPPING_PROPERTY ||
		    propertyName == ManagedType.NAME_PROPERTY) {

			updateDescription();
		}
	}
}
