/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface RelationshipMappingAnnotation extends JavaResourceNode
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
		String TARGET_ENTITY_PROPERTY = "targetEntity"; //$NON-NLS-1$
	
	/**
	 * Returns the qualified targetEntity name as it is resolved in the AST
	 * <p>
	 *     &#64;ManyToMany(targetEntity=Employee.class)
	 * </p>
	 * will return "model.Employee" if there is an import for model.Employee
	 * @return
	 */
	String getFullyQualifiedTargetEntity();
		String FULLY_QUALFIEID_TARGET_ENTITY_PROPERTY = "fullyQualifiedTargetEntity"; //$NON-NLS-1$
	
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
		String FETCH_PROPERTY = "fetch"; //$NON-NLS-1$
	
	boolean isCascadeAll();	
	void setCascadeAll(boolean all);
		String CASCADE_ALL_PROPERTY = "cascadeAll"; //$NON-NLS-1$
	
	boolean isCascadeMerge();	
	void setCascadeMerge(boolean merge);
	String CASCADE_MERGE_PROPERTY = "cascadeMerge"; //$NON-NLS-1$

	boolean isCascadePersist();	
	void setCascadePersist(boolean persist);
		String CASCADE_PERSIST_PROPERTY = "cascadePersist"; //$NON-NLS-1$

	boolean isCascadeRefresh();	
	void setCascadeRefresh(boolean refresh);
		String CASCADE_REFRESH_PROPERTY = "cascadeRefresh"; //$NON-NLS-1$

	boolean isCascadeRemove();	
	void setCascadeRemove(boolean remove);
		String CASCADE_REMOVE_PROPERTY = "cascadeRemove"; //$NON-NLS-1$

	
	/**
	 * Return the {@link TextRange} for the targetEntity element.  If the targetEntity element 
	 * does not exist return the {@link TextRange} for the mapping annotation.
	 */
	TextRange getTargetEntityTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the fetch element.  If the fetch element 
	 * does not exist return the {@link TextRange} for the mapping annotation.
	 */
	TextRange getFetchTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the cascade element.  If the cascade element 
	 * does not exist return the {@link TextRange} for the mapping annotation.
	 */
	TextRange getCascadeTextRange(CompilationUnit astRoot);

}
