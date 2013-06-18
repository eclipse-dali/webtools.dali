/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.ui.JavaManagedTypeUiDefinition;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;

//TODO NullManagedTypeUiDefinition or should it be a JavaPersistentType implementation if it doesn't match any of the ManagedType types
public class JavaPersistentTypeUiDefinition
	implements JavaManagedTypeUiDefinition
{
	// singleton
	private static final JavaPersistentTypeUiDefinition INSTANCE = new JavaPersistentTypeUiDefinition();


	/**
	 * Return the singleton.
	 */
	public static JavaManagedTypeUiDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private JavaPersistentTypeUiDefinition() {
		super();
	}

	public Class<PersistentType> getManagedTypeType() {
		return PersistentType.class;
	}

	public ImageDescriptor getImageDescriptor(JavaManagedType managedType) {
		return this.getTypeMappingUiDefinition((JavaPersistentType) managedType).getImageDescriptor();
	}

	private MappingUiDefinition getTypeMappingUiDefinition(JavaPersistentType persistentType) {
		return this.getJpaPlatformUi(persistentType).getTypeMappingUiDefinition(persistentType.getResourceType(), persistentType.getMappingKey());
	}

	private JpaPlatformUi getJpaPlatformUi(JavaPersistentType persistentType) {
		return (JpaPlatformUi) persistentType.getJpaPlatform().getAdapter(JpaPlatformUi.class);
	}
}
