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

import java.util.Iterator;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.PersistentTypeDetailsPage;
import org.eclipse.jpt.ui.internal.mappings.details.JavaPersistentTypeMapAsComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * The default implementation of the details page used for the Java persistent
 * type.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | JavaPersistentTypeMapAsComposite                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | Type mapping pane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see JavaPersistentType
 * @see JavaPersistentTypeMapAsComposite
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

	@Override
	protected Iterator<TypeMappingUiProvider<? extends TypeMapping>> typeMappingUiProviders() {
		return getJpaPlatformUi().javaTypeMappingUiProviders();
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Map As composite
		new JavaPersistentTypeMapAsComposite(
			this,
			addSubPane(container, 0, 0, 5, 0)
		);

		// Type properties page
		buildTypeMappingPageBook(container);
	}
}