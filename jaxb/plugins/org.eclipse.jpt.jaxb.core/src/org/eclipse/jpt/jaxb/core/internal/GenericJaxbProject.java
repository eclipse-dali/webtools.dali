/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.jpt.jaxb.core.JaxbProject;

/**
 * Not much different from the abstract JAXB project.
 */
public class GenericJaxbProject
	extends AbstractJaxbProject
{

	// ********** constructor/initialization **********

	public GenericJaxbProject(JaxbProject.Config config) {
		super(config);
	}

}
