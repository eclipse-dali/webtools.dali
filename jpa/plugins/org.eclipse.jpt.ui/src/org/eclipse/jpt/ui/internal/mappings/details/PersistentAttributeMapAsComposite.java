/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.MappingUiProvider;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

/**
 * This "Map As" composite is responsible for showing the mapping name and
 * mapping type for an attribute.
 *
 * @see JavaPersistentAttributeMapAsComposite
 * @see OrmPersistentAttributeMapAsComposite
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class PersistentAttributeMapAsComposite<T extends PersistentAttribute> extends MapAsComposite<T> {

	/**
	 * Creates a new <code>PersistentAttributeMapAsComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public PersistentAttributeMapAsComposite(AbstractPane<? extends T> parentPane,
                                            Composite parent) {

		super(parentPane, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(PersistentAttribute.DEFAULT_MAPPING_PROPERTY);
		propertyNames.add(PersistentAttribute.SPECIFIED_MAPPING_PROPERTY);
		propertyNames.add(PersistentAttribute.NAME_PROPERTY);
	}

	/**
	 * Retrieves the list of providers that are registered with the JPT plugin.
	 *
	 * @return The supported types of mapping
	 */
	protected abstract Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> attributeMappingUiProviders();

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected MappingUiProvider<T> buildDefaultProvider() {

		if (subject().defaultMappingKey() == null) {
			return null;
		}

		return new MappingUiProvider<T>() {

			public Image image() {
				String mappingKey = subject().defaultMappingKey();
				return JpaMappingImageHelper.imageForAttributeMapping(mappingKey);
			}

			public String label() {
				String mappingKey = subject().defaultMappingKey();

				return SWTUtil.buildDisplayString(
					JptUiMessages.class,
					MapAsComposite.class,
					mappingKey + "_default2"
				);
			}

			public String mappingKey() {
				return null;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected MappingChangeHandler buildMappingChangeHandler() {
		return new MappingChangeHandler() {

			public String labelText() {
				String mappingKey = subject().mappingKey();

				if (mappingKey != null) {
					return JptUiMessages.MapAsComposite_mappedAttributeText;
				}

				return JptUiMessages.MapAsComposite_unmappedAttributeText;
			}

			public String mappingType() {
				String mappingKey = subject().mappingKey();

				if (mappingKey == null) {
					return JptUiMessages.MapAsComposite_changeMappingType;
				}

				if (subject().getSpecifiedMapping() == null) {
					return SWTUtil.buildDisplayString(
						JptUiMessages.class,
						MapAsComposite.class,
						mappingKey + "_default"
					);
				}

				return SWTUtil.buildDisplayString(
					JptUiMessages.class,
					MapAsComposite.class,
					mappingKey
				);
			}

			public void morphMapping(MappingUiProvider<?> provider) {
				subject().setSpecifiedMappingKey(provider.mappingKey());
			}

			public String name() {
				return subject().getName();
			}

			public Iterator<? extends MappingUiProvider<?>> providers() {
				return attributeMappingUiProviders();
			}
		};
	}

	/**
	 * Returns the list of providers that are registered with the JPT plugin.
	 *
	 * @return The supported default types of mapping
	 */
	protected abstract Iterator<AttributeMappingUiProvider<? extends AttributeMapping>>
		defaultAttributeMappingUiProviders();

	/**
	 * Returns the JPT platform responsble to manage the user interface part of
	 * the JPT plug-in.
	 *
	 * @return The UI platform of the JPT plug-in
	 */
	protected JpaPlatformUi jpaPlatformUi() {
		String platformId = subject().jpaProject().jpaPlatform().getId();
		return JpaPlatformUiRegistry.instance().jpaPlatform(platformId);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String mappingKey() {
		return subject().mappingKey();
	}

	/*
	 * (non-Javadoc)
	 */
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
