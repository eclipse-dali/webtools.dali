/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
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
 * Common protocol among
 *     javax.persistence.ManyToOne
 *     javax.persistence.ManyToMany
 *     javax.persistence.OneToMany
 *     javax.persistence.OneToOne
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface RelationshipMappingAnnotation
	extends Annotation
{
	/**
	 * Corresponds to the 'targetEntity' element of the relationship mapping
	 * annotations.
	 * Return null if the element does not exist in Java.
	 * Return the portion of the value preceding ".class".
	 * <pre>
	 *     &#64;ManyToMany(targetEntity=Employee.class)
	 * </pre>
	 * will return "Employee"
	 */
	String getTargetEntity();	
		String TARGET_ENTITY_PROPERTY = "targetEntity"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'targetEntity' element of the relationship mapping
	 * annotations.
	 * Set to null to remove the element.
	 */
	void setTargetEntity(String targetEntity);
	
	/**
	 * Return the {@link TextRange} for the 'targetEntity' element. If the element 
	 * does not exist return the {@link TextRange} for the relationship mapping annotation.
	 */
	TextRange getTargetEntityTextRange(CompilationUnit astRoot);

	/**
	 * Return the fully-qualified target entity class name as resolved by the AST's bindings.
	 * <pre>
	 *     &#64;ManyToMany(targetEntity=Employee.class)
	 * </pre>
	 * will return "model.Employee" if there is an import for model.Employee.
	 * @return
	 */
	String getFullyQualifiedTargetEntityClassName();
		String FULLY_QUALIFIED_TARGET_ENTITY_CLASS_NAME_PROPERTY = "fullyQualifiedTargetEntityClassName"; //$NON-NLS-1$


	/**
	 * Corresponds to the 'fetch' element of the relationship mapping annotations.
	 * Return null if the element does not exist in Java.
	 */
	FetchType getFetch();
		String FETCH_PROPERTY = "fetch"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'fetch' element of the relationship annotations.
	 * Set to null to remove the element.
	 */
	void setFetch(FetchType fetch);
	
	/**
	 * Return the {@link TextRange} for the 'fetch' element. If the element 
	 * does not exist return the {@link TextRange} for the relationship mapping annotation.
	 */
	TextRange getFetchTextRange(CompilationUnit astRoot);


	/**
	 * Corresponds to the 'cascade' element of the relationship annotations.
	 */
	boolean isCascadeAll();	
		String CASCADE_ALL_PROPERTY = "cascadeAll"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'cascade' element of the relationship annotations.
	 */
	void setCascadeAll(boolean all);
	
	/**
	 * Corresponds to the 'cascade' element of the relationship annotations.
	 */
	boolean isCascadeMerge();	
		String CASCADE_MERGE_PROPERTY = "cascadeMerge"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'cascade' element of the relationship annotations.
	 */
	void setCascadeMerge(boolean merge);

	/**
	 * Corresponds to the 'cascade' element of the relationship annotations.
	 */
	boolean isCascadePersist();	
		String CASCADE_PERSIST_PROPERTY = "cascadePersist"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'cascade' element of the relationship annotations.
	 */
	void setCascadePersist(boolean persist);

	/**
	 * Corresponds to the 'cascade' element of the relationship annotations.
	 */
	boolean isCascadeRefresh();	
		String CASCADE_REFRESH_PROPERTY = "cascadeRefresh"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'cascade' element of the relationship annotations.
	 */
	void setCascadeRefresh(boolean refresh);

	/**
	 * Corresponds to the 'cascade' element of the relationship annotations.
	 */
	boolean isCascadeRemove();	
		String CASCADE_REMOVE_PROPERTY = "cascadeRemove"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'cascade' element of the relationship annotations.
	 */
	void setCascadeRemove(boolean remove);

	/**
	 * Return the {@link TextRange} for the 'cascade' element. If the element 
	 * does not exist return the {@link TextRange} for the relationship mapping annotation.
	 */
	TextRange getCascadeTextRange(CompilationUnit astRoot);

}
