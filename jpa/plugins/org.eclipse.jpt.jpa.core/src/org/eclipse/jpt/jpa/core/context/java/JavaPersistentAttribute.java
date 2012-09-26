/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jpa.core.context.CollectionMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;

/**
 * Context Java persistent <em>attribute</em> (field or property).
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
public interface JavaPersistentAttribute
	extends PersistentAttribute, JavaElementReference
{
	// ********** covariant overrides **********

	JavaAttributeMapping getMapping();

	JavaAttributeMapping setMappingKey(String key);


	// ********** misc **********

	/**
	 * Return the accessor(field/property) for the attribute
	 */
	Accessor getAccessor();

	/**
	 * Return the corresponding <em>resource</em> attribute. 
	 * This is the attribute (field/method) that is annotated.
	 * @see Accessor#getResourceAttribute()
	 */
	JavaResourceAttribute getResourceAttribute();

	/**
	 * @see Accessor#isFor(JavaResourceField)
	 */
	boolean isFor(JavaResourceField resourceField);

	/**
	 * @see Accessor#isFor(JavaResourceMethod, JavaResourceMethod)
	 */
	boolean isFor(JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter);

	/**
	 * Return whether the attribute contains the given offset into its Java
	 * source code file.
	 */
	boolean contains(int offset);


	// ********** type **********

	/**
	 * Return whether the attribute's type is valid for a default basic mapping.
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
	JpaContainerDefinition getJpaContainerDefinition();
		String JPA_CONTAINER_DEFINITION = "jpaContainerDefinition"; //$NON-NLS-1$


	// ********** JPA container **********

	/**
	 * JPA container definition interface (and null implementation)
	 */
	interface JpaContainerDefinition {
		String getTypeName();
		boolean isContainer();
		boolean isMap();
		String getMultiReferenceTargetTypeName(JavaResourceAttribute resourceAttribute);
		String getMultiReferenceMapKeyTypeName(JavaResourceAttribute resourceAttribute);
		String getMetamodelContainerFieldTypeName();
		String getMetamodelContainerFieldMapKeyTypeName(CollectionMapping mapping);

		final class Null implements JpaContainerDefinition {
			public static final JpaContainerDefinition INSTANCE = new Null();
			public static JpaContainerDefinition instance() {
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
			public boolean isMap() {
				return false;
			}
			public String getMultiReferenceTargetTypeName(JavaResourceAttribute resourceAttribute) {
				return null;
			}
			public String getMultiReferenceMapKeyTypeName(JavaResourceAttribute resourceAttribute) {
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
				return JpaContainerDefinition.class.getSimpleName() + ".Null";  //$NON-NLS-1$
			}
		}
	}

}
