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
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.ui.details.DefaultMappingUiProvider;
import org.eclipse.jpt.ui.details.MappingUiProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.swt.widgets.Composite;

/**
 * This "Map As" composite is responsible for showing the mapping name and
 * mapping type for a type.
 *
 * @see JavaPersistentTypeMapAsComposite
 * @see OrmPersistentTypeMapAsComposite
 *
 * @version 2.2
 * @since 2.0
 */
public class PersistentTypeMapAsComposite extends MapAsComposite<PersistentType>
{
	/**
	 * Creates a new <code>PersistentTypeMapAsComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public PersistentTypeMapAsComposite(Pane<? extends PersistentType> parentPane,
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

				if (mappingKey != null) {
					return JptUiMappingsMessages.MapAsComposite_mappedTypeText;
				}

				return JptUiMappingsMessages.MapAsComposite_unmappedTypeText;
			}

			public String getMappingText() {
				String mappingKey = getMappingKey();

				if (mappingKey == null) {
					return JptUiMappingsMessages.MapAsComposite_changeMappingType;
				}

				return getProvider(mappingKey).getLinkLabel();
			}

			public void morphMapping(MappingUiProvider<?> provider) {
				getSubject().setMappingKey(provider.getKey());
			}

			public String getName() {
				return getSubject().getShortName();
			}

			public Iterator<? extends MappingUiProvider<?>> providers() {
				return typeMappingUiProviders(getSubject().getContentType());
			}
		};
	}

	/**
	 * Retrieves the list of providers that are registered with the JPT plugin.
	 *
	 * @return The supported types of mapping
	 */
	protected Iterator<TypeMappingUiProvider<? extends TypeMapping>> typeMappingUiProviders(IContentType contentType) {
		return getJpaPlatformUi().typeMappingUiProviders(contentType);
	}
	
	@Override
	protected DefaultMappingUiProvider<?> getDefaultProvider() {
		return getJpaPlatformUi().getDefaultTypeMappingUiProvider(getSubject().getContentType());
	}
	
	@Override
	protected DefaultMappingUiProvider<?> getDefaultProvider(String mappingKey) {
		return getDefaultProvider();
	}
	
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(PersistentType.MAPPING_PROPERTY);
		propertyNames.add(PersistentType.NAME_PROPERTY);
	}

	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == PersistentType.MAPPING_PROPERTY ||
		    propertyName == PersistentType.NAME_PROPERTY) {

			updateDescription();
		}
	}

}
