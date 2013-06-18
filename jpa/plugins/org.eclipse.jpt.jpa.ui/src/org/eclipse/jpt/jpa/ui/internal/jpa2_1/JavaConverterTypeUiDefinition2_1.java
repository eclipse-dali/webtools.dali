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
import org.eclipse.jpt.jpa.core.jpa2_1.context.ConverterType2_1;
import org.eclipse.jpt.jpa.ui.JavaManagedTypeUiDefinition;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;

public class JavaConverterTypeUiDefinition2_1
	implements JavaManagedTypeUiDefinition
{
	// singleton
	private static final JavaConverterTypeUiDefinition2_1 INSTANCE = new JavaConverterTypeUiDefinition2_1();


	/**
	 * Return the singleton.
	 */
	public static JavaManagedTypeUiDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private JavaConverterTypeUiDefinition2_1() {
		super();
	}

	public Class<ConverterType2_1> getManagedTypeType() {
		return ConverterType2_1.class;
	}

	public ImageDescriptor getImageDescriptor(JavaManagedType managedType) {
		return JptJpaUiImages.CONVERTER;
	}
}
