/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.details;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.AbstractMappedSuperclassUiProvider;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class JavaMappedSuperclassUiProvider extends AbstractMappedSuperclassUiProvider<JavaMappedSuperclass>
{
	// singleton
	private static final JavaMappedSuperclassUiProvider INSTANCE = 
		new JavaMappedSuperclassUiProvider();
	
	/**
	 * Return the singleton.
	 */
	public static TypeMappingUiProvider<JavaMappedSuperclass> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private JavaMappedSuperclassUiProvider() {
		super();
	}
	
	public IContentType getContentType() {
		return JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE;
	}
	
	public JpaComposite buildPersistentTypeMappingComposite(
		JpaUiFactory factory,
		PropertyValueModel<JavaMappedSuperclass> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return factory.createJavaMappedSuperclassComposite(subjectHolder, parent, widgetFactory);
	}
}
