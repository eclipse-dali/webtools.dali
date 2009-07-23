/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context.java;

import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTypeConverter;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.2
 */
public interface EclipseLinkJavaConverterHolder extends JavaJpaContextNode
{
	/**
	 * Initialize the JavaConverterHolder context model object to match the JavaResourcePersistentType 
	 * resource model object. This should be called immediately after object creation.
	 */
	void initialize(JavaResourcePersistentType jrpt);
	
	/**
	 * Update the JavaConverterHolder context model object to match the JavaResourcePersistentType 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(JavaResourcePersistentType jrpt);
	
	EclipseLinkCustomConverter getCustomConverter();
	EclipseLinkCustomConverter addCustomConverter();
	void removeCustomConverter();
	String CUSTOM_CONVERTER_PROPERTY = "customConverter"; //$NON-NLS-1$
	
	EclipseLinkObjectTypeConverter getObjectTypeConverter();
	EclipseLinkObjectTypeConverter addObjectTypeConverter();
	void removeObjectTypeConverter();
	String OBJECT_TYPE_CONVERTER_PROPERTY = "objectTypeConverter"; //$NON-NLS-1$
	
	EclipseLinkStructConverter getStructConverter();
	EclipseLinkStructConverter addStructConverter();
	void removeStructConverter();
	String STRUCT_CONVERTER_PROPERTY = "structConverter"; //$NON-NLS-1$
	
	EclipseLinkTypeConverter getTypeConverter();
	EclipseLinkTypeConverter addTypeConverter();
	void removeTypeConverter();
	String TYPE_CONVERTER_PROPERTY = "typeConverter"; //$NON-NLS-1$
	
}