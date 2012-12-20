/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.java;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsPageManager;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.jpa.ui.internal.details.PersistentTypeDetailsPageManager;
import org.eclipse.swt.widgets.Composite;

/**
 * This provider is responsible for creating the <code>JpaDetailsPage</code>
 * when the information comes from the Java source file.
 */
public class JavaPersistentTypeDetailsProvider
	implements JpaDetailsProvider
{
	// singleton
	private static final JpaDetailsProvider INSTANCE = new JavaPersistentTypeDetailsProvider();
	
	
	/**
	 * Return the singleton
	 */
	public static JpaDetailsProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private JavaPersistentTypeDetailsProvider() {
		super();
	}
	
	
	public boolean providesDetails(JpaStructureNode structureNode) {
			return ObjectTools.equals(structureNode.getType(), JavaPersistentType.class)
				&& structureNode.getResourceType().getContentType().equals(JavaResourceCompilationUnit.CONTENT_TYPE);
	}
	
	public JpaDetailsPageManager buildDetailsPageManager(Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return new PersistentTypeDetailsPageManager(parent, widgetFactory, resourceManager);
	}
}
