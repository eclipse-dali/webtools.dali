/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.customization;

import org.eclipse.jpt.eclipselink.core.internal.context.PersistenceUnitProperties;

/**
 *  Customization
 */
public interface Customization extends PersistenceUnitProperties
{
	//http://wiki.eclipse.org/Using_EclipseLink_JPA_Extensions_%28ELUG%29#How_to_Use_the_Persistence_Unit_Properties_for_Customization_and_Validation
	
	Boolean getDefaultThrowExceptions();
	Boolean getThrowExceptions();
	void setThrowExceptions(Boolean newThrowExceptions); // put
		static final String THROW_EXCEPTIONS_PROPERTY = "throwExceptionsProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_THROW_EXCEPTIONS = "eclipselink.orm.throw.exceptions";
		static final Boolean DEFAULT_THROW_EXCEPTIONS = Boolean.TRUE;
	
	
	// EclipseLink key string
//	Boolean 
	static final String ECLIPSELINK_WEAVING = "persistence.tools.weaving";
	
//	Boolean 
	static final String ECLIPSELINK_WEAVING_LAZY = "persistence.tools.weaving.lazy";
	
//	Boolean 
	static final String ECLIPSELINK_WEAVING_CHANGETRACKING = "persistence.tools.weaving.changetracking";
	
//	Boolean 
	static final String ECLIPSELINK_WEAVING_FETCHGROUPS = "persistence.tools.weaving.fetchgroups";
	
//	Class name 
	static final String ECLIPSELINK_SESSION_CUSTOMIZER = "eclipselink.session.customizer";
	
//	Class name 
	static final String ECLIPSELINK_DESCRIPTOR_CUSTOMIZER = "eclipselink.descriptor.customizer.";
	
	
	
	
	
//	TTT getDefaultZZZ();
//	TTT getZZZ();
//	void setZZZ(TTT newZZZ); // put
//		static final String XXX_PROPERTY = "sssProperty";
//		// EclipseLink key string
//		static final String ECLIPSELINK_XXX = "eclipselink.ooo";
//		static final TTT DEFAULT_XXX = Boolean.TRUE;

	
}
