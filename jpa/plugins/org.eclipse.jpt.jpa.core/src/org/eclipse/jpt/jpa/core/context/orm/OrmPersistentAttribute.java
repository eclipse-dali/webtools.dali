/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;

/**
 * <code>orm.xml</code> persistent <em>attribute</em> (field or property).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmPersistentAttribute
	extends PersistentAttribute
{
	Class<OrmPersistentAttribute> getStructureType();

	/**
	 * A <em>specified</em> <code>orm.xml</code> attribute will return an
	 * <code>orm.xml</code> mapping, while a <em>virtual</em> attribute will
	 * return a Java mapping. If the attribute is <em>virtual</em>, the returned
	 * (Java) mapping is also <em>virtual</em>; i.e. it is read-only and is not to
	 * be modified (nor are any of its parts). The modifiable Java
	 * mapping can be retrieved via the modifiable Java attribute:
	 * {@link #resolveJavaPersistentAttribute()}.
	 */
	AttributeMapping getMapping();
	Transformer<OrmPersistentAttribute, AttributeMapping> MAPPING_TRANSFORMER = new MappingTransformer();
	class MappingTransformer
		extends TransformerAdapter<OrmPersistentAttribute, AttributeMapping>
	{
		@Override
		public AttributeMapping transform(OrmPersistentAttribute attribute) {
			return attribute.getMapping();
		}
	}

	OrmPersistentType getDeclaringPersistentType();

	OrmTypeMapping getDeclaringTypeMapping();

	/**
	 * If the <code>orm.xml</code> attribute is <em>virtual</em>, the returned Java
	 * attribute is also <em>virtual</em>; i.e. it is read-only and is not to
	 * be modified (nor are any of its parts [e.g. column]). The modifiable Java
	 * attribute can be retrieved via {@link #resolveJavaPersistentAttribute()}.
	 */
	JavaSpecifiedPersistentAttribute getJavaPersistentAttribute();
		String JAVA_PERSISTENT_ATTRIBUTE_PROPERTY = "javaPersistentAttribute"; //$NON-NLS-1$

	/**
	 * Return the <code>orm.xml</code> attribute's Java attribute; which, in the
	 * case of <em>virtual</em> attributes, is not the same Java attribute as
	 * returned by {@link #getJavaPersistentAttribute()}.
	 * <p>
	 * This is probably useful only to tests; since nothing else will want to
	 * modify the returned Java attribute.
	 */
	JavaSpecifiedPersistentAttribute resolveJavaPersistentAttribute();

	JavaResourceAttribute getJavaResourceAttribute();

	boolean isFor(JavaResourceField javaResourceField);

	boolean isFor(JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter);


	// ********** virtual -> specified **********

	/**
	 * Convert the (currently virtual) attribute to a specified
	 * attribute. The attribute will be added to the list of specified
	 * attributes and added to the <code>orm.xml</code> file. The mapping will
	 * remain the same. Return the new attribute.
	 * @see #isVirtual()
	 * @throw IllegalStateException if the attribute is already specified
	 */
	OrmSpecifiedPersistentAttribute addToXml();

	/**
	 * Convert the (currently virtual) persistent attribute to a specified
	 * attribute. The attribute will be added to the list of specified
	 * attributes and added to the <code>orm.xml</code> file. The mapping will
	 * be changed according to the specified mapping key.
	 * Return the new attribute.
	 * @see #isVirtual()
	 * @throw IllegalStateException if the attribute is already specified
	 */
	OrmSpecifiedPersistentAttribute addToXml(String mappingKey);


	// ********** Java to virtual notification **********

	/**
	 * The specified Java persistent attribute changed in some way;
	 * so the corresponding virtual persistent attribute may need to update its state.
	 */
	void javaAttributeChanged(JavaSpecifiedPersistentAttribute attribute);
}
