/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkTypeConverter;

/**
 * EclipseLink Java converter container
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.1
 */
public interface EclipseLinkJavaConverterContainer
	extends EclipseLinkConverterContainer
{
	ListIterable<EclipseLinkJavaCustomConverter> getCustomConverters();
	EclipseLinkJavaCustomConverter addCustomConverter(String name, int index);

	ListIterable<EclipseLinkJavaObjectTypeConverter> getObjectTypeConverters();
	EclipseLinkJavaObjectTypeConverter addObjectTypeConverter(String name, int index);

	ListIterable<JavaEclipseLinkStructConverter> getStructConverters();
	JavaEclipseLinkStructConverter addStructConverter(String name, int index);

	ListIterable<JavaEclipseLinkTypeConverter> getTypeConverters();
	JavaEclipseLinkTypeConverter addTypeConverter(String name, int index);

	/**
	 * Parent
	 */
	interface Parent
		extends JpaContextModel
	{
		/**
		 * Return the element that is annotated with converters.
		 */
		JavaResourceAnnotatedElement getJavaResourceAnnotatedElement();

		/**
		 * Return whether the container's parent supports converters.
		 * (Virtual attributes do not support converters.)
		 */
		boolean supportsConverters();
	}
}
