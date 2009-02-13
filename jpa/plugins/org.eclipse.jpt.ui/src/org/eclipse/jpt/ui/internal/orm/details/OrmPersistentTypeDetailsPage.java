/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import java.util.Iterator;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.PersistentTypeDetailsPage;
import org.eclipse.jpt.ui.internal.mappings.details.OrmPersistentTypeMapAsComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;

/**
 * The default implementation of the details page used for the XML persistent
 * attribute.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OrmJavaClassChooser                                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OrmPersistentTypeMapAsComposite                                       | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | X Metadata Complete                                                       |
 * |                                                                           |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OrmJavaClassChooser                                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | Type mapping pane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see OrmPersistentType
 * @see OrmJavaClassChooser
 * @see AccessTypeComposite
 * @see OrmPersistentTypeMapAsComposite
 *
 * @version 2.0
 * @since 2.0
 */
public class OrmPersistentTypeDetailsPage extends PersistentTypeDetailsPage<OrmPersistentType>
{
	/**
	 * Creates a new <code>OrmPersistentTypeDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmPersistentTypeDetailsPage(Composite parent,
	                                    WidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Type Mapping widgets
		new OrmPersistentTypeMapAsComposite(
			this,
			addSubPane(container, 0, 0, 5, 0)
		);

		// Type mapping pane
		PageBook typeMappingPageBook = buildTypeMappingPageBook(container);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.TOP;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;

		typeMappingPageBook.setLayoutData(gridData);
	}

	@Override
	public Iterator<TypeMappingUiProvider<? extends TypeMapping>> typeMappingUiProviders() {
		return getJpaPlatformUi().ormTypeMappingUiProviders();
	}
}