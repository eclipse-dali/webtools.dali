/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.java;

import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkTypeConverter;

/**
 * EclipseLink Java converter container
 * <p>
 * <strong>NB:</strong> This is only mildly-related to
 * {@link org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer};
 * thus the lack of common super-interface.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.2
 */
public interface JavaEclipseLinkConverterContainer
	extends JavaJpaContextNode
{
	JavaEclipseLinkCustomConverter getCustomConverter();
	JavaEclipseLinkCustomConverter addCustomConverter();
	void removeCustomConverter();
	String CUSTOM_CONVERTER_PROPERTY = "customConverter"; //$NON-NLS-1$
	
	JavaEclipseLinkObjectTypeConverter getObjectTypeConverter();
	JavaEclipseLinkObjectTypeConverter addObjectTypeConverter();
	void removeObjectTypeConverter();
	String OBJECT_TYPE_CONVERTER_PROPERTY = "objectTypeConverter"; //$NON-NLS-1$
	
	JavaEclipseLinkStructConverter getStructConverter();
	JavaEclipseLinkStructConverter addStructConverter();
	void removeStructConverter();
	String STRUCT_CONVERTER_PROPERTY = "structConverter"; //$NON-NLS-1$
	
	JavaEclipseLinkTypeConverter getTypeConverter();
	JavaEclipseLinkTypeConverter addTypeConverter();
	void removeTypeConverter();
	String TYPE_CONVERTER_PROPERTY = "typeConverter"; //$NON-NLS-1$
}
