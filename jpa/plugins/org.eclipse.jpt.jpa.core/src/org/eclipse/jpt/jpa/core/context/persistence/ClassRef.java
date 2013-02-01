/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.persistence;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.DeleteTypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlJavaClassRef;

/**
 * Context model corresponding to the XML resource model
 * {@link XmlJavaClassRef}, which corresponds to the <code>class<code> element
 * in the <code>persistence.xml</code> file.
 * This is also used for "implied" class refs; i.e. class refs that are not
 * explicitly listed in the <code>persistence.xml</code> file.
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
public interface ClassRef
	extends JpaStructureNode, PersistentType.Owner, DeleteTypeRefactoringParticipant, TypeRefactoringParticipant
{
	/**
	 * Return whether the class ref is a reference to the specified type.
	 */
	boolean isFor(String typeName);

	/**
	 * Return <code>true</code> if the mapping file ref is <em>virtual</em>;
	 * return <code>false</code> if the mapping file ref is represented by an
	 * entry in the <code>persistence.xml</code> file.
	 */
	boolean isVirtual();

	/**
	 * Return the class ref's corresponding resource class ref.
	 * This is <code>null</code> for <em>implied</em> class refs.
	 */
	XmlJavaClassRef getXmlClassRef();


	// ********** class name **********

	/**
	 * String constant associated with changes to the class name
	 */
	final static String CLASS_NAME_PROPERTY = "className"; //$NON-NLS-1$

	/**
	 * Return the class name of the class ref.
	 */
	String getClassName();

	/**
	 * Set the class name of the class ref.
	 */
	void setClassName(String className);

	/**
	 * Return the corresponding JavaResourceAbstractType, null if the name does not resolve to a class.
	 */
	JavaResourceAbstractType getJavaResourceType();


	// ********** java persistent type **********

	/**
	 * String constant associated with changes to the java persistent type
	 */
	final static String JAVA_PERSISTENT_TYPE_PROPERTY = "javaPersistentType"; //$NON-NLS-1$

	/**
	 * Return the JavaPersistentType that corresponds to this IClassRef.
	 * This can be null.
	 * This is not settable by users of this API.
	 */
	JavaPersistentType getJavaPersistentType();

	Transformer<ClassRef, JavaPersistentType> JAVA_PERSISTENT_TYPE_TRANSFORMER = new JavaPersistentTypeTransformer();

	class JavaPersistentTypeTransformer
		extends AbstractTransformer<ClassRef, JavaPersistentType>
	{
		@Override
		protected JavaPersistentType transform_(ClassRef ref) {
			return ref.getJavaPersistentType();
		}
	}
}
