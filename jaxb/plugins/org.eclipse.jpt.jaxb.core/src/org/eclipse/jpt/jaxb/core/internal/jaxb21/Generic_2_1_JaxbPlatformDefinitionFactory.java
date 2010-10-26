/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.jaxb21;

import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinitionFactory;


public class Generic_2_1_JaxbPlatformDefinitionFactory
		implements JaxbPlatformDefinitionFactory {

	public JaxbPlatformDefinition buildJaxbPlatformDefinition() {
		return GenericJaxbPlatformDefinition.instance();
	}
}
