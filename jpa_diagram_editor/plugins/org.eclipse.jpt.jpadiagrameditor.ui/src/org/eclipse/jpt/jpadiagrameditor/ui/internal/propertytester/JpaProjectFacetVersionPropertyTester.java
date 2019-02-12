/*******************************************************************************
 * Copyright (c) 2011, 2019 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.propertytester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.jpt.jpa.core.JpaProject;

public class JpaProjectFacetVersionPropertyTester extends PropertyTester{

	public boolean test(Object receiver, String property, Object[] args,
			Object value) {
		
		String[] subValues = ((String) value).split(":"); //$NON-NLS-1$
		String receiverId = subValues[0];

		if (receiver instanceof JpaModel) {
			if(receiverId.equals(JpaProject.FACET_ID)){
				return true;
			}			
		}
		return false;
	}	
}
