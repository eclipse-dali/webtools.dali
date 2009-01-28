/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface PersistentType
	extends JpaContextNode, JpaStructureNode, AccessHolder
{
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$
		
	String getShortName();
		
	/**
	 * Return the access type that overrides the client persistent type's
	 * access type; null if there is no such access override
	 */
	AccessType getOwnerOverrideAccess();

	/**
	 * Return the client persistent type's default access type;
	 * null if there is no such access default.
	 */
	AccessType getOwnerDefaultAccess();

	TypeMapping getMapping();
	String getMappingKey();
	void setMappingKey(String key);
		String MAPPING_PROPERTY = "mapping"; //$NON-NLS-1$

	boolean isMapped();
	

	/**
	 * Return the parent {@link PersistentType} from the inheritance hierarchy.
	 * If the java inheritance parent is not a {@link PersistentType} then continue
	 * up the hierarchy(the JPA spec allows non-persistent types to be part of the hierarchy.)  
	 * Return null if this persistentType is the root persistent type. 
	 * Example:
	 * <pre>
	 * &#64;Entity
	 * public abstract class Model {}
	 * <a>
	 * public abstract class Animal extends Model {}
	 * <a>
	 * &#64;Entity
	 * public class Cat extends Animal {}
	 * </pre> 
	 * 
	 * If this is the Cat JavaPersistentType then parentPersistentType is the Model JavaPersistentType
	 * The parentPersistentType could be found in java or xml.
	 */
	PersistentType getParentPersistentType();
		String PARENT_PERSISTENT_TYPE_PROPERTY = "parentPersistentType"; //$NON-NLS-1$

	/**
	 * Return a read-only iterator of the contained {@link PersistentAttribute}
	 */
	<T extends PersistentAttribute> ListIterator<T> attributes();
	
	/**
	 * Return the size of {@link PersistentAttribute}s list
	 * @return
	 */
	int attributesSize();
		String SPECIFIED_ATTRIBUTES_LIST = "specifiedAttributes"; //$NON-NLS-1$
	
	Iterator<String> attributeNames();

	/**
	 * Return a read-only iterator of the all the {@link PersistentAttribute}s
	 * in the hierarchy
	 */
	Iterator<PersistentAttribute> allAttributes();

	Iterator<String> allAttributeNames();

	/**
	 * Return the attribute named <code>attributeName</code> if
	 * it exists locally on this type
	 */
	PersistentAttribute getAttributeNamed(String attributeName);

	/**
	 * Resolve and return the attribute named <code>attributeName</code> if it
	 * is distinct and exists within the context of this type
	 */
	PersistentAttribute resolveAttribute(String attributeName);

	/**
	 * Includes the present persistent type.
	 * This iterator will return elements infinitely if the hierarchy has a loop.
	 */
	Iterator<PersistentType> inheritanceHierarchy();
	
	/**
	 * Excludes the present persistent type.
	 * This iterator will return elements infinitely if the hierarchy has a loop.
	 */
	Iterator<PersistentType> ancestors();
	
	
	// **************** validation **************************************

	/**
	 * Add to the list of current validation messages
	 */
	void validate(List<IMessage> messages);


	// ********** owner interface **********

	interface Owner
		extends JpaContextNode
	{
		/**
		 * Return the access type that overrides the client persistent type's
		 * access type; null if there is no such access override
		 */
		AccessType getOverridePersistentTypeAccess();
	
		/**
		 * Return the client persistent type's default access type;
		 * null if there is no such access default.
		 */
		AccessType getDefaultPersistentTypeAccess();

	}

}
