/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.details;

import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaStructureNodes;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaDetailsPage;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * This provider is responsible for creating the <code>JpaDetailsPage</code>
 * when the information comes from the Java source file.
 */
public class JavaPersistentAttributeDetailsProvider
	implements JpaDetailsProvider
{

	// singleton
	private static final JpaDetailsProvider INSTANCE = new JavaPersistentAttributeDetailsProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaDetailsProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private JavaPersistentAttributeDetailsProvider() {
		super();
	}
	
	public String getId() {
		return JavaStructureNodes.PERSISTENT_ATTRIBUTE_ID;
	}

	public JpaDetailsPage<JavaPersistentAttribute> buildDetailsPage(
		Composite parent,
		WidgetFactory widgetFactory) {
		
		return new JavaPersistentAttributeDetailsPage(parent, widgetFactory);
	}
}
