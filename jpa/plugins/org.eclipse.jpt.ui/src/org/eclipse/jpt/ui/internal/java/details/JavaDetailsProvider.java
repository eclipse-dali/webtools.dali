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

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.java.JavaStructureNodes;
import org.eclipse.jpt.ui.details.JpaDetailsPage;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * This provider is responsible for creating the <code>IJpaDetailsPage</code>
 * when the information comes from the Java source file.
 *
 * @version 2.0
 * @since 1.0
 */
public class JavaDetailsProvider
	implements JpaDetailsProvider
{
	/**
	 * Creates a new <code>JavaDetailsProvider</code>.
	 */
	public JavaDetailsProvider() {
		super();
	}

	/*
	 * (non-Javadoc)
	 */
	public JpaDetailsPage<? extends JpaStructureNode> buildDetailsPage(
		Composite parent,
		Object contentNodeId,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		if (contentNodeId == JavaStructureNodes.PERSISTENT_TYPE_ID) {
			return new JavaPersistentTypeDetailsPage(parent, widgetFactory);
		}

		if (contentNodeId == JavaStructureNodes.PERSISTENT_ATTRIBUTE_ID) {
			return new JavaPersistentAttributeDetailsPage(parent, widgetFactory);
		}

		return null;
	}
}