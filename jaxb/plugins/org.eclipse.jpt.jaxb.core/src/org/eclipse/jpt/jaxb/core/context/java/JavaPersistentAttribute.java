/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface JavaPersistentAttribute
		extends JaxbPersistentAttribute {
	
	// ***** overrides *****
	
	public JavaClassMapping getClassMapping();
	
	
	// ***** java resource model *****
	
	JavaResourceAttribute getJavaResourceAttribute();
	
	boolean isFor(JavaResourceField resourceField);
	
	boolean isFor(JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter);
	
	
	// ***** default mapping key *****
	
	String DEFAULT_MAPPING_KEY_PROPERTY = "defaultMappingKey"; //$NON-NLS-1$
	
	/**
	 * Return the key for the attribute's default mapping.
	 * @see JavaAttributeMapping#isDefault()
	 */
	String getDefaultMappingKey();
}
