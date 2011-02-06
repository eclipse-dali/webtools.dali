/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

import org.eclipse.jpt.jpa.core.context.JpaContextNode;

/**
 * Corresponds to a Customizer resource model object
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface EclipseLinkCustomizer
	extends JpaContextNode
{
	String getCustomizerClass();
	
	String getSpecifiedCustomizerClass();	
	void setSpecifiedCustomizerClass(String customizerClass);
		String SPECIFIED_CUSTOMIZER_CLASS_PROPERTY = "specifiedCustomizerClass"; //$NON-NLS-1$
		String ECLIPSELINK_DESCRIPTOR_CUSTOMIZER_CLASS_NAME = "org.eclipse.persistence.config.DescriptorCustomizer"; //$NON-NLS-1$

	String getDefaultCustomizerClass();
		String DEFAULT_CUSTOMIZER_CLASS_PROPERTY = "defaultCustomizerClass"; //$NON-NLS-1$
	
	/**
	 * Return the char to be used for browsing or creating the customizer class IType.
	 * @see org.eclipse.jdt.core.IType#getFullyQualifiedName(char)
	 */
	char getCustomizerClassEnclosingTypeSeparator();
}
