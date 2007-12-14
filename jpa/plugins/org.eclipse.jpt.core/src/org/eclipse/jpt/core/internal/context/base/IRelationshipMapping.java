/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import org.eclipse.jdt.core.dom.CompilationUnit;


public interface IRelationshipMapping extends IAttributeMapping
{

	String getTargetEntity();

	String getSpecifiedTargetEntity();
	void setSpecifiedTargetEntity(String value);
		String SPECIFIED_TARGET_ENTITY_PROPERTY = "specifiedTargetEntityProperty";

	String getDefaultTargetEntity();
		String DEFAULT_TARGET_ENTITY_PROPERTY = "defaultTargetEntityProperty";

	IEntity getResolvedTargetEntity();

	void setResolvedTargetEntity(IEntity value);

//	ICascade getCascade();

//	void setCascade(ICascade value);

	/**
	 * Return whether the specified 'targetEntity' is valid.
	 */
	boolean targetEntityIsValid(String targetEntity);

	/**
	 * Return the fully qualified target entity.  If it is already specified
	 * as fully qualified then just return that.
	 * @return
	 */
	String fullyQualifiedTargetEntity(CompilationUnit astRoot);

	/**
	 * Return the Entity that owns this relationship mapping
	 * @return
	 */
	IEntity getEntity();

//	ICascade createCascade();
}