/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaStructureNode;

/**
 * Context persistent type.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.0
 */
public interface PersistentType
	extends ManagedType, JpaStructureNode, AccessHolder
{
	Class<? extends PersistentType> getType();

	// ********** mapping **********

	/**
	 * Return the persistent type's mapping.
	 * Set the mapping via {@link #setMappingKey(String)}.
	 */
	TypeMapping getMapping();
		String MAPPING_PROPERTY = "mapping"; //$NON-NLS-1$
	Transformer<PersistentType, TypeMapping> MAPPING_TRANSFORMER = new MappingTransformer();
	class MappingTransformer
		extends TransformerAdapter<PersistentType, TypeMapping>
	{
		@Override
		public TypeMapping transform(PersistentType pt) {
			return pt.getMapping();
		}
	}

	String getMappingKey();

	void setMappingKey(String key);

	boolean isMapped();


	// ********** attributes **********

	/**
	 * Return the persistent type's persistent attributes.
	 */
	ListIterable<? extends ReadOnlyPersistentAttribute> getAttributes();
	Transformer<PersistentType, ListIterable<? extends ReadOnlyPersistentAttribute>> ATTRIBUTES_TRANSFORMER = new AttributesTransformer();
	class AttributesTransformer
		extends TransformerAdapter<PersistentType, ListIterable<? extends ReadOnlyPersistentAttribute>>
	{
		@Override
		public ListIterable<? extends ReadOnlyPersistentAttribute> transform(PersistentType type) {
			return type.getAttributes();
		}
	}

	/**
	 * Return the number of the persistent type's persistent attributes.
	 */
	int getAttributesSize();

	/**
	 * Return the names of the persistent type's persistent attributes.
	 */
	Iterable<String> getAttributeNames();

	/**
	 * Return all the persistent attributes in the persistent type's
	 * inheritance hierarchy.
	 */
	Iterable<ReadOnlyPersistentAttribute> getAllAttributes();

	/**
	 * Return the names of all the persistent attributes in the
	 * persistent type's hierarchy.
	 */
	Iterable<String> getAllAttributeNames();

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
	
	/**
	 * Return qualified type name of given attribute within the context of this type.
	 */
	TypeBinding getAttributeTypeBinding(ReadOnlyPersistentAttribute attribute);


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
	Transformer<PersistentType, PersistentType> SUPER_PERSISTENT_TYPE_TRANSFORMER = new SuperPersistentTypeTransformer();
	class SuperPersistentTypeTransformer
		extends AbstractTransformer<PersistentType, PersistentType>
	{
		@Override
		protected PersistentType transform_(PersistentType persistentType) {
			return persistentType.getSuperPersistentType();
		}
	}

	/**
	 * Return the persistent type's "persistence" inheritance hierarchy,
	 * <em>including</em> the persistent type itself.
	 * The returned iterator will return elements infinitely if the hierarchy
	 * has a loop.
	 */
	Iterable<PersistentType> getInheritanceHierarchy();

	/**
	 * Return the persistent type's "persistence" inheritance hierarchy,
	 * <em>excluding</em> the persistent type itself.
	 * The returned iterator will return elements infinitely if the hierarchy
	 * has a loop.
	 */
	Iterable<PersistentType> getAncestors();


	// ********** misc **********

	/**
	 * Return the persistent type the persistent type overrides.
	 * Typically this is the Java persistent type overridden by a
	 * non-metadata-complete <code>orm.xml</code> persistent type.
	 */
	PersistentType getOverriddenPersistentType();
	Transformer<PersistentType, PersistentType> OVERRIDDEN_PERSISTENT_TYPE_TRANSFORMER = new OverriddenPersistentTypeTransformer();
	class OverriddenPersistentTypeTransformer
		extends AbstractTransformer<PersistentType, PersistentType>
	{
		@Override
		protected PersistentType transform_(PersistentType persistentType) {
			return persistentType.getOverriddenPersistentType();
		}
	}


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
		extends JpaContextModel
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
