/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2_1;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedType;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaConverterType2_1;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.JavaManagedTypeUiDefinition;

public class JavaConverterTypeUiDefinition
	implements JavaManagedTypeUiDefinition
{
	// singleton
	private static final JavaConverterTypeUiDefinition INSTANCE = 
			new JavaConverterTypeUiDefinition();


	/**
	 * Return the singleton.
	 */
	public static JavaManagedTypeUiDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	protected JavaConverterTypeUiDefinition() {
		super();
	}

	public Class<JavaConverterType2_1> getType() {
		return JavaConverterType2_1.class;
	}

	public ImageDescriptor getImageDescriptor(JavaManagedType managedType) {
		return JptJpaUiImages.CLASS_REF;
	}
}
