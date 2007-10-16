/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.details;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsPage;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class JavaDetailsProvider 
	implements IJpaDetailsProvider
{
	public JavaDetailsProvider() {
		super();
	}
	
	public String fileContentType() {
		return JavaCore.JAVA_SOURCE_CONTENT_TYPE;
	}
	
	public IJpaDetailsPage buildDetailsPage(
			Composite parentComposite, Object contentNodeId, TabbedPropertySheetWidgetFactory widgetFactory) {
		if (contentNodeId.equals(IJavaContentNodes.PERSISTENT_TYPE_ID)) {
			return new JavaPersistentTypeDetailsPage(parentComposite, widgetFactory);
		}
		else if (contentNodeId.equals(IJavaContentNodes.PERSISTENT_ATTRIBUTE_ID)) {
			return new JavaPersistentAttributeDetailsPage(parentComposite, widgetFactory);
		}
		
		return null;
	}

	public void dispose() {
		// no op  ... for now
	}
}
