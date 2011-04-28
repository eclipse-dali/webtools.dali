/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Context persistent type.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface PersistentType
	extends JpaContextNode, JpaStructureNode, AccessHolder
{
	// ********** name **********

	/**
	 * Return the persistent type's [fully-qualified] name.
	 * The enclosing type separator is <code>'.'</code>,
	 * as opposed to <code>'$'</code>.
	 * @see #getSimpleName()
	 */
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$

	/**
	 * Return the persistent type's simple name.
	 * @see #getName()
	 */
	String getSimpleName();


	// ********** mapping **********

	/**
	 * Return the persistent type's mapping.
	 * Set the mapping via {@link #setMappingKey(String)}.
	 */
	TypeMapping getMapping();
		String MAPPING_PROPERTY = "mapping"; //$NON-NLS-1$

	String getMappingKey();

	void setMappingKey(String key);

	boolean isMapped();


	// ********** attributes **********

	/**
	 * Return the persistent type's persistent attributes.
	 */
	<T extends ReadOnlyPersistentAttribute> ListIterator<T> attributes();

	/**
	 * Return the number of the persistent type's persistent attributes.
	 */
	int attributesSize();

	/**
	 * Return the names of the persistent type's persistent attributes.
	 */
	Iterator<String> attributeNames();

	/**
	 * Return all the persistent attributes in the persistent type's
	 * inheritance hierarchy.
	 */
	Iterator<ReadOnlyPersistentAttribute> allAttributes();

	/**
	 * Return the names of all the persistent attributes in the
	 * persistent type's hierarchy.
	 */
	Iterator<String> allAttributeNames();

	/**
	 * Return the persistent attribute with the specified name,
	 * if it exists locally on the persistent type (as opposed to in its
	 * inheritance hierarchy).
	 */
	ReadOnlyPersistentAttribute getAttributeNamed(String attributeName);

	/**
	 * Resolve and return the persistent attribute with the specified name, if it
	 * is distinct and exists within the context of the persistent type.
	 */
	ReadOnlyPersistentAttribute resolveAttribute(String attributeName);


	// ********** inheritance **********

	/**
	 * Return the "super" {@link PersistentType} from the "persistence"
	 * inheritance hierarchy.
	 * If the Java inheritance parent is not a {@link PersistentType}, then continue
	 * up the hierarchy (the JPA spec allows non-persistent types to be part of the hierarchy.)
	 * Return <code>null</code> if the persistent type is the root persistent type.
	 * <p>
	 * Example:
	 * <pre>
	 * &#64;Entity
	 * public abstract class Model {}
	 * 
	 * public abstract class Animal extends Model {}
	 * 
	 * &#64;Entity
	 * public class Cat extends Animal {}
	 * </pre>
	 * The "super" persistent type of the <code>Cat</code> persistent type is
	 * the <code>Model</code> persistent type. The "super" persistent type can
	 * be either a Java annotated class or declared in the XML files.
	 */
	PersistentType getSuperPersistentType();
		String SUPER_PERSISTENT_TYPE_PROPERTY = "superPersistentType"; //$NON-NLS-1$

	/**
	 * Return the persistent type's "persistence" inheritance hierarchy,
	 * <em>including</em> the persistent type itself.
	 * The returned iterator will return elements infinitely if the hierarchy
	 * has a loop.
	 */
	Iterator<PersistentType> inheritanceHierarchy();

	/**
	 * Return the persistent type's "persistence" inheritance hierarchy,
	 * <em>excluding</em> the persistent type itself.
	 * The returned iterator will return elements infinitely if the hierarchy
	 * has a loop.
	 */
	Iterator<PersistentType> ancestors();


	// ********** validation **********

	/**
	 * Add to the list of current validation messages
	 */
	void validate(List<IMessage> messages, IReporter reporter);

	/**
	 * Return the text range to be used with any validation messages related
	 * to the persistent type.
	 */
	TextRange getValidationTextRange();


	// ********** misc **********

	/**
	 * Return whether the persistent type applies to the
	 * specified type name qualified with <code>'.'</code>.
	 */
	boolean isFor(String typeName);

	/**
	 * Return whether the persistent type resolves to a Java class in the
	 * specified package fragment.
	 */
	boolean isIn(IPackageFragment packageFragment);


	// ********** owner **********

	/**
	 * Return the access type that overrides the client persistent type's
	 * access type; <code>null</code> if there is no such access override.
	 */
	AccessType getOwnerOverrideAccess();

	/**
	 * Return the client persistent type's default access type;
	 * <code>null</code> if there is no such access default.
	 */
	AccessType getOwnerDefaultAccess();


	// ********** owner interface **********

	interface Owner
		extends JpaContextNode
	{
		/**
		 * Return the access type that overrides the client persistent type's
		 * access type; <code>null</code> if there is no such access override.
		 */
		AccessType getOverridePersistentTypeAccess();

		/**
		 * Return the client persistent type's default access type;
		 * <code>null</code> if there is no such access default.
		 */
		AccessType getDefaultPersistentTypeAccess();

	}
}
