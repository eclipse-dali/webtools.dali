/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

public interface JpaValidation
{
	/**
	 * Return whether table-per-concrete-class is a supported
	 * inheritance strategy in the jpa platform.
	 * Supported.MAYBE means that it is in the JPA spec, but not portable
	 * or might not be supported by a particular provider. 
	 * @return
	 */
	Supported getTablePerConcreteClassInheritanceIsSupported();

	
	public enum Supported {
		YES, //fully supported by the platform
		NO,  //not supported by the platform
		MAYBE, //in the JPA spec, might not supported be supported by a particular provider
	}
}
