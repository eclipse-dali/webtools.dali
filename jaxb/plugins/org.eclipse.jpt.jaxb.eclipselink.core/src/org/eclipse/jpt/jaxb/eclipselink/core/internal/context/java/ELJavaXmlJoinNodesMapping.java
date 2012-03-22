/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbMappingKeys;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlJoinNodesMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;


public class ELJavaXmlJoinNodesMapping
		extends AbstractJavaAttributeMapping
		implements ELXmlJoinNodesMapping {
	
	public ELJavaXmlJoinNodesMapping(JaxbPersistentAttribute parent) {
		super(parent);
	}
	
	
	public String getKey() {
		return ELJaxbMappingKeys.XML_JOIN_NODES_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return ELJaxb.XML_JOIN_NODES;
	}
	
	
	// ***** overrides *****
	
	@Override
	protected boolean buildDefault() {
		return getAnnotation() == null 
				&& CollectionTools.isEmpty(getPersistentAttribute().getJavaResourceAttribute().getAnnotations(ELJaxb.XML_JOIN_NODE));
	}
}
