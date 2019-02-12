/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.jaxb21;

import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinitionFactory;


public class GenericJaxb_2_1_PlatformDefinitionFactory
		implements JaxbPlatformDefinitionFactory {

	public JaxbPlatformDefinition buildJaxbPlatformDefinition() {
		return GenericJaxb_2_1_PlatformDefinition.instance();
	}
}
