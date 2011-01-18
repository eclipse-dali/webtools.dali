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

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jpt.utility.MethodSignature;

/**
 * Java source code or binary persistent attribute (field or property)
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
//TODO handle:
//  @Basic
//  private String foo, bar;
public interface JavaResourcePersistentAttribute
	extends JavaResourcePersistentMember
{

	JavaResourcePersistentType getParent();

	JavaResourcePersistentType getResourcePersistentType();

	/**
	 * The Java resource persistent attribute's name does not change.
	 */
	String getName();
	
	/**
	 * Return a <em>null</em> annotation for the specified annotation name.
	 * Return <code>null</code> if the specified annotation name is
	 * <code>null</code>.
	 * The corresponding annotation definition must implement 
	 * {@link AnnotationDefinition#buildNullAnnotation(JavaResourcePersistentMember)
	 * buildNullAnnotation()}.
	 */
	Annotation buildNullAnnotation(String annotationName);
	
	/**
	 * Whether the Java resource persistent attribute is a field does not change.
	 */
	boolean isField();

	/**
	 * Whether the Java resource persistent attribute is a property does not change.
	 */
	boolean isProperty();

	/**
	 * Return the access type explicitly specified by the javax.persistence.Access annotation.
	 * Return null if the Access annotation is not present. 
	 * For JPA 1.0 this is always going to return null; Access annotation is not supported in 1.0.
	 */
	AccessType getSpecifiedAccess();

	/**
	 * Return whether the attribute's type implements or extends the specified
	 * type.
	 */
	boolean typeIsSubTypeOf(String typeName);

	/**
	 * Return whether the attribute's type is a "variable" primitive type
	 * (i.e. any primitive type except 'void').
	 */
	boolean typeIsVariablePrimitive();

	/**
	 * Return whether the Java resource persistent attribute is for the specified
	 * method.
	 */
	boolean isFor(MethodSignature methodSignature, int occurrence);

	/**
	 * @see java.lang.reflect.Modifier
	 */
	int getModifiers();
		String MODIFIERS_PROPERTY = "modifiers"; //$NON-NLS-1$

	/**
	 * Return the resolved, qualified name of the attribute's type
	 * (e.g. "java.util.Collection" or "byte[]").
	 * If the type is an array, this name will include the appropriate number
	 * of bracket pairs.
	 * This name will not include the type's generic type arguments
	 * (e.g. "java.util.Collection<java.lang.String>" will only return
	 * "java.util.Collection").
	 * @see #typeTypeArgumentNames()
	 */
	String getTypeName();
		String TYPE_NAME_PROPERTY = "typeName"; //$NON-NLS-1$

	/**
	 * Return whether the attribute type is an interface.
	 */
	boolean typeIsInterface();
		String TYPE_IS_INTERFACE_PROPERTY = "typeIsInterface"; //$NON-NLS-1$

	/**
	 * Return whether the attribute type is an enum.
	 */
	boolean typeIsEnum();
		String TYPE_IS_ENUM_PROPERTY = "typeIsEnum"; //$NON-NLS-1$

	/**
	 * Return the names of the attribute type's superclasses.
	 */
	ListIterator<String> typeSuperclassNames();
		String TYPE_SUPERCLASS_NAMES_LIST = "typeSuperclassNames"; //$NON-NLS-1$

	/**
	 * Return the names of the attribute type's interfaces.
	 */
	Iterator<String> typeInterfaceNames();
		String TYPE_INTERFACE_NAMES_COLLECTION = "typeInterfaceNames"; //$NON-NLS-1$

	/**
	 * Return the names of the attribute type's type arguments.
	 * The name for any argument that is an array will contain the appropriate
	 * number of bracket pairs.
	 * The names will not include any further generic type arguments.
	 */
	ListIterator<String> typeTypeArgumentNames();
		String TYPE_TYPE_ARGUMENT_NAMES_LIST = "typeTypeArgumentNames"; //$NON-NLS-1$

	int typeTypeArgumentNamesSize();

	String getTypeTypeArgumentName(int index);
}
