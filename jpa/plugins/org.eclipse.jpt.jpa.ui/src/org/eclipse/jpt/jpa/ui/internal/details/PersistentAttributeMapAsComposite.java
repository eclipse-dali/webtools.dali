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
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.ModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.orm.UnsupportedOrmAttributeMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

/**
 * This "Map As" composite is responsible for showing the mapping name and
 * mapping type for an attribute.
 */
public class PersistentAttributeMapAsComposite 
	extends MapAsComposite<PersistentAttribute>
{
	public PersistentAttributeMapAsComposite(Pane<? extends PersistentAttribute> parentPane, Composite parent) {
		super(parentPane, parent);
	}

	public PersistentAttributeMapAsComposite(Pane<? extends PersistentAttribute> parentPane, Composite parent, PropertyValueModel<Boolean> enabledModel) {
		super(parentPane, parent, enabledModel);
	}
	
	protected String getMappingKey() {
		return getSubject().getMappingKey();
	}

	@Override
	protected MappingChangeHandler buildMappingChangeHandler() {
		return new AttributeMappingChangeHandler();
	}

	protected class AttributeMappingChangeHandler
		implements MappingChangeHandler
	{
		public String getLabelText() {
			String mappingKey = getMappingKey();

			if (mappingKey != MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY) {
				return JptJpaUiDetailsMessages.MapAsComposite_mappedAttributeText;
			}
			if (getSubject().isVirtual()) {
				return JptJpaUiDetailsMessages.MapAsComposite_virtualAttributeText;
			}

			return JptJpaUiDetailsMessages.MapAsComposite_unmappedAttributeText;
		}

		public String getMappingText() {
			AttributeMapping mapping = getSubject().getMapping();
			String mappingKey = mapping.getKey();

			return mapping.isDefault() ? 
                          getDefaultDefinition(mappingKey).getLinkLabel() : 
                          getMappingUiDefinition().getLinkLabel();
		}
		
		public void morphMapping(MappingUiDefinition definition) {
			((ModifiablePersistentAttribute) getSubject()).setMappingKey(definition.getKey());
		}
		
		public String getName() {
			return getSubject().getName();
		}
		
		public Iterable<MappingUiDefinition> getMappingUiDefinitions() {
			return getAttributeMappingUiDefinitions();
		}

		public MappingUiDefinition getMappingUiDefinition() {
			return getAttributeMappingUiDefinition();
		}
	}
	
	protected Iterable<MappingUiDefinition> getAttributeMappingUiDefinitions() {
		JpaPlatformUi ui = this.getJpaPlatformUi();
		return (ui != null) ? ui.getAttributeMappingUiDefinitions(getSubject()) : IterableTools.<MappingUiDefinition>emptyIterable();
	}
	
	protected MappingUiDefinition getAttributeMappingUiDefinition() {
		JpaPlatformUi ui = this.getJpaPlatformUi();
		return (ui == null) ? null : ui.getAttributeMappingUiDefinition(getSubject().getResourceType(), getMappingKey());
	}
	
	@Override
	protected DefaultMappingUiDefinition getDefaultDefinition() {
		return getDefaultDefinition(getSubject().getDefaultMappingKey());
	}
	
	@Override
	protected DefaultMappingUiDefinition getDefaultDefinition(String mappingKey) {
		JpaPlatformUi ui = this.getJpaPlatformUi();
		return (ui == null) ? null : ui.getDefaultAttributeMappingUiDefinition(getSubject().getMapping().getResourceType(), mappingKey);
	}

	@Override
	protected MappingUiDefinition getMappingUiDefinition() {
		MappingUiDefinition definition = super.getMappingUiDefinition();
		return (definition != null) ? definition : UnsupportedOrmAttributeMappingUiDefinition.instance();
	}
	
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(PersistentAttribute.DEFAULT_MAPPING_KEY_PROPERTY);
		propertyNames.add(PersistentAttribute.MAPPING_PROPERTY);
		propertyNames.add(PersistentAttribute.NAME_PROPERTY);
	}

	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == PersistentAttribute.MAPPING_PROPERTY ||
		    propertyName == PersistentAttribute.DEFAULT_MAPPING_KEY_PROPERTY   ||
		    propertyName == PersistentAttribute.NAME_PROPERTY) {

			updateDescription();
		}
	}
}
