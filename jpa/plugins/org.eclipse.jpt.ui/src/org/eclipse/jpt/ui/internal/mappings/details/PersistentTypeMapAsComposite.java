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
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.details.MappingUiProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.swt.widgets.Composite;

/**
 * This "Map As" composite is responsible for showing the mapping name and
 * mapping type for a type.
 *
 * @see JavaPersistentTypeMapAsComposite
 * @see OrmPersistentTypeMapAsComposite
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class PersistentTypeMapAsComposite<T extends PersistentType> extends MapAsComposite<T>
{
	/**
	 * Creates a new <code>PersistentTypeMapAsComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public PersistentTypeMapAsComposite(AbstractPane<? extends T> parentPane,
	                                    Composite parent) {

		super(parentPane, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(PersistentType.MAPPING_PROPERTY);
		propertyNames.add(PersistentType.NAME_PROPERTY);
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
					return JptUiMessages.MapAsComposite_mappedTypeText;
				}

				return JptUiMessages.MapAsComposite_unmappedTypeText;
			}

			public String mappingType() {
				String mappingKey = subject().mappingKey();

				if (mappingKey == null) {
					return JptUiMessages.MapAsComposite_changeMappingType;
				}

				return SWTUtil.buildDisplayString(
					JptUiMessages.class,
					MapAsComposite.class,
					mappingKey
				);
			}

			public void morphMapping(MappingUiProvider<?> provider) {
				subject().setMappingKey(provider.mappingKey());
			}

			public String name() {
				return subject().getName();
			}

			public Iterator<? extends MappingUiProvider<?>> providers() {
				return typeMappingUiProviders();
			}
		};
	}

	/**
	 * Returns the JPT platform responsble to manage the user interface part of
	 * the JPT plug-in.
	 *
	 * @return The UI platform of the JPT plug-in
	 */
	protected JpaPlatformUi jpaPlatformUi() {
		String platformId = subject().getJpaProject().getJpaPlatform().getId();
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

		if (propertyName == PersistentType.MAPPING_PROPERTY ||
		    propertyName == PersistentType.NAME_PROPERTY) {

			updateDescription();
		}
	}

	/**
	 * Retrieves the list of providers that are registered with the JPT plugin.
	 *
	 * @return The supported types of mapping
	 */
	protected abstract Iterator<TypeMappingUiProvider<? extends TypeMapping>> typeMappingUiProviders();
}
