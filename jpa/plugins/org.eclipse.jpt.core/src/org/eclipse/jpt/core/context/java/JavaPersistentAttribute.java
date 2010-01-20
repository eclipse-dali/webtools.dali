/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.CollectionMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;

/**
 * Java persistent attribute (field or property)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaPersistentAttribute
	extends PersistentAttribute, JavaJpaContextNode
{
	/**
	 * covariant override
	 */
	JavaAttributeMapping getMapping();

	/**
	 * covariant override
	 */
	JavaAttributeMapping getSpecifiedMapping();

	/**
	 * Return the attribute's default mapping.
	 */
	JavaAttributeMapping getDefaultMapping();

	/**
	 * Update the context persistent attribute to match its
	 * resource persistent attribute (passed in to the constructor).
	 * @see org.eclipse.jpt.core.JpaProject#update()
	 */
	void update();

	/**
	 * Return the "resource" persistent attribute.
	 */
	JavaResourcePersistentAttribute getResourcePersistentAttribute();

	/**
	 * Return whether the specified mapping is the attribute's default mapping.
	 */
	boolean mappingIsDefault(JavaAttributeMapping mapping);

	/**
	 * Return whether the attribute contains the given offset into the text file.
	 */
	boolean contains(int offset, CompilationUnit astRoot);

	/**
	 * Return the embeddable (type mapping) corresponding to the persistent
	 * attribute's type. Return null if it is not found.
	 */
	Embeddable getEmbeddable();

	/**
	 * Return whether the attribute is a field (as opposed to a property).
	 */
	boolean isField();

	/**
	 * Return whether the attribute is a property (as opposed to a field).
	 */
	boolean isProperty();

	/**
	 * Return whether the attribute is 'public', which is problematic for fields.
	 */
	boolean isPublic();

	/**
	 * Return whether the attribute is 'final', which is problematic.
	 */
	boolean isFinal();

	/**
	 * Return whether the attribute's type is valid for a default Basic mapping.
	 */
	boolean typeIsBasic();

	/**
	 * Return the attribute's type name if it is valid as a target type
	 * (i.e. the type is neither an array nor a "container").
	 */
	String getSingleReferenceTargetTypeName();

	/**
	 * If the attribute's type is an appropriate "container" type,
	 * return the type parameter that can be used as a target type.
	 * Return null if the attribute is not a container or if the type
	 * parameter is not valid as a target type (i.e. it is either
	 * an array or a "container").
	 */
	String getMultiReferenceTargetTypeName();

	/**
	 * If the attribute's type is a map type,
	 * return the type parameter that can be used as a key type.
	 * Return null if the attribute is not a map or if the type
	 * parameter is not valid as a key type (i.e. it is either
	 * an array or a "container").
	 */
	String getMultiReferenceMapKeyTypeName();

	/**
	 * Return the JpaContainer that corresponds to this attribute's type.
	 * Return a null implementation if the type is not a container (map or collection)
	 */
	JpaContainer getJpaContainer();

	/**
	 * JPA container interface (and null implementation)
	 */
	interface JpaContainer {
		String getTypeName();
		boolean isContainer();
		String getMultiReferenceTargetTypeName(JavaResourcePersistentAttribute resourcePersistentAttribute);
		String getMultiReferenceMapKeyTypeName(JavaResourcePersistentAttribute resourcePersistentAttribute);
		String getMetamodelContainerFieldTypeName();
		String getMetamodelContainerFieldMapKeyTypeName(CollectionMapping mapping);

		final class Null implements JpaContainer {
			public static final JpaContainer INSTANCE = new Null();
			public static JpaContainer instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Null() {
				super();
			}
			public String getTypeName() {
				return null;
			}
			public boolean isContainer() {
				return false;
			}
			public String getMultiReferenceTargetTypeName(JavaResourcePersistentAttribute resourcePersistentAttribute) {
				return null;
			}
			public String getMultiReferenceMapKeyTypeName(JavaResourcePersistentAttribute resourcePersistentAttribute) {
				return null;
			}
			public String getMetamodelContainerFieldTypeName() {
				return JPA2_0.COLLECTION_ATTRIBUTE;
			}
			public String getMetamodelContainerFieldMapKeyTypeName(CollectionMapping mapping) {
				return null;
			}
			@Override
			public String toString() {
				return "JpaContainer.Null";  //$NON-NLS-1$
			}
		}
	}

}
