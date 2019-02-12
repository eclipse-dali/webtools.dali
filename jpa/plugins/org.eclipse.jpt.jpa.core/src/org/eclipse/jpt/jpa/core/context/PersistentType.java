/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;
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
 * @version 3.6
 * @since 2.0
 */
public interface PersistentType
		extends ManagedType, JpaStructureNode, SpecifiedAccessReference {
	
	Class<PersistentType> getManagedTypeType();
	
	
	// ***** mapping *****
	
	/**
	 * String associated with changes to the "mapping" property
	 */
	String MAPPING_PROPERTY = "mapping"; //$NON-NLS-1$
	
	/**
	 * Return the persistent type's mapping.
	 * Set the mapping via {@link #setMappingKey(String)}.
	 */
	TypeMapping getMapping();
	
	Transformer<PersistentType, TypeMapping> MAPPING_TRANSFORMER = new MappingTransformer();
	class MappingTransformer
			extends TransformerAdapter<PersistentType, TypeMapping> {
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
	ListIterable<? extends PersistentAttribute> getAttributes();
	Transformer<PersistentType, ListIterable<? extends PersistentAttribute>> ATTRIBUTES_TRANSFORMER = new AttributesTransformer();
	class AttributesTransformer
		extends TransformerAdapter<PersistentType, ListIterable<? extends PersistentAttribute>>
	{
		@Override
		public ListIterable<? extends PersistentAttribute> transform(PersistentType type) {
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
	Iterable<PersistentAttribute> getAllAttributes();

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
	PersistentAttribute getAttributeNamed(String attributeName);

	/**
	 * Resolve and return the persistent attribute with the specified name, if it
	 * is distinct and exists within the context of the persistent type.
	 */
	PersistentAttribute resolveAttribute(String attributeName);
	
	/**
	 * Return qualified type name of given attribute within the context of this type.
	 */
	TypeBinding getAttributeTypeBinding(PersistentAttribute attribute);

	/**
	 * One of the persistent type's attributes changed.
	 * Notify interested parties (e.g. "virtual" copies of the attribute).
	 */
	void attributeChanged(PersistentAttribute attribute);


	// ********** inheritance **********
	
	/**
	 * Return the persistent type of the super type mapping
	 * @see TypeMapping#getSuperTypeMapping()
	 */
	PersistentType getSuperPersistentType();
	
	/**
	 * Return the persistent type's "persistence" inheritance hierarchy,
	 * <em>including</em> the persistent type itself.
	 * @see TypeMapping#getInheritanceHierarchy()
	 */
	Iterable<PersistentType> getInheritanceHierarchy();
	
	/**
	 * Return the persistent type's "persistence" inheritance hierarchy,
	 * <em>excluding</em> the persistent type itself.
	 * @see TypeMapping#getAncestors()
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
		extends TransformerAdapter<PersistentType, PersistentType>
	{
		@Override
		public PersistentType transform(PersistentType persistentType) {
			return persistentType.getOverriddenPersistentType();
		}
	}


	// ********** parent **********

	interface Parent
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


	// ********** config **********

	/**
	 * Config that can be used to add persistent types to a persistence unit.
	 * @see org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit#addPersistentTypes(Config[], boolean, IProgressMonitor)
	 */
	interface Config {
		String getName();
		String getMappingKey();
	}
}
