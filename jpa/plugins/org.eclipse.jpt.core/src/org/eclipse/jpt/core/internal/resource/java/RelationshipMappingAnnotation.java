/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;



public interface RelationshipMappingAnnotation extends MappingAnnotation
{
	/**
	 * Corresponds to the targetEntity element of the relationship 
	 * annotations (ManyToMany, OneToMany, ManyToOne, OneToOne).
	 * Returns null if the targetEntity element does not exist in java.
	 * Returns the portion of the targetEntity preceding the .class.
	 * <p>
	 *     &#64;ManyToMany(targetEntity=Employee.class)
	 * </p>
	 * will return "Employee"
	 **/
	String getTargetEntity();	
	
	/**
	 * Corresponds to the targetEntity of the relationship 
	 * annotations (ManyToMany, OneToMany, ManyToOne, OneToOne).
	 * Set to null to remove the targetEntity element.
	 */
	void setTargetEntity(String targetEntity);
	
	/**
	 * Returns the qualified targetEntity name as it is resolved in the AST
	 * <p>
	 *     &#64;ManyToMany(targetEntity=Employee.class)
	 * </p>
	 * will return "model.Employee" if there is an import for model.Employee
	 * @return
	 */
	String getFullyQualfiedTargetEntity();
	
	/**
	 * Corresponds to the fetch element of the relationship 
	 * annotations (ManyToMany, OneToMany, ManyToOne, OneToOne).
	 * Returns null if the fetch element does not exist in java.
	 */
	FetchType getFetch();
	
	/**
	 * Corresponds to the fetch element of the relationship 
	 * annotations (ManyToMany, OneToMany, ManyToOne, OneToOne).
	 * Set to null to remove the fetch element.
	 */
	void setFetch(FetchType fetch);
	
	boolean isCascadeAll();
	
	void setCascadeAll(boolean all);
	
	boolean isCascadeMerge();
	
	void setCascadeMerge(boolean merge);

	boolean isCascadePersist();
	
	void setCascadePersist(boolean persist);

	boolean isCascadeRefresh();
	
	void setCascadeRefresh(boolean refresh);

	boolean isCascadeRemove();
	
	void setCascadeRemove(boolean remove);

}
