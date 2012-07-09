/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.ui.internal.details.PersistentAttributeDetailsPage;
import org.eclipse.jpt.jpa.ui.internal.details.PersistentAttributeMapAsComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * The default implementation of the details page used for the Java persistent
 * attribute.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | JavaPersistentAttributeMapAsComposite                                 | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | Type mapping pane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see JavaPersistentAttribute
 * @see JavaPersistentTypeMapAsComposite
 *
 * @version 2.2
 * @since 2.0
 */
public class JavaPersistentAttributeDetailsPage
	extends PersistentAttributeDetailsPage<JavaPersistentAttribute>
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

	@Override
	protected void initializeLayout(Composite container) {
		new PersistentAttributeMapAsComposite(this, container);

		this.buildMappingPageBook(container);
	}

	private static final PropertyValueModel<Boolean> TRUE_ENABLED_MODEL = new StaticPropertyValueModel<Boolean>(Boolean.TRUE);

	@Override
	protected PropertyValueModel<Boolean> getMappingCompositeEnabledModel() {
		return TRUE_ENABLED_MODEL;
	}

}