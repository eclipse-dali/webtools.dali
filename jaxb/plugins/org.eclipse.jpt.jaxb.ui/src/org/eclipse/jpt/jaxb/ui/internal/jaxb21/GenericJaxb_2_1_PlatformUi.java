/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.jaxb21;

import org.eclipse.jpt.jaxb.ui.navigator.JaxbNavigatorUi;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUi;


public class GenericJaxb_2_1_PlatformUi
		implements JaxbPlatformUi {
	
	public JaxbNavigatorUi getNavigatorUi() {
		return GenericJaxb_2_1_NavigatorUi.instance();
	}
}
