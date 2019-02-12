/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.core.internal.utility.TypeTools;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlElementRef;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;


public class ELJavaXmlElementRef
		extends GenericJavaXmlElementRef {
	
	public ELJavaXmlElementRef(JaxbContextNode parent, GenericJavaXmlElementRef.Context context) {
		super(parent, context);
	}
	
	
	@Override
	protected boolean isTypeJAXBElement() {
		String fqType = getFullyQualifiedType();
		return fqType != null && TypeTools.isSubTypeOf(getFullyQualifiedType(), JAXB.JAXB_ELEMENT, getJaxbProject().getJavaProject());
	}
}
