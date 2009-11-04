/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.ui.details.MappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.orm.UnsupportedOrmMappingUiDefinition;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.swt.widgets.Composite;

/**
 * This "Map As" composite is responsible for showing the mapping name and
 * mapping type for an attribute.
 *
 * @see JavaPersistentAttributeMapAsComposite
 * @see OrmPersistentAttributeMapAsComposite
 */
public class PersistentAttributeMapAsComposite 
	extends MapAsComposite<PersistentAttribute>
{
	/**
	 * Creates a new <code>PersistentAttributeMapAsComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public PersistentAttributeMapAsComposite(
			Pane<? extends PersistentAttribute> parentPane,
            Composite parent) {
		
		super(parentPane, parent);
	}
	
	
	@Override
	protected String getMappingKey() {
		return getSubject().getMappingKey();
	}

	@Override
	protected MappingChangeHandler buildMappingChangeHandler() {
		return new MappingChangeHandler() {

			public String getLabelText() {
				String mappingKey = getMappingKey();

				if (mappingKey != MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY) {
					return JptUiDetailsMessages.MapAsComposite_mappedAttributeText;
				}
				if (getSubject().isVirtual()) {
					return JptUiDetailsMessages.MapAsComposite_virtualAttributeText;
				}

				return JptUiDetailsMessages.MapAsComposite_unmappedAttributeText;
			}

			public String getMappingText() {
				String mappingKey = getMappingKey();

				if (mappingKey == null) {
					return JptUiDetailsMessages.MapAsComposite_changeMappingType;
				}

				if (getSubject().getSpecifiedMapping() == null) {
					return getDefaultDefinition(getSubject().getDefaultMappingKey()).getLinkLabel();
				}

				return getMappingUiDefinition(mappingKey).getLinkLabel();
			}

			public void morphMapping(MappingUiDefinition<?> definition) {
				getSubject().setSpecifiedMappingKey(definition.getKey());
			}

			public String getName() {
				return getSubject().getName();
			}

			public Iterator<? extends MappingUiDefinition<?>> mappingUiDefinitions() {
				return attributeMappingUiDefinitions();
			}
		};
	}
	
	protected Iterator<? extends MappingUiDefinition<? extends AttributeMapping>> attributeMappingUiDefinitions() {
		return getJpaPlatformUi().attributeMappingUiDefinitions(getSubject().getResourceType());
	}
	
	@Override
	protected DefaultMappingUiDefinition<?> getDefaultDefinition() {
		return getDefaultDefinition(getSubject().getDefaultMappingKey());
	}
	
	@Override
	protected DefaultMappingUiDefinition<?> getDefaultDefinition(String mappingKey) {
		return getJpaPlatformUi().getDefaultAttributeMappingUiDefinition(getSubject().getResourceType(), mappingKey);
	}

	@Override
	protected MappingUiDefinition<?> getMappingUiDefinition(String mappingKey) {
		MappingUiDefinition<?> definition = super.getMappingUiDefinition(mappingKey);
		if (definition != null) {
			return definition;
		}
		return UnsupportedOrmMappingUiDefinition.instance();
	}
	
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(PersistentAttribute.DEFAULT_MAPPING_PROPERTY);
		propertyNames.add(PersistentAttribute.SPECIFIED_MAPPING_PROPERTY);
		propertyNames.add(PersistentAttribute.NAME_PROPERTY);
	}

	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == PersistentAttribute.SPECIFIED_MAPPING_PROPERTY ||
		    propertyName == PersistentAttribute.DEFAULT_MAPPING_PROPERTY   ||
		    propertyName == PersistentAttribute.NAME_PROPERTY) {

			updateDescription();
		}
	}
}
