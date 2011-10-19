/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTransientAnnotation;

public class GenericJavaXmlTransientMapping
		extends AbstractJavaAttributeMapping<XmlTransientAnnotation> {
	
	public GenericJavaXmlTransientMapping(JaxbPersistentAttribute parent) {
		super(parent);
	}

	public String getKey() {
		return MappingKeys.XML_TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return JAXB.XML_TRANSIENT;
	}
	
	@Override
	public Iterable<String> getReferencedXmlTypeNames() {
		return EmptyIterable.instance();
	}
	
	@Override
	public boolean isTransient() {
		return true;
	}
}
