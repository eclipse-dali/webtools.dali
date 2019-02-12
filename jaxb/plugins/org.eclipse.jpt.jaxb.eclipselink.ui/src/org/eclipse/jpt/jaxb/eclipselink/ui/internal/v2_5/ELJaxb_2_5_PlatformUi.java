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
package org.eclipse.jpt.jaxb.eclipselink.ui.internal.v2_5;

import org.eclipse.jpt.jaxb.eclipselink.ui.internal.navigator.ELJaxbNavigatorUi;
import org.eclipse.jpt.jaxb.ui.navigator.JaxbNavigatorUi;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUi;

public class ELJaxb_2_5_PlatformUi
		implements JaxbPlatformUi {
	
	public JaxbNavigatorUi getNavigatorUi() {
		return ELJaxbNavigatorUi.instance();
	}
}
