/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;

/**
 * Context persistent <em>attribute</em> (field or property).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since ... a while?
 */
public interface PersistentAttribute
	extends JpaStructureNode, AccessReference
{
	// ********** name **********

	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$

		Transformer<PersistentAttribute, String> NAME_TRANSFORMER = new NameTransformer();
	class NameTransformer
		extends TransformerAdapter<PersistentAttribute, String>
	{
		@Override
		public String transform(PersistentAttribute attribute) {
			return attribute.getName();
		}
	}

	class NameEquals
		extends CriterionPredicate<PersistentAttribute, String>
	{
		public NameEquals(String attributeName) {
			super(attributeName);
		}
		public boolean evaluate(PersistentAttribute attribute) {
			return ObjectTools.equals(this.criterion, attribute.getName());
		}
	}



	// ********** mapping **********

	/**
	 * Return the attribute's mapping. This is never <code>null</code>
	 * (although, it may be a <em>null</em> mapping).
	 * Set the mapping via {@link SpecifiedPersistentAttribute#setMappingKey(String)}.
	 */
	AttributeMapping getMapping();
		String MAPPING_PROPERTY = "mapping"; //$NON-NLS-1$
	Transformer<PersistentAttribute, AttributeMapping> MAPPING_TRANSFORMER = new MappingTransformer();
	class MappingTransformer
		extends TransformerAdapter<PersistentAttribute, AttributeMapping>
	{
		@Override
		public AttributeMapping transform(PersistentAttribute pa) {
			return pa.getMapping();
		}
	}

	/**
	 * Return the attribute's mapping key.
	 */
	String getMappingKey();

	/**
	 * Return the key for the attribute's default mapping.
	 * This can be <code>null</code> (e.g. for <em>specified</em>
	 * <code>orm.xml</code> attributes).
	 * @see AttributeMapping#isDefault()
	 */
	String getDefaultMappingKey();
		String DEFAULT_MAPPING_KEY_PROPERTY = "defaultMappingKey"; //$NON-NLS-1$


	// ********** misc **********

	/**
	 * Return the persistent type that declares the attribute.
	 */
	PersistentType getDeclaringPersistentType();
	
	/**
	 * Return the attribute's declaring persistent type's mapping (as opposed to
	 * the attribute's target type's mapping).
	 * @see #getDeclaringPersistentType()
	 */
	TypeMapping getDeclaringTypeMapping();
	
	/**
	 * Return the resolved, qualified name of the attribute's type
	 * (e.g. <code>"java.util.Collection"</code> or <code>"byte</code>[]").
	 * Return <code>null</code> if the attribute's type can not be resolved.
	 * If the type is an array, this name will include the appropriate number
	 * of bracket pairs.
	 * This name will not include the type's generic type arguments
	 * (e.g. <code>"java.util.Collection<java.lang.String>"</code> will only return
	 * <code>"java.util.Collection"</code>).
	 */
	String getTypeName();

	/**
	 * Return the resolved, qualified name of the attribute's type (see {@link #getTypeName()})
	 *  in the context of the given {@link PersistentType}.
	 * (In some cases, the attribute's type may be further constrained by a subclass of the 
	 *  persistent type that actually contains the attribute.)
	 * Return null if a type cannot be determined for this attribute in the given context.
	 */
	String getTypeName(PersistentType contextType);
	
	/**
	 * If the attribute is mapped to a primary key column, return the
	 * column's name, otherwise return <code>null</code>.
	 */
	String getPrimaryKeyColumnName();
	
	/**
	 * Return whether the attribute has a textual representation
	 * in its underlying resource.
	 */
	boolean isVirtual();

	JavaSpecifiedPersistentAttribute getJavaPersistentAttribute();
}
