/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultMappingUiProvider;
import org.eclipse.jpt.ui.details.MappingUiProvider;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.swt.widgets.Composite;

/**
 * This "Map As" composite is responsible for showing the mapping name and
 * mapping type for an attribute.
 *
 * @see JavaPersistentAttributeMapAsComposite
 * @see OrmPersistentAttributeMapAsComposite
 *
 * @version 2.2
 * @since 2.0
 */
public class PersistentAttributeMapAsComposite extends MapAsComposite<PersistentAttribute> {

	/**
	 * Creates a new <code>PersistentAttributeMapAsComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public PersistentAttributeMapAsComposite(Pane<? extends PersistentAttribute> parentPane,
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
					return JptUiMappingsMessages.MapAsComposite_mappedAttributeText;
				}
				if (getSubject().isVirtual()) {
					return JptUiMappingsMessages.MapAsComposite_virtualAttributeText;
				}

				return JptUiMappingsMessages.MapAsComposite_unmappedAttributeText;
			}

			public String getMappingText() {
				String mappingKey = getMappingKey();

				if (mappingKey == null) {
					return JptUiMappingsMessages.MapAsComposite_changeMappingType;
				}

				if (getSubject().getSpecifiedMapping() == null) {
					return getDefaultProvider(getSubject().getDefaultMappingKey()).getLinkLabel();
				}

				return getProvider(mappingKey).getLinkLabel();
			}

			public void morphMapping(MappingUiProvider<?> provider) {
				getSubject().setSpecifiedMappingKey(provider.getKey());
			}

			public String getName() {
				return getSubject().getName();
			}

			public Iterator<? extends MappingUiProvider<?>> providers() {
				return attributeMappingUiProviders();
			}
		};
	}
	
	/**
	 * Retrieves the list of providers that are registered with the JPT plugin.
	 *
	 * @return The supported types of mapping
	 */
	protected Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> attributeMappingUiProviders() {
		return getJpaPlatformUi().attributeMappingUiProviders(getSubject().getContentType());
	}

	@Override
	protected DefaultMappingUiProvider<?> getDefaultProvider() {
		return getDefaultProvider(getSubject().getDefaultMappingKey());

	}
	
	@Override
	protected DefaultMappingUiProvider<?> getDefaultProvider(String mappingKey) {
		return getJpaPlatformUi().getDefaultAttributeMappingUiProvider(mappingKey, getSubject().getContentType());
	}
	
	/**
	 * Returns the list of providers that are registered with the JPT plugin.
	 *
	 * @return The supported default types of mapping
	 */
	protected Iterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> defaultAttributeMappingUiProviders() {
		return getJpaPlatformUi().defaultAttributeMappingUiProviders(getSubject().getContentType());
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
