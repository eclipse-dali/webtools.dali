/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkObjectTypeConverter;
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
 * @version 3.2
 * @since 2.1
 */
public interface JavaEclipseLinkConverterContainer
	extends EclipseLinkConverterContainer, JavaJpaContextNode
{
	ListIterable<JavaEclipseLinkCustomConverter> getCustomConverters();
	JavaEclipseLinkCustomConverter addCustomConverter(int index);

	ListIterable<JavaEclipseLinkObjectTypeConverter> getObjectTypeConverters();
	JavaEclipseLinkObjectTypeConverter addObjectTypeConverter(int index);

	ListIterable<JavaEclipseLinkStructConverter> getStructConverters();
	JavaEclipseLinkStructConverter addStructConverter(int index);

	ListIterable<JavaEclipseLinkTypeConverter> getTypeConverters();
	JavaEclipseLinkTypeConverter addTypeConverter(int index);

	/**
	 * Parent adapter
	 */
	interface ParentAdapter {
		/**
		 * Return the container's parent.
		 */
		JavaJpaContextNode getConverterContainerParent();

		/**
		 * Return the element that is annotated with converters.
		 */
		JavaResourceAnnotatedElement getJavaResourceAnnotatedElement();

		/**
		 * Return whether the container's parent supports converters.
		 * (Virtual attributes do not support converters.)
		 */
		boolean parentSupportsConverters();
	}
}
