/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_3;

import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinitionFactory;


public class Moxy_2_3_PlatformDefinitionFactory
		implements JaxbPlatformDefinitionFactory {
	
	public JaxbPlatformDefinition buildJaxbPlatformDefinition() {
		return Moxy_2_3_PlatformDefinition.instance();
	}
}
