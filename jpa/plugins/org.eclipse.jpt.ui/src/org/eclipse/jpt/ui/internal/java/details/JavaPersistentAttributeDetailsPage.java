/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.details;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.PersistentAttributeDetailsPage;
import org.eclipse.jpt.ui.internal.platform.base.BaseJpaPlatformUi;
import org.eclipse.jpt.ui.internal.widgets.WidgetFactory;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;

/**
 * The default implementation of the details page used for the Java persistent
 * attribute.
 *
 * @see PersistentAttribute
 *
 * @version 2.0
 * @since 2.0
 */
public class JavaPersistentAttributeDetailsPage extends PersistentAttributeDetailsPage<JavaPersistentAttribute>
{
	/**
	 * Creates a new <code>JavaPersistentAttributeDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JavaPersistentAttributeDetailsPage(Composite parent,
	                                          WidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected ListIterator<AttributeMappingUiProvider<? extends AttributeMapping>> attributeMappingUiProviders() {
		// TODO
		return ((BaseJpaPlatformUi) jpaPlatformUi()).javaAttributeMappingUiProviders();
	}

	/**
	 * These IAtttributeMappingUiProviders will be used as elements in the attributeMapping combo
	 * The first element in the combo will be one of the defaultAttributeMappingUiProviders or
	 * if none of those apply the nullAttributeMappingUiProvider will be used. The rest of the elements
	 * will be the attributeMappingUiProviders.  The defaultAttributeMappingUiProvider is
	 * determined by matching its key with the key of the current attributeMapping.
	 */
	@Override
	protected AttributeMappingUiProvider<? extends AttributeMapping>[] attributeMappingUiProvidersFor(PersistentAttribute persistentAttribute) {
		AttributeMappingUiProvider<? extends AttributeMapping>[] providers = new AttributeMappingUiProvider<?>[CollectionTools.size(attributeMappingUiProviders()) + 1];
		providers[0] =  defaultAttributeMappingUiProvider(persistentAttribute.defaultMappingKey());
		int i = 1;
		for (ListIterator<AttributeMappingUiProvider<? extends AttributeMapping>> iterator = attributeMappingUiProviders(); iterator.hasNext(); ) {
			providers[i++] = iterator.next();
		}
		return providers;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected AttributeMappingUiProvider<? extends AttributeMapping> defaultAttributeMappingUiProvider(String key) {
		for (ListIterator<AttributeMappingUiProvider<? extends AttributeMapping>> i = defaultAttributeMappingUiProviders(); i.hasNext(); ) {
			AttributeMappingUiProvider<? extends AttributeMapping> provider = i.next();
			if (provider.mappingKey() == key) {
				return provider;
			}
		}
		return this.nullAttributeMappingUiProvider();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected ListIterator<AttributeMappingUiProvider<? extends AttributeMapping>> defaultAttributeMappingUiProviders() {
		// TODO
//		return jpaPlatformUi().defaultJavaAttributeMappingUiProviders();
		return ((BaseJpaPlatformUi) jpaPlatformUi()).defaultJavaAttributeMappingUiProviders();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Note: The combo's parent is a container fixing the issue with the
		// border not being painted
		buildLabeledComposite(
			container,
			buildMappingLabel(container),
			buildMappingCombo(container).getControl().getParent()
		);

		PageBook mappingPane = buildMappingPageBook(container);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.TOP;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;

		mappingPane.setLayoutData(gridData);
	}

	protected AttributeMappingUiProvider<AttributeMapping> nullAttributeMappingUiProvider() {
		return NullAttributeMappingUiProvider.instance();
	}
}