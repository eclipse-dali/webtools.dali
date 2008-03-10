/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.details;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.PersistentTypeDetailsPage;
import org.eclipse.jpt.ui.internal.platform.base.BaseJpaPlatformUi;
import org.eclipse.jpt.ui.internal.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;

/**
 * The default implementation of the details page used for the Java persistent
 * type.
 *
 * @see JavaPersistentType
 *
 * @version 2.0
 * @since 2.0
 */
public class JavaPersistentTypeDetailsPage extends PersistentTypeDetailsPage<JavaPersistentType>
{
	/**
	 * Creates a new <code>JavaPersistentTypeDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JavaPersistentTypeDetailsPage(Composite parent,
	                                     WidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected ListIterator<TypeMappingUiProvider<? extends TypeMapping>> typeMappingUiProviders() {
		// TODO
		return ((BaseJpaPlatformUi) jpaPlatformUi()).javaTypeMappingUiProviders();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Entity Type widgets
		buildLabeledComposite(
			container,
			buildTypeMappingLabel(container),
			buildTypeMappingCombo(container).getControl().getParent()
		);

		PageBook typeMappingPageBook = buildTypeMappingPageBook(container);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.TOP;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;

		typeMappingPageBook.setLayoutData(gridData);
	}
}