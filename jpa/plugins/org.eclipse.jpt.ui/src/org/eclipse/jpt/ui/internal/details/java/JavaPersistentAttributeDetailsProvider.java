/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.java;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaStructureNodes;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaDetailsPage;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.utility.internal.Tools;
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
	 * Return the singleton
	 */
	public static JpaDetailsProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private JavaPersistentAttributeDetailsProvider() {
		super();
	}
	
	
	public boolean providesDetails(JpaStructureNode structureNode) {
			return Tools.valuesAreEqual(structureNode.getId(), JavaStructureNodes.PERSISTENT_ATTRIBUTE_ID)
				&& structureNode.getResourceType().getContentType().equals(JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE);
	}
	
	public JpaDetailsPage<JavaPersistentAttribute> buildDetailsPage(
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return new JavaPersistentAttributeDetailsPage(parent, widgetFactory);
	}
}
