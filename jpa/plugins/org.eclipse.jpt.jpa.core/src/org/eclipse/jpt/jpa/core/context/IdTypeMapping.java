/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * ID type mapping:<ul>
 * <li>entity
 * <li>mapped superclass
 * </ul>
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.6
 * @since 3.5
 */
public interface IdTypeMapping
		extends TypeMapping {
	
	// ***** primary key *****
	
	/**
	 * Return the (aggregate) class reference for configuring and validating
	 * the type mapping's ID class.
	 */
	IdClassReference getIdClassReference();
	
	/**
	 * Return the fully qualified name of the type used as the primary key class
	 * (e.g. the id class name, or the type of the id mapping, etc.)
	 */
	String getPrimaryKeyClassName();
	
	
	// ***** inheritance *****
	
	/**
	 * Return the type mapping's "persistence" inheritance hierarchy,
	 * <em>including</em> the type mapping itself.
	 * If there is an inheritance loop, the iterable will terminate before including
	 * this type mapping a second time.
	 * @see TypeMapping#getAncestors()
	 */
	Iterable<IdTypeMapping> getInheritanceHierarchy();
	
	/**
	 * {@link Transformer} that returns the super type mapping of the input,
	 * except if result would be the leaf type mapping.
	 * Use to terminate inheritance cycles
	 * @see IdTypeMapping#getInheritanceHierarchy()
	 * @see TypeMapping#getAncestors()
	 */
	class SuperTypeMappingTransformer
			implements Transformer<IdTypeMapping, IdTypeMapping> {
		
		private final IdTypeMapping leaf;
		
		public SuperTypeMappingTransformer(IdTypeMapping leaf) {
			this.leaf = leaf;
		}
		
		public IdTypeMapping transform(IdTypeMapping input) {
			IdTypeMapping result = input.getSuperTypeMapping();
			return (result == this.leaf) ? null : result;
		}
	}
	
	/**
	 * Return whether this type mapping is a root entity in an inheritance hierarchy.
	 */
	boolean isRootEntity();
	
	/**
	 * Return the root entity of the inheritance hierarchy or null.
	 */
	Entity getRootEntity();
	
	/**
	 * Return the inheritance strategy or null
	 */
	InheritanceType getInheritanceStrategy();
}
