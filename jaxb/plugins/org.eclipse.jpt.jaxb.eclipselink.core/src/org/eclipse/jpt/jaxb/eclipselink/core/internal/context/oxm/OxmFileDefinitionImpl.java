/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFileDefinition;

public class OxmFileDefinitionImpl
		implements OxmFileDefinition {
	
	final OxmAttributeMappingDefinition[] attributeMappingDefinitions = {
			new OxmXmlAnyAttributeDefinition(),
			new OxmXmlAnyElementDefinition(),
			new OxmXmlAttributeDefinition(),
			new OxmXmlElementDefinition(),
			new OxmXmlElementRefDefinition(),
			new OxmXmlElementRefsDefinition(),
			new OxmXmlElementsDefinition(),
			new OxmXmlInverseReferenceDefinition(),
			new OxmXmlJoinNodesDefinition(),
			new OxmXmlTransformationDefinition(),
			new OxmXmlTransientDefinition(),
			new OxmXmlValueDefinition(),
			new OxmXmlJavaTypeAdapterXmlElementDefinition() };  // this one goes LAST
	
	
	public OxmAttributeMappingDefinition getAttributeMappingDefinitionForKey(String key) {
		for (OxmAttributeMappingDefinition amd : this.attributeMappingDefinitions) {
			if (ObjectTools.equals(amd.getKey(), key)) {
				return amd;
			}
		}
		throw new IllegalArgumentException("No attribute mapping definition for key: " + key);
	}
	
	public OxmAttributeMappingDefinition getAttributeMappingDefinitionForElement(String element) {
		for (OxmAttributeMappingDefinition amd : this.attributeMappingDefinitions) {
			if (ObjectTools.equals(amd.getElement(), element)) {
				return amd;
			}
		}
		throw new IllegalArgumentException("No attribute mapping definition supports element: " + element);
	}
}
