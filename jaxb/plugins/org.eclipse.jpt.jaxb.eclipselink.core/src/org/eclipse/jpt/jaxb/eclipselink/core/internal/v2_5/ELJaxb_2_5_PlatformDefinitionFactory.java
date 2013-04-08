/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_5;

import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinitionFactory;

public class ELJaxb_2_5_PlatformDefinitionFactory
		implements JaxbPlatformDefinitionFactory {

	public JaxbPlatformDefinition buildJaxbPlatformDefinition() {
		return ELJaxb_2_5_PlatformDefinition.instance();
	}
}
