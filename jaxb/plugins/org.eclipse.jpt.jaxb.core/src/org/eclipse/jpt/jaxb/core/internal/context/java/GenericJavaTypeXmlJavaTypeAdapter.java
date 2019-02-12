/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.jaxb.core.context.java.JavaType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;

public class GenericJavaTypeXmlJavaTypeAdapter
	extends AbstractJavaXmlJavaTypeAdapter
{

	public GenericJavaTypeXmlJavaTypeAdapter(JavaType parent, XmlJavaTypeAdapterAnnotation resource) {
		super(parent, resource);
	}

	@Override
	public JavaType getParent() {
		return (JavaType) super.getParent();
	}

	// ********** type **********
	@Override
	protected String buildDefaultType() {
		return getParent().getJavaResourceType().getTypeBinding().getQualifiedName();
	}

}
