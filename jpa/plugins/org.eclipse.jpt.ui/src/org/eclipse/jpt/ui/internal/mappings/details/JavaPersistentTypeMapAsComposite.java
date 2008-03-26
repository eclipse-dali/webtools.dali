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
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.ui.details.MappingUiProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.java.details.NullTypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

/**
 * This "Map As" composite is responsible for showing the mapping name and
 * mapping type for a type declared in a Java type.
 *
 * @see JavaPersistentType
 * @see JavaPersistentTypeDetailsPage - The parent container
 *
 * @version 2.0
 * @since 2.0
 */
public class JavaPersistentTypeMapAsComposite extends PersistentTypeMapAsComposite<JavaPersistentType>
{
	/**
	 * Creates a new <code>JavaPersistentTypeMapAsComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public JavaPersistentTypeMapAsComposite(AbstractPane<? extends JavaPersistentType> parentPane,
	                                        Composite parent) {

		super(parentPane, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected MappingUiProvider<JavaPersistentType> buildDefaultProvider() {
		return new MappingUiProvider<JavaPersistentType>() {

			public Image getImage() {
				return JpaMappingImageHelper.imageForTypeMapping(null);
			}

			public String getLabel() {
				return JptUiMessages.MapAsComposite_default;
			}

			public String getMappingKey() {
				return null;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Iterator<TypeMappingUiProvider<? extends TypeMapping>> typeMappingUiProviders() {

		Collection<TypeMappingUiProvider<? extends TypeMapping>> providers =
			CollectionTools.collection(jpaPlatformUi().javaTypeMappingUiProviders());

		providers.remove(NullTypeMappingUiProvider.instance());
		return providers.iterator();
	}
}
